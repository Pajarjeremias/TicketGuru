import { ChangeEvent, useEffect, useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";


export default function TapahtumanMyynnitTyypeittainComponent() {
  const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
  const [valittuTapahtumaId, setValittuTapahtumaId] = useState("-1");
  const [myynnit, setMyynnit] =useState<any[]>([]);
  
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

  
  const fetchMyynnit = async () => {
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
        type LipputTiedot = {
            lipputyyppi: number;
            kpl: number; 
            hinta: number; 
        };
        let lipputiedot: LipputTiedot[] = suodatetutLiput.map((lippu: any) => {
            return(
                {
                    lipputyyppi: lippu.tapahtuman_lipputyyppi.lipputyyppi.lipputyyppi,
                    kpl: 1,
                    hinta: lippu.hinta
                }
            )
        })
        let myynnitTyypeittain = lipputiedot.reduce<Record<number, { lipputyyppi: number, kpl: number, hinta: number }>>(
            (acc, curr) => {
                if(acc[curr.lipputyyppi]) {
                  acc[curr.lipputyyppi].hinta += curr.hinta;
                  acc[curr.lipputyyppi].kpl += 1;
                } else{
                  acc[curr.lipputyyppi] = {lipputyyppi: curr.lipputyyppi, hinta: curr.hinta, kpl: curr.kpl};
                }
                return acc;
              }, {}
        );
        setMyynnit(Object.values(myynnitTyypeittain));
      }

    } catch(e) {
      console.error(e)
    }

  }

  return(
    <>
      <div className="row my-4">
        <h2 className="pb-2">Tapahtuman myynnit</h2>
        

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
          <button className="btn btn-primary" onClick={fetchMyynnit}>
            Hae liput
          </button>
        </div>

        </div>

      </div>

      <div className="row my-4">
        <div className="col-12">
          {kaikkiTapahtumat.length != 0 && myynnit &&
            <table className="table mb-4">
              <thead>
                <tr>
                  <th>Lipputyyppi</th>
                  <th>Kpl</th>
                  <th>Yhteensä</th>
                </tr>
              </thead>

              <tbody>
                {kaikkiTapahtumat && myynnit &&
                  myynnit.map((lt) => {
                    return(
                      <tr key={lt.lipputyyppi}>
                        <td>{lt.lipputyyppi}</td>
                        <td>{lt.kpl}</td>
                        <td>{lt.hinta.toFixed(2)} €</td>
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