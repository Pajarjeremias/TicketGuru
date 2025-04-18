import { useState, useEffect } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";


export default function MyyLippuComponent() {
    const [tapahtumat, setTapahtumat] = useState([ ]);
    const [lippuForm, setLippuForm] = useState({
      tapahtuma: '',
      lipputyyppiId: 0,
      maara: 1,
      hinta: 0
    });

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

  return (
    <>
      <div className="row my-4">
        <h2 className="pb-2">Myy lippu</h2>
        <div className="col-6">

          <label htmlFor="tapahtuma-select" className="form-label">Valitse tapahtuma</label>
          <select defaultValue={""} id="tapahtuma-select" className="form-select mb-2" onChange={(event) => setLippuForm({...lippuForm, tapahtuma: event.target.value})}>
            <option disabled value="" key={0}>Tapahtuma</option>
            {tapahtumat[0] && 
              tapahtumat.map(tapahtuma => {
                return(
                  <option value={tapahtuma.nimi} key={tapahtuma.tapahtuma_id}>
                    {tapahtuma.nimi}
                  </option>
                )
            })}
          </select>

          <label htmlFor="tapahtuma-select" className="form-label">Valitse lipputyyppi</label>
          <select defaultValue={""} id="tapahtuma-select" className="form-select mb-2" aria-label="Default select example" disabled={lippuForm.tapahtuma == '' ? true : false} onChange={(event) => setLippuForm({...lippuForm, lipputyyppiId: parseInt(event.target.value)})}>
            <option disabled value="" key={0}>Lipputyyppi</option>
            {tapahtumat && lippuForm.tapahtuma &&
              tapahtumat.find(tapahtuma => tapahtuma.nimi == lippuForm.tapahtuma)?.tapahtuman_lipputyypit.map(lipputyyppi => {
                return (
                  <option value={lipputyyppi.tapahtuma_lipputyyppi_id} key={lipputyyppi.tapahtuma_lipputyyppi_id}>
                    {lipputyyppi.lipputyyppi.lipputyyppi}
                    </option>
                  )
                })
              }
          </select>

          <label htmlFor="exampleFormControlInput1" className="form-label">Määrä</label>
          <input type="number" className="form-control" id="exampleFormControlInput1" value={lippuForm.maara} disabled={lippuForm.lipputyyppiId == 0 ? true : false} onChange={event => setLippuForm({...lippuForm, maara: parseInt(event.target.value)})}></input>
                
          <button className="btn btn-primary mt-3">
            Myy liput
          </button>
        </div>

      </div>
    </>
  )
}