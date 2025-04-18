import { useEffect, useState } from "react";
import { config as defaultConfig } from "../config/default";
import { config as pixeliConfig } from "../config/pixeli";
import { config as scrummeriConfig } from "../config/scrummerit";

const tyhjaScrummeritLippu = {
  id: '',
  koodi: '',
  lipputyyppi: '',
  hinta: '',
  tila: {
    id: '',
    tila: ''
  },
  tarkastanut: '',
  tarkastuspvm: '',
  myynti: {
    myyntipaiva: '',
    maksutapa: ''
  }
}

const tyhjaPixeliLippu = {
  id: '',
  koodi: '',
  lipputyyppi: '',
  hinta: '',
  tapahtuma: '',
  kaytetty: ''
}

export default function MainComponent() {
  // Tab 1 - Tarkasta lippu stuffs
  const [koodi, setKoodi] = useState('');
  const [id, setId] = useState('');
  const [envi, setEnvi] = useState('Scrummerit');
  const [scrummeritLippu, setScrummeritLippu] = useState(tyhjaScrummeritLippu);
  const [pixeliLippu, setPixeliLippu] = useState(tyhjaPixeliLippu);
  const [successMsg, setSuccessMsg] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const [lipunHakuLoading, setLipunHakuLoading] = useState(false);
  const [lipunTarkastusLoading, setLipunTarkastusLoading] = useState(false);

  // Tab 2 - Myy lippu stuffs
  const [tapahtumat, setTapahtumat] = useState([ ]);
  const [lippuForm, setLippuForm] = useState({
    tapahtuma: '',
    lipputyyppiId: 0,
    maara: 1,
    hinta: 0
  });

  // Common for both tabs
  const [activeTab, setActiveTab] = useState(1);

  useEffect(() => {
    fetchTapahtumat();
  }, [])

  const getTicketInfo = async () => {
    let result;
    let data;

    if (koodi === '') {
      setSuccessMsg('');
      setErrorMsg('Koodikenttä on tyhjä');
      return;
    }

    setLipunHakuLoading(true);

    switch (envi) {
      case 'Localhost':
        result = await fetch(`${defaultConfig.apiBaseUrl}/liput?koodi=${koodi}`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
          }
        })
        if (result.status === 200) {
          data = await result.json();
          setScrummeritLippu({
            id: data.lippu_id,
            koodi: data.koodi,
            lipputyyppi: data.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi,
            hinta: data.hinta,
            tila: {
              id: data.tila.tila_id,
              tila: data.tila.tila
            },
            tarkastanut: data.tarkastanut == null ? '': data.tarkastanut,
            tarkastuspvm: data.tarkastus_pvm == null ? '' : data.tarkastus_pvm,
            myynti: {
              myyntipaiva: data.myynti.myyntipaiva,
              maksutapa: data.myynti.maksutapa.maksutapa
            }
          });
          setErrorMsg('');
          setSuccessMsg('Lipun haku onnistui.')
          setPixeliLippu(tyhjaPixeliLippu);

        } else {
          setErrorMsg(result.status + " Lipun haku ei onnistunut.")
          setSuccessMsg('')
          setScrummeritLippu(tyhjaScrummeritLippu);
          setPixeliLippu(tyhjaPixeliLippu);
        }
        break;

      case 'Scrummerit':
        try {
          result = await fetch(`${scrummeriConfig.apiBaseUrl}/liput?koodi=${koodi}`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
            }
          })
          if (result.status === 200) {
            data = await result.json();
            setScrummeritLippu({
              id: data.lippu_id,
              koodi: data.koodi,
              lipputyyppi: data.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi,
              hinta: data.hinta,
              tila: {
                id: data.tila.tila_id,
                tila: data.tila.tila
              },
              tarkastanut: data.tarkastanut == null ? '': data.tarkastanut,
              tarkastuspvm: data.tarkastus_pvm == null ? '' : data.tarkastus_pvm,
              myynti: {
                myyntipaiva: data.myynti.myyntipaiva,
                maksutapa: data.myynti.maksutapa.maksutapa
              }
            });
            setErrorMsg('');
            setSuccessMsg('Lipun haku onnistui.');
            setPixeliLippu(tyhjaPixeliLippu);

          } else {
            setErrorMsg(result.status + " Lipun haku ei onnistunut.");
            setSuccessMsg('');
            setScrummeritLippu(tyhjaScrummeritLippu);
            setPixeliLippu(tyhjaPixeliLippu);
          }
        } catch(e) {
          setScrummeritLippu(tyhjaScrummeritLippu);
          setPixeliLippu(tyhjaPixeliLippu);

          if (e instanceof TypeError) {
            setErrorMsg("CORS-virhe")
          } else {
            setErrorMsg("Tuntematon virhe")
          }
          setSuccessMsg('');

        }
        break;

      case 'Pixelipioneerit':
        try {
          result = await fetch(`${pixeliConfig.apiBaseUrl}/tickets?code=${koodi}`, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Basic ${btoa('admin:admin')}`,
            }
          })
          if (result.status === 200) {
            data = await result.json();
            data = data[data.length - 1];

            setPixeliLippu({
              id: data.ticketId,
              koodi: data.code,
              lipputyyppi: data.ticketType.ticketType,
              hinta: data.price,
              tapahtuma: data.event.name,
              kaytetty: data.ticketUsed ? 'Kyllä' : 'Ei'
            });
            setScrummeritLippu(tyhjaScrummeritLippu);
            setSuccessMsg('Lipun haku onnistui.')
            setErrorMsg('');

          } else {
            setErrorMsg(result.status + " Lipun haku ei onnistunut.");
            setSuccessMsg('');
            setPixeliLippu(tyhjaPixeliLippu);
            setScrummeritLippu(tyhjaScrummeritLippu);
          }
        } catch(e) {
          setPixeliLippu(tyhjaPixeliLippu);
          setScrummeritLippu(tyhjaScrummeritLippu);

          if (e instanceof TypeError) {
            setErrorMsg("CORS-virhe")
          } else {
            setErrorMsg("Tuntematon virhe")
          }
          setSuccessMsg('');

        }
        break;  

      default:
    }
    setLipunHakuLoading(false);
  }

  const clearTicketInfo = () => {
    setKoodi('');
    setId('');
    setErrorMsg('')
    setSuccessMsg('')
    setScrummeritLippu(tyhjaScrummeritLippu);
    setPixeliLippu(tyhjaPixeliLippu);
  }

  const markTicketAsUsed = async () => {
    let result;
    let data;

    if (id === '') {
      setSuccessMsg('');
      setErrorMsg('ID-kenttä on tyhjä');
      return;
    }

    setLipunTarkastusLoading(true);

    switch (envi) {
      case 'Localhost':
        result = await fetch(`${defaultConfig.apiBaseUrl}/liput/${id}`, {
          method: "PATCH",
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
          },
          body: JSON.stringify({
            'used': new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0]
          })
        })
        if (result.status === 200) {
          data = await result.json();
          setScrummeritLippu({
            id: data.lippu_id,
            koodi: data.koodi,
            lipputyyppi: data.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi,
            hinta: data.hinta,
            tila: {
              id: data.tila.tila_id,
              tila: data.tila.tila
            },
            tarkastanut: data.tarkastanut == null ? '': data.tarkastanut,
            tarkastuspvm: data.tarkastus_pvm == null ? '' : data.tarkastus_pvm,
            myynti: {
              myyntipaiva: data.myynti.myyntipaiva,
              maksutapa: data.myynti.maksutapa.maksutapa
            }
          });
          setPixeliLippu(tyhjaPixeliLippu);
          setSuccessMsg('Lippu merkitty tarkastetuksi')
          setErrorMsg('');

        } else {
          setPixeliLippu(tyhjaPixeliLippu);
          setErrorMsg(result.status + " Lippu on jo tarkastettu.")
          setSuccessMsg('');

        }
        break;

      case 'Scrummerit':
        try {
          result = await fetch(`${scrummeriConfig.apiBaseUrl}/liput/${id}`, {
            method: 'PATCH',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
            },
            body: JSON.stringify({
              'used': new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0]
            })
          })
          if (result.status === 200) {
            data = await result.json();
            setScrummeritLippu({
              id: data.lippu_id,
              koodi: data.koodi,
              lipputyyppi: data.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi,
              hinta: data.hinta,
              tila: {
                id: data.tila.tila_id,
                tila: data.tila.tila
              },
              tarkastanut: data.tarkastanut == null ? '': data.tarkastanut,
              tarkastuspvm: data.tarkastus_pvm == null ? '' : data.tarkastus_pvm,
              myynti: {
                myyntipaiva: data.myynti.myyntipaiva,
                maksutapa: data.myynti.maksutapa.maksutapa
              }
            });
            setPixeliLippu(tyhjaPixeliLippu);
            setSuccessMsg('Lippu merkitty tarkastetuksi')
            setErrorMsg('');

          } else {
            setPixeliLippu(tyhjaPixeliLippu);
            setErrorMsg(result.status + " Lippu on jo tarkastettu.")
            setSuccessMsg('');
          }
        } catch(e) {
          setScrummeritLippu(tyhjaScrummeritLippu);
          setPixeliLippu(tyhjaPixeliLippu);
          if (e instanceof TypeError) {
            setErrorMsg("CORS-virhe")
          } else {
            setErrorMsg("Tuntematon virhe")
          }
          setSuccessMsg('');
        }
        break;

        case 'Pixelipioneerit':
          try {
            result = await fetch(`${pixeliConfig.apiBaseUrl}/tickets/check/${id}`, {
              method: 'PATCH',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${btoa('admin:admin')}`,
              }
            })
            if (result.status === 200) {
              data = await result.json();
              setPixeliLippu({
                id: data.ticketId,
                koodi: data.code,
                lipputyyppi: data.ticketType.ticketType,
                hinta: data.price,
                tapahtuma: data.event.name,
                kaytetty: data.ticketUsed ? 'Kyllä' : 'Ei'
              });
              setScrummeritLippu(tyhjaScrummeritLippu);
              setSuccessMsg('Lippu merkitty tarkastetuksi')
              setErrorMsg('');

            } else {
              setScrummeritLippu(tyhjaScrummeritLippu);
              setErrorMsg(result.status + " Lippu on jo tarkastettu.")
              setSuccessMsg('');

            }
          } catch(e) {
            setPixeliLippu(tyhjaPixeliLippu);
            setScrummeritLippu(tyhjaScrummeritLippu);
            if (e instanceof TypeError) {
              setErrorMsg("CORS-virhe")
            } else {
              setErrorMsg("Tuntematon virhe")
            }
            setSuccessMsg('');
          }
          break;

      default:
    }

    setLipunTarkastusLoading(false);
  }

  const fetchTapahtumat = async () => {
    try {
      const result = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
        }
      })
      if (result.status === 200) {
        const data = await result.json();
        setTapahtumat(data);
      }

    } catch(e) {
      console.error(e)
    }
    
  }

  const switchTab = () => {
    if (activeTab === 1) {
      setActiveTab(2);
    } else {
      setActiveTab(1);
    }
  }

  return(
    <>
      <div className="container my-4">
        
        {/***** Tab navigation *****/}
        <ul className="nav nav-tabs">
          <li className="nav-item">
            <a className={"nav-link " + (activeTab === 1 ? "active" : "")} onClick={switchTab}>Tarkista lippu</a>
          </li>
          <li className="nav-item">
            <a className={"nav-link " + (activeTab === 2 ? "active" : "")} onClick={switchTab}>Myy lippu</a>
          </li>
        </ul>
      

      {/***** Tab 1 - Tarkasta lippu *****/}
        <div className={"container " + (activeTab === 1 ? "" : "display-none")}>
          <div className="row my-4">
            <div className="col-12">
              Valitse käytettävä back-end:
              <div className="form-check">
                <input type="radio" value="Localhost" name="envi" className="form-check-input" checked={envi === "Localhost"} onChange={e => setEnvi(e.target.value)}/>
                Localhost
              </div>
              <div className="form-check">
                <input type="radio" value="Pixelipioneerit" name="envi" className="form-check-input" checked={envi === "Pixelipioneerit"} onChange={e => setEnvi(e.target.value)}/>
                Pixelipioneerit Rahti
              </div>
              <div className="form-check">
                <input type="radio" value="Scrummerit" name="envi" className="form-check-input" checked={envi === "Scrummerit"} onChange={e => setEnvi(e.target.value)} />
                Scrummerit Rahti
              </div>
            </div>
          </div>
          <div className="row my-4">
            <div className="col-6 pe-4">
              <h2>Hae Lippu koodilla</h2>
              <div className="mt-3 mb-2">
                Syötä haettavan lipun koodi ja paina "Hae lipun tiedot"
              </div>

              <div className="mb-3">
                <input type="text" value={koodi} className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän koodi" onChange={e => setKoodi(e.target.value)}></input>

                {!lipunHakuLoading &&
                  <button className="btn btn-primary mt-2" onClick={getTicketInfo} style={{width: "9em"}} >
                    Hae lipun tiedot
                  </button>  
                }

                {lipunHakuLoading &&
                  <button className="btn btn-primary mt-2" style={{width: "9em"}} type="button" disabled id="book-search-spinner">
                    <span className="spinner-border spinner-border-sm" aria-hidden="true"></span>
                  </button>
                }

                <button className="btn btn-outline-secondary mt-2 ms-2" onClick={clearTicketInfo}>
                  Tyhjennä kaikki kentät
                </button>
              </div>
            </div>
            <div className="col-6">
            <h2>Merkitse lippu käytetyksi</h2>
              <div className="mt-3 mb-2">
                Syötä käytetyksi merkattavan lipun ID ja paina "Merkitse käytetyksi"
              </div>
              <div className="mb-3">
                <input type="text" value={id} className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän id" onChange={e => setId(e.target.value)}></input>

                {!lipunTarkastusLoading &&
                  <button className="btn btn-primary mt-2" style={{width: "11em"}} onClick={markTicketAsUsed}>
                    Merkitse käytetyksi
                  </button>
                }

                {lipunTarkastusLoading &&
                  <button className="btn btn-primary mt-2" style={{width: "11em"}} type="button" disabled id="book-search-spinner">
                    <span className="spinner-border spinner-border-sm" aria-hidden="true"></span>
                  </button>
                }

              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-12">

            {successMsg &&
              <p className="text-success" style={{fontSize: "20px"}}>{successMsg}</p>
            }

            {errorMsg &&
              <p className="text-danger" style={{fontSize: "20px"}}>{errorMsg}</p>
            }

            {scrummeritLippu.id &&
              <div>
                <h3>Lipun tiedot (Scrummerit)</h3>
                <table className="table">
                  <tbody>
                    <tr>
                      <th scope="row">Id</th>
                      <td>{scrummeritLippu.id}</td>
                    </tr>
                    <tr>
                      <th scope="row">Koodi</th>
                      <td>{scrummeritLippu.koodi}</td>
                    </tr>
                    <tr>
                      <th scope="row">Lipputyyppi</th>
                      <td>{scrummeritLippu.lipputyyppi}</td>
                    </tr>
                    <tr>
                      <th scope="row">Hinta</th>
                      <td>{scrummeritLippu.hinta + " €"}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tila</th>
                      <td>{scrummeritLippu.tila.tila}</td>
                    </tr>
                    <tr>
                      <th scope="row">Myyntipäivä</th>
                      <td>{scrummeritLippu.myynti.myyntipaiva}</td>
                    </tr>
                    <tr>
                      <th scope="row">Maksutapa</th>
                      <td>{scrummeritLippu.myynti.maksutapa}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tarkastuspvm</th>
                      <td>{scrummeritLippu.tarkastuspvm ? scrummeritLippu.tarkastuspvm : '-'}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tarkastanut</th>
                      <td>{scrummeritLippu.tarkastanut ? scrummeritLippu.tarkastanut : '-'}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            }

            {pixeliLippu.id &&
              <div>
                <h3>Lipun tiedot (Pixelipioneerit)</h3>
                <table className="table">
                  <tbody>
                    <tr>
                      <th scope="row">Id</th>
                      <td>{pixeliLippu.id}</td>
                    </tr>
                    <tr>
                      <th scope="row">Koodi</th>
                      <td>{pixeliLippu.koodi}</td>
                    </tr>
                    <tr>
                      <th scope="row">Lipputyyppi</th>
                      <td>{pixeliLippu.lipputyyppi}</td>
                    </tr>
                    <tr>
                      <th scope="row">Hinta</th>
                      <td>{pixeliLippu.hinta + " €"}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tapahtuma</th>
                      <td>{pixeliLippu.tapahtuma}</td>
                    </tr>
                    <tr>
                      <th scope="row">Käytetty</th>
                      <td>{pixeliLippu.kaytetty}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            }

          </div>
        </div>
      </div>

      {/***** Tab 2 - Myy lippu *****/}
      <div className={"container " + (activeTab === 2 ? "" : "display-none")}>
          <div className="row my-4">
          <h2 className="pb-2">Myy lippu</h2>
            <div className="col-6">
              
              <label htmlFor="tapahtuma-select" className="form-label">Valitse tapahtuma</label>
              <select id="tapahtuma-select" className="form-select mb-2" onChange={(event) => setLippuForm({...lippuForm, tapahtuma: event.target.value})}>
                <option selected disabled>Tapahtuma</option>
                {tapahtumat[0] && 
                  tapahtumat.map(tapahtuma => {
                    return(
                      <option value={tapahtuma.nimi}>
                        {tapahtuma.nimi}
                      </option>
                    )
                  })}
              </select>

              <label htmlFor="tapahtuma-select" className="form-label">Valitse lipputyyppi</label>
                <select id="tapahtuma-select" className="form-select mb-2" aria-label="Default select example" disabled={lippuForm.tapahtuma == '' ? true : false} onChange={(event) => setLippuForm({...lippuForm, lipputyyppiId: parseInt(event.target.value)})}>
                  <option selected disabled>Lipputyyppi</option>
                    {tapahtumat && lippuForm.tapahtuma &&
                      tapahtumat.find(tapahtuma => tapahtuma.nimi == lippuForm.tapahtuma)?.tapahtuman_lipputyypit.map(lipputyyppi => {
                        return (
                          <option value={lipputyyppi.tapahtuma_lipputyyppi_id}>
                            {lipputyyppi.lipputyyppi.lipputyyppi}
                            </option>
                        )
                      })
                    }
                </select>

                <label htmlFor="exampleFormControlInput1" className="form-label">Määrä</label>
                <input type="number" className="form-control" id="exampleFormControlInput1" value={lippuForm.maara} disabled={lippuForm.tapahtuma == '' ? true : false}></input>
                
                <button className="btn btn-primary mt-3">
                Myy liput
              </button>
            </div>

            <div className="col-3">

            </div>

            <div className="col-3">

            </div>

            <div className="col-3">

            </div>
          </div>

          <div className="row my-4">

          </div>

          <div className="row">
            <div className="col-12">

            </div>
        </div>
      </div>
    </div>
  </>

  )
}