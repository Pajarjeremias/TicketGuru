import { ChangeEvent, useEffect, useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";

export default function MyyLippuComponent() {
  const [valittuTapahtumaId, setValittuTapahtumaId] = useState("-1");
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
  const [myydytLiput, setMyydytLiput] = useState("-");
  const [maxLiput, setMaxLiput] = useState("-");
  const [valittuLipputyyppiId, setValittuLipputyyppiId] = useState("-1");
  const [lippuMaara, setLippuMaara] = useState("1");
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const [uudetLiput, setUudetLiput] = useState<any[]>([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetchTapahtumat();
  }, [])

  const reset = () => {
    setMyydytLiput("-");
    setMaxLiput("-");
    setValittuLipputyyppiId("-1");
    setLippuMaara("1");
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
        setKaikkiTapahtumat(data);
      }

    } catch(e) {
      console.error(e)
    }
  }

  const setTapahtumaIdAndFetchTicketAmount = async (e: ChangeEvent<HTMLSelectElement>) => {
    setValittuTapahtumaId(e.target.value);

    if (e.target.value !== "-1") {
      const res = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat/${e.target.value}/myydytliput`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
        }
      })
      setMyydytLiput(await res.json());
      if(kaikkiTapahtumat) {
        const valittuTap = kaikkiTapahtumat.find(t => t.tapahtuma_id == e.target.value);
        setMaxLiput(valittuTap.lippumaara.toString());
      }
    } else {
      reset();
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
      for (let i = 0; i < parseInt(lippuMaara); i++) {
        const res = await fetch(`${scrummeriConfig.apiBaseUrl}/liput`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
          },
          body: JSON.stringify({
            myynti_id: myyntiId,
            tapahtuman_lipputyypit_id: parseInt(valittuLipputyyppiId),
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            hinta: (kaikkiTapahtumat.find(t => t.tapahtuma_id == valittuTapahtumaId).tapahtuman_lipputyypit.find((l: any) => l.tapahtuma_lipputyyppi_id == valittuLipputyyppiId)).hinta
          })
        })
        holder.push(await res.json());
      }
      return Promise.all(holder);
    }
    setUudetLiput(await postLoop());
    setMessage('Liput luotu tietokantaan onnistuneesti');
    setValittuTapahtumaId("-1");
    reset();
  }


  return(
    <>
      <div className="row my-4">
        <h2 className="pb-2">Myy lippuja</h2>
        <div className="col-12">
          <label htmlFor="tapahtuma-select" className="form-label">Tapahtuma</label>
          <select
            value={valittuTapahtumaId}
            onChange={e => setTapahtumaIdAndFetchTicketAmount(e)}
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
            <b>Myydyt liput: </b>{myydytLiput}/{valittuTapahtumaId === "-1" ? "-" : maxLiput}
          </div>

          <label htmlFor="lipputyyppi-select" className="form-label mt-3">Lipputyyppi</label>
          <select
            value={valittuLipputyyppiId}
            onChange={e => setValittuLipputyyppiId(e.target.value)}
            className="form-select mb-2"
            id="lipputyyppi-select"
          >
            <option value={"-1"}></option>
            {valittuTapahtumaId != "-1" &&
              // eslint-disable-next-line @typescript-eslint/no-explicit-any
              kaikkiTapahtumat.find(t => t.tapahtuma_id == valittuTapahtumaId)?.tapahtuman_lipputyypit.map((lipputyyppi: any) => {
                return (
                  <option value={lipputyyppi.tapahtuma_lipputyyppi_id} key={lipputyyppi.tapahtuma_lipputyyppi_id}>
                    {lipputyyppi.lipputyyppi.lipputyyppi}
                  </option>
                )
              })
            }
          </select>

          <label htmlFor="maara-input" className="form-label">Määrä</label>
          <input
            value={lippuMaara}
            onChange={e => setLippuMaara(e.target.value)}
            min={1}
            max={10}
            type="number"
            className="form-control"
            id="maara-input">
          </input>

          <button
            className="btn btn-primary mt-3"
            onClick={createTickets}
            disabled={valittuTapahtumaId === "-1" || valittuLipputyyppiId === "-1" ? true : false}
          >
            Myy liput
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