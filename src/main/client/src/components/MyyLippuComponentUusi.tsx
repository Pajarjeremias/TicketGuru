/* eslint-disable @typescript-eslint/no-explicit-any */
import { ChangeEvent, useEffect, useState } from "react"
import { LipunMyyntiTapahtuma } from "../types/LipunMyyntiTapahtuma"
import { config as scrummeriConfig } from "../config/scrummerit";
import { format } from "date-fns";
import { Myyntiraportti, tyhjaMyyntiRportti } from "../types/Myyntiraportti";
import { BlobProvider } from "@react-pdf/renderer";
import LiputPDFComponent from "./LiputPdfComponent";
import QRCode from 'qrcode';

export default function MyyLippuComponentUusi() {
  const [myyLippuLista, setMyyLippuLista] = useState<LipunMyyntiTapahtuma[]>([]);
  const [hintaYhteensa, setHintaYhteensa] = useState(0);
  const [myyntiError, setMyyntiError] = useState('');
  const [myyntiYhteenveto, setMyyntiYhteenveto] = useState<Myyntiraportti>(tyhjaMyyntiRportti);
  const [shouldReload, setShouldReload] = useState(false);
  const [loading, setLoading] = useState(false);
  const [loadingTapahtumat, setLoadingTapahtumat] = useState(false);
  const [qrDataUrls, setQrDataUrls] = useState<any[]>([])

  useEffect(() => {
    fetchTapahtumat();
  }, [shouldReload]);

  useEffect(() => {
    if (myyntiYhteenveto.id != -1) {
      const loadQrCodes = async () => {
        const qrCodes = await fetchCodes();
        setQrDataUrls(qrCodes); // Store array of strings
      };
    
      loadQrCodes();
    }

  }, [myyntiYhteenveto])

  const fetchTapahtumat = async () => {
    setLoadingTapahtumat(true);
    try {
      const result = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
        }
      })
      if (result.status === 200) {
        const data = await result.json();
        const holder: LipunMyyntiTapahtuma[] = []
        data.forEach((tapahtuma: any) => {
          holder.push({
            id: tapahtuma.tapahtuma_id,
            nimi: tapahtuma.nimi,
            maxLiput: tapahtuma.lippumaara,
            currentLiput: 0,
            aika: new Date(tapahtuma.paivamaara),
            kuvaus: tapahtuma.kuvaus,
            lipputyypit: tapahtuma.tapahtuman_lipputyypit.map((l: { tapahtuma_lipputyyppi_id: any; lipputyyppi: { lipputyyppi: any; }; hinta: any; }) => {
              return({
                id: l.tapahtuma_lipputyyppi_id,
                nimi: l.lipputyyppi.lipputyyppi,
                hinta: l.hinta,
                maara: 0
              })
            })
          })
        })

        const getLippumaara = async () => {
          const currentLiputPromises: Promise<any>[] = [];

          holder.forEach(t => {
            currentLiputPromises.push(
              fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat/${t.id}/myydytliput`, {
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                },
              }).then(async res => {
                return {id: t.id, maara: await res.json()}
              })
            );
          })
          return await Promise.all(currentLiputPromises);
        }
        const lippudata = await getLippumaara();

        lippudata.forEach(data => {
          const indx = holder.findIndex(t => t.id === data.id);
          holder[indx].currentLiput = data.maara;
        })

        setMyyLippuLista(holder);
        setLoadingTapahtumat(false);
      }
    } catch(e) {
      console.error(e)
    }
  }

  const updateLista = (event: ChangeEvent<HTMLInputElement>) => {
    const holder = Array.from(myyLippuLista, t => t);
    holder.forEach(t => {
      t.lipputyypit.forEach(l => {
        if (l.id.toString() === event.target.id) {
          if (parseInt(event.target.value) > 10) {
            l.maara = 10;
          } else {
            l.maara = parseInt(event.target.value);
          }
          
        }
      })
    })
    setMyyLippuLista(holder);
    updateHintaYhteensa();
  }

  const updateHintaYhteensa = () => {
    let total = 0;
    myyLippuLista.forEach(t => {
      t.lipputyypit.forEach(l => {
        total += (l.maara * l.hinta);
      })
    })
    setHintaYhteensa(total);
  }

  const countLiput = () => {
    let total = 0;
    myyLippuLista.forEach(t => {
      t.lipputyypit.forEach(l => {
        total += l.maara;
      })
    })
    return total;
  }

  const createTickets = async () => {
    // Luodaan myynti ja otetaan talteen myyntiId
    const response = await fetch(`${scrummeriConfig.apiBaseUrl}/myynnit`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
      },
      body: JSON.stringify({
        liput: [],
        asiakas: null,
        myyntipiste: {
            myyntipisteId: 1
        },
        maksutapa: {
            maksutapa_id: 2
        }
      })
    })
    const data = await response.json();
    const myyntiId = data.myynti_id;

    // Luodaan liput
    const postLoop = async () => {
      const ticketPromises: Promise<any>[] = [];
    
      myyLippuLista.forEach(t => {
        t.lipputyypit.forEach(l => {
          for (let i = 0; i < Math.min(l.maara, 10); i++) {
            ticketPromises.push(
              fetch(`${scrummeriConfig.apiBaseUrl}/liput`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                },
                body: JSON.stringify({
                  myynti_id: myyntiId,
                  tapahtuman_lipputyypit_id: l.id,
                  hinta: l.hinta
                })
              }).then(res => res.json())
            );
          }
        });
      });

      return await Promise.all(ticketPromises);
    };

    return [myyntiId, await postLoop()]
  }

  const myyLiput = async () => {
    setLoading(true);
    if (countLiput() === 0) {
      setMyyntiError('Myynti ei sisällä lippuja');

    } else {
      setMyyntiError('');
      const [myyntiId, uudetLiput] = await createTickets();
      setMyyntiYhteenveto({
        ...myyntiYhteenveto,
        id: myyntiId,
        aika: new Date(),
        summa: hintaYhteensa,
        liput: [...uudetLiput]
      });

    }
    setLoading(false)
  }

  const fetchCodes = async (): Promise<string[]> => {
    const codeListPromises = myyntiYhteenveto.liput.map(l => generateQrDataURL(l.koodi));
    return await Promise.all(codeListPromises);
  };

  const generateQrDataURL = async (value: string): Promise<string> => {
    try {
      return await QRCode.toDataURL(value);
    } catch (err) {
      console.error("QR generation failed:", err);
      return "";
    }
  };

  const getTapahtumaNimi = (lippu: { tapahtuman_lipputyyppi: { tapahtuma_lipputyyppi_id: number; }; }) => {
    let nimi = "Ei löytynyt";
    myyLippuLista.forEach(t => {
      t.lipputyypit.forEach(l => {
        if (lippu.tapahtuman_lipputyyppi.tapahtuma_lipputyyppi_id === l.id) {
          nimi = t.nimi;
        }
      })
    })
    return nimi;
  }

  const reset = () => {
    setMyyLippuLista([]);
    setHintaYhteensa(0);
    setMyyntiError('');
    setMyyntiYhteenveto(tyhjaMyyntiRportti);
    setShouldReload(!shouldReload);
  }

  const LiputPDF = () => {
    return(
      <LiputPDFComponent
        myyntiYhteenveto={myyntiYhteenveto}
        myyLippuLista={myyLippuLista}
        qrDataUrls={qrDataUrls} />
    )
  }

  return(
    <>
      <div className="row mt-4">
        <h2 className="pb-2">Myy lippuja
          {myyntiYhteenveto.id != -1 &&
            <span> - Raportti</span>
          }
        </h2>
      </div>

      {myyntiYhteenveto.id != -1 &&
        <>
          <div className="row mb-3 pb-3">
            <div className="col-12 col-sm-6" style={{fontSize: "1em"}}>
              <div className="mb-1">Myynti ID: <b>{myyntiYhteenveto.id}</b></div>
              <div className="mb-1">Maksettu: <b>{`${format(myyntiYhteenveto.aika, "dd.MM.yyyy")} klo ${format(myyntiYhteenveto.aika, "hh:mm")}`}</b></div>
              <div className="mb-1">Summa: <b>€{myyntiYhteenveto.summa.toFixed(2)}</b></div>
            </div>

            <div className="col-12 col-sm-6 ms-md-auto mt-2 mt-md-0" style={{width: "fit-content"}}>
              {myyntiYhteenveto.id != -1 && qrDataUrls.length != 0 &&
                <BlobProvider document={LiputPDF()}>
                {({ url }) => (
                  <a className="btn btn-primary" href={url || ""} target="_blank">Tulosta liput</a>
                )}
                </BlobProvider>
              }
              <button className="ms-2 btn btn-outline-secondary" onClick={reset}>
                Uusi myyntitapahtuma
              </button>
            </div>
          </div>

          <div className="row">
            <div className="col-12">
              <table className="table">
                <thead>
                  <tr>
                    <th scope="col">Tapahtuma</th>
                    <th scope="col">Lipputyyppi</th>
                    <th scope="col">Hinta</th>
                    <th scope="col">Koodi</th>
                  </tr>
                </thead>

                <tbody>
                  {myyntiYhteenveto.liput.map((lippu, indx) => {
                    return(
                      <tr key={indx}>
                        <td>{getTapahtumaNimi(lippu)}</td>
                        <td>{lippu.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi}</td>
                        <td>€{lippu.hinta.toFixed(2)}</td>
                      <td>{lippu.koodi}</td>
                    </tr>
                    )
                  })}
                </tbody>
              </table>
            </div>
          </div>
        </>
      }

      {loadingTapahtumat &&
        <div className="container text-center mt-5">
          <div className="row">
            <div className="col-12">
              <div className="spinner-border" role="status">
                <span className="sr-only"></span>
              </div>
            </div>
          </div>

          <div className="row mt-1">
            <div className="col-12">
              Haetaan tapahtumia...
            </div>
          </div>
        </div>
      }

      {myyntiYhteenveto.id === -1 && loadingTapahtumat === false &&
        <>
          <div className="row mb-3">
            <div className="col-5" style={{fontSize: "1em"}}>
              Hinta yhteensä: <b>€{hintaYhteensa.toFixed(2)}</b>
            </div>

            <div className="col-5">
              <span className="text-danger">{myyntiError}</span>
            </div>

            <div className="col-2 ms-auto" style={{width: "fit-content"}}>
              <button className="btn btn-primary" onClick={myyLiput} style={{width: "6em"}} disabled={loading? true : false}>
              {loading &&
                <span className="spinner-border spinner-border-sm" aria-hidden="true"></span>
              }
              {!loading &&
                <span>Myy liput</span>
              }
              </button>
            </div>
          </div>

          <div className="row">
            <div className="col-12">
              {myyLippuLista &&
                <div>
                  {myyLippuLista.map((tapahtuma, index) => {
                    return(
                      <div className="accordion mb-1" id={`accordion-${index}`} key={index}>
                        <div className="accordion-item">
                          <h2 className="accordion-header" id={`header-${index}`}>
                            <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target={`#collapse-${index}`} aria-expanded="true" aria-controls="collapseOne">
                              <b className="col-6">{tapahtuma.nimi}</b>
                              <span className="ms-auto me-5">{`${format(tapahtuma.aika, "dd.MM.yyyy")} klo ${format(tapahtuma.aika, "hh:mm")}`}</span>
                            </button>
                          </h2>
                          <div id={`collapse-${index}`} className="accordion-collapse collapse collapse" aria-labelledby={`header-${index}`} data-bs-parent={`#accordion-${index}`}>
                            <div className="accordion-body">
                              <div className="row">
                                <div className="pb-2 col-5 col-md-9">
                                  {tapahtuma.kuvaus}
                                </div>
                                <div className="col-3 ms-auto me-2" style={{width: "fit-content"}}>
                                  Lippuja myyty: <b>{tapahtuma.currentLiput}/{tapahtuma.maxLiput}</b> kpl
                                </div> 
                              </div>
                                {tapahtuma.lipputyypit &&
                                  <form className="my-2 pt-3" style={{borderTop: "1px solid lightgrey"}}>
                                    {tapahtuma.lipputyypit.length === 0 &&
                                      <div className="text-danger">Tapahtumalle ei ole luotu lipputyyppejä</div>
                                    }
                                    {tapahtuma.lipputyypit.map((l, indx) => {
                                      return(
                                        <div key={indx}>
                                          <div className="row mb-1">
                                            <div style={{minWidth: "10em"}} className="col-2">{l.nimi}<span className="ms-2">€{l.hinta.toFixed(2)}</span></div>
                                            <div className="col-2 col-lg-1" style={{minWidth: "6em"}}>
                                              <input id={l.id.toString()} type="number" min={0} max={10} className="form-control maara-input" placeholder="Email" value={l.maara}
                                              onChange={event => updateLista(event)}
                                              ></input>
                                            </div>
                                          </div>
                                        </div>
                                      )
                                    })}
                                  </form>
                                }
                              </div>
                            </div>
                          </div>
                      </div>
                    )
                  })}
                </div>
              }
            </div>
          </div>
        </>
      }
    </>
  )
}