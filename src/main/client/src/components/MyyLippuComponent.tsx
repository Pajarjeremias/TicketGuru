import { useState, useEffect, ChangeEvent } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";
//import { config as defaultConfig } from "../config/default";

const tyhjaLippuForm = {
  tapahtuma: 0,
  lipputyyppiId: 0,
  maara: 1,
  hinta: 0,
  myydytLiput: 0,
  maxLiput: 0
}

export default function MyyLippuComponent() {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const [tapahtumat, setTapahtumat] = useState<any[]>([ ]);
    const [lippuForm, setLippuForm] = useState(tyhjaLippuForm);
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const [uudetLiput, setUudetLiput] = useState<any[]>([ ]);
    const [message, setMessage] = useState('');

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
        setTapahtumat(data);
      }

    } catch(e) {
      console.error(e)
    }
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
      const holder = [];
      for (let i = 0; i < lippuForm.maara; i++) {
        const res = await fetch(`${scrummeriConfig.apiBaseUrl}/liput`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
          },
          body: JSON.stringify({
            myynti_id: myyntiId,
            tapahtuman_lipputyypit_id: lippuForm.lipputyyppiId,
            hinta: lippuForm.hinta
          })
        })
        holder.push(await res.json());
      }
      return Promise.all(holder);
    }
    setUudetLiput(await postLoop());
    setMessage('Liput luotu tietokantaan onnistuneesti');
    setLippuForm({...lippuForm, myydytLiput: lippuForm.myydytLiput + lippuForm.maara});
  }

  const setTyyppiAndHinta = (event: ChangeEvent<HTMLSelectElement>) => {
    tapahtumat.forEach(tapahtuma => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      tapahtuma.tapahtuman_lipputyypit.forEach((lipputyyppi: { tapahtuma_lipputyyppi_id: string; hinta: any; }) => {
        if(lipputyyppi.tapahtuma_lipputyyppi_id == event.target.value) {
          setLippuForm({...lippuForm, hinta: lipputyyppi.hinta, lipputyyppiId: parseInt(event.target.value)})
        }
      })
    })
  }

  const resetFields = () => {
    setLippuForm(tyhjaLippuForm);
    setMessage('');
    setUudetLiput([]);
  }

  const setTapahtumaAndFetchNoTickets = async (event: ChangeEvent<HTMLSelectElement>) => {
    const tapahtuma = tapahtumat.find(tapahtuma => tapahtuma.tapahtuma_id == event.target.value);

    const res = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat/${event.target.value}/myydytliput`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
      }
    })

    setLippuForm({...lippuForm, myydytLiput: await res.json(), tapahtuma: tapahtuma?.tapahtuma_id, maxLiput: tapahtuma?.lippumaara});
  }

  return (
    <>
      <div className="row my-4">
        <h2 className="pb-2">Myy lippuja</h2>
        <div className="col-12">

          <label htmlFor="tapahtuma-select" className="form-label">Tapahtuma</label>
          <select defaultValue={0} id="tapahtuma-select" className="form-select mb-2" onChange={(event => setTapahtumaAndFetchNoTickets(event))}>
            <option disabled value={0} key={0}></option>
            {tapahtumat[0] && 
              tapahtumat.map(tapahtuma => {
                return(
                  <option value={tapahtuma.tapahtuma_id} key={tapahtuma.tapahtuma_id}>
                    {tapahtuma.nimi}
                  </option>
                )
            })}
          </select>

          {lippuForm.maxLiput != 0 &&
            <p>
              <b>Lippuja myyty: {lippuForm.myydytLiput}/{lippuForm.maxLiput}</b>
            </p>
          }

          <label htmlFor="tapahtuma-select" className="form-label">Lipputyyppi</label>
          <select defaultValue={""} id="tapahtuma-select" className="form-select mb-2" aria-label="Default select example" disabled={lippuForm.tapahtuma == 0 ? true : false} onChange={(event) => setTyyppiAndHinta(event)}>
            <option disabled value="" key={0}></option>
            {tapahtumat && lippuForm.tapahtuma &&
              // eslint-disable-next-line @typescript-eslint/no-explicit-any
              tapahtumat.find(tapahtuma => tapahtuma.tapahtuma_id == lippuForm.tapahtuma)?.tapahtuman_lipputyypit.map((lipputyyppi: any) => {
                return (
                  <option value={lipputyyppi.tapahtuma_lipputyyppi_id} key={lipputyyppi.tapahtuma_lipputyyppi_id}>
                    {lipputyyppi.lipputyyppi.lipputyyppi}
                    </option>
                  )
                })
              }
          </select>

          <label htmlFor="exampleFormControlInput1" className="form-label">Määrä</label>
          <input min={1} max={10} type="number" className="form-control" id="exampleFormControlInput1" value={lippuForm.maara} disabled={lippuForm.lipputyyppiId == 0 ? true : false} onChange={event => setLippuForm({...lippuForm, maara: parseInt(event.target.value)})}></input>
                
          <button className="btn btn-primary mt-3" onClick={createTickets}>
            Myy liput
          </button>

          <button className="btn btn-outline-secondary ms-2 mt-3" onClick={resetFields}>
            Tyhjennä kentät
          </button>

          {message &&
            <p className="text-success mt-3">
              {message}
            </p>
          }

        </div>

      </div>

      <div className="row my-4">
          <div className="col-12">
          {uudetLiput &&
            uudetLiput.map((lippu, index) => {
              return(
                <div>
                <h3>{"Lippu " + (index + 1)}</h3>
                <table className="table mb-4">
                <tbody>
                    <tr>
                      <th scope="row">Koodi</th>
                      <td>{lippu.koodi}</td>
                    </tr>
                    <tr>
                      <th scope="row">Lipputyyppi</th>
                      <td>{lippu.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi}</td>
                    </tr>
                    <tr>
                      <th scope="row">Hinta</th>
                      <td>{lippu.hinta + " €"}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tila</th>
                      <td>{lippu.tila.tila}</td>
                    </tr>
                    <tr>
                      <th scope="row">Myyntipäivä</th>
                      <td>{lippu.myynti.myyntipaiva}</td>
                    </tr>
                    <tr>
                      <th scope="row">Maksutapa</th>
                      <td>{lippu.myynti.maksutapa.maksutapa}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tarkastuspvm</th>
                      <td>{lippu.tila.tarkastus_pvm ? lippu.tarkastuspvm : '-'}</td>
                    </tr>
                    <tr>
                      <th scope="row">Tarkastanut</th>
                      <td>{lippu.tila.tarkastanut ? lippu.tarkastanut : '-'}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
              )
            })



        }
          </div>
      </div>
    </>
  )
}