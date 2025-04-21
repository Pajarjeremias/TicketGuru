/* eslint-disable @typescript-eslint/no-explicit-any */
import { useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";


export default function SelaaLippujaComponent() {
  const [liput, setLiput] = useState<any[]>([]);
  const [tapahtumat, setTapahtumat] = useState<any[]>([]);

  const fetchLiputJaTapahtumat = async () => {
    try {
      let result = await fetch(`${scrummeriConfig.apiBaseUrl}/liput`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
        }
      })
      if (result.status === 200) {
        const data = await result.json();
        setLiput(data);
      }

      result = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
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

  const getTapahtumanNimi = (lippu:any) => {
    let nimi;

    tapahtumat.forEach(t => {
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
        <div className="col-12">
          <button className="btn btn-primary" onClick={fetchLiputJaTapahtumat}>
            Hae liput
          </button>
        </div>
      </div>

      <div className="row my-4">
        <div className="col-12">
          {tapahtumat.length != 0 && liput &&
            <table className="table mb-4">
              <thead>
                <tr>
                  <th>Id</th>
                  <th>Koodi</th>
                  <th>Tapahtuma</th>
                  <th>Lipputyyppi</th>
                  <th>Myyntipäivä</th>
                  <th>Maksutapa</th>
                  <th>Hinta</th>
                  <th>Tila</th>
                </tr>
              </thead>

              <tbody>
                {tapahtumat && liput &&
                  liput.map((lippu) => {
                    return(
                      <tr key={lippu.lippu_id}>
                        <td>{lippu.lippu_id}</td>
                        <td>{lippu.koodi}</td>
                        <td>{getTapahtumanNimi(lippu)}</td>
                        <td>{lippu.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi}</td>
                        <td>{lippu.myynti.myyntipaiva}</td>
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