/* eslint-disable @typescript-eslint/no-explicit-any */
import { ChangeEvent, useEffect, useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";


export default function SelaaTapahtumienLippujaComponent() {
  const [liput, setLiput] = useState<any[]>([]);
  const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
  const [valittuTapahtumaId, setValittuTapahtumaId] = useState("-1");
  
  useEffect(() => {
      fetchTapahtumat();
    }, [])
  
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
          setKaikkiTapahtumat(data);
        }
  
      } catch(e) {
        console.error(e)
      }
    }

  const setTapahtumaId = async (e: ChangeEvent<HTMLSelectElement>) => {
      setValittuTapahtumaId(e.target.value);
    }

    console.log("Valittu tapahtuma ID:", valittuTapahtumaId);
    console.log("Kaikki tapahtumat:", kaikkiTapahtumat);

    const valittuTapahtuma = kaikkiTapahtumat.find(tapahtuma => tapahtuma.tapahtuma_id === parseInt(valittuTapahtumaId));
    console.log("valittu tapahtuma:", valittuTapahtuma);

  
  const fetchLiput = async () => {
    try {
      let result = await fetch(`${scrummeriConfig.apiBaseUrl}/liput`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
        }
      })
      if (result.status === 200) {
        const data = await result.json();
        console.log(data);
        const valittuTapahtumaLipputyypitIdLista = valittuTapahtuma.tapahtuman_lipputyypit.map((liTy: any) => liTy.tapahtuma_lipputyyppi_id);
        let suodatetutLiput = data.filter((lip: any) => valittuTapahtumaLipputyypitIdLista.includes(lip.tapahtuman_lipputyyppi.tapahtuma_lipputyyppi_id));
        setLiput(suodatetutLiput);
      }

    } catch(e) {
      console.error(e)
    }

  }

  
  const getTapahtumanNimi = (lippu:any) => {
    let nimi;

    kaikkiTapahtumat.forEach(t => {
      t.tapahtuman_lipputyypit.forEach((l:any) => {
        if(l.tapahtuma_lipputyyppi_id === lippu.tapahtuman_lipputyyppi.tapahtuma_lipputyyppi_id) {
          nimi = t.nimi;
        } 
      })
    })

    return nimi;
  } 

  return(
    <>
      <div className="row my-4">
        <h2 className="pb-2">Selaa lippuja</h2>
        

        <label htmlFor="tapahtuma-select" className="form-label">Tapahtuma</label>
          <select
            value={valittuTapahtumaId}
            onChange={e => setTapahtumaId(e)}
            className="form-select mb-2"
            id="tapahtuma-select"
          >
            <option value={"-1"}></option>
            {kaikkiTapahtumat && 
              kaikkiTapahtumat.map(tapahtuma => {
                return(
                  <option value={tapahtuma.tapahtuma_id} key={tapahtuma.tapahtuma_id}>
                    {tapahtuma.nimi}
                  </option>
                )
            })}
          </select>

        <div>
        <div className="col-12">
          <button className="btn btn-primary" onClick={fetchLiput}>
            Hae liput
          </button>
        </div>

        </div>

      </div>

      <div className="row my-4">
        <div className="col-12">
          {kaikkiTapahtumat.length != 0 && liput &&
            <table className="table mb-4">
              <thead>
                <tr>
                  <th>Id</th>
                  <th>Koodi</th>
                  <th>Tapahtuma</th>
                  <th>Lipputyyppi</th>
                  <th>Myyntipäivä</th>
                  <th>MyyntiId</th>
                  <th>Maksutapa</th>
                  <th>Hinta</th>
                  <th>Tila</th>
                </tr>
              </thead>

              <tbody>
                {kaikkiTapahtumat && liput &&
                  liput.map((lippu) => {
                    return(
                      <tr key={lippu.lippu_id}>
                        <td>{lippu.lippu_id}</td>
                        <td>{lippu.koodi}</td>
                        <td>{getTapahtumanNimi(lippu)}</td>
                        <td>{lippu.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi}</td>
                        <td>{lippu.myynti.myyntipaiva}</td>
                        <td>{lippu.myynti.myynti_id}</td>
                        <td>{lippu.myynti.maksutapa.maksutapa}</td>
                        <td>{lippu.hinta.toFixed(2)} €</td>
                        <td>{lippu.tila.tila}</td>
                      </tr>
                    )
                  })
                }
              </tbody>
            </table>
          }
        </div>
      </div>
    </>
  )
}