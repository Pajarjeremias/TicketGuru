import { useState } from "react"
import { config as defaultConfig } from "../config/default";
import { config as pixeliConfig } from "../config/pixeli";
import { config as scrummeriConfig } from "../config/scrummerit";

export default function MainComponent() {
  const [koodi, setKoodi] = useState('');
  const [id, setId] = useState('');
  const [envi, setEnvi] = useState('Localhost');

  const getTicketInfo = async () => {
    console.log(defaultConfig.apiBaseUrl)
    return;
  }

  const markTicketAsUsed = async () => {
    console.log(defaultConfig.apiBaseUrl)
    return;
  }

  return(
    <>
    <div className="container">
      <div className="row my-4">
        <div className="col-12">
        Valitse käytettävä ympäristö:
        <div className="form-check">
          <input type="radio" value="Localhost" name="envi" className="form-check-input" checked={envi === "Localhost"} onChange={e => setEnvi(e.target.value)}/> Localhost
        </div>
        <div className="form-check">
        <input type="radio" value="Pixelipioneerit" name="envi" className="form-check-input" checked={envi === "Pixelipioneerit"} onChange={e => setEnvi(e.target.value)}/> Pixelipioneerit
        </div>
        <div className="form-check">
        <input type="radio" value="Scrummerit" name="envi" className="form-check-input" checked={envi === "Scrummerit"} onChange={e => setEnvi(e.target.value)} /> Scrummerit
        </div>
        </div>
      </div>
      <div className="row my-4">
        <div className="col-6 pe-4">
          <h2>Hae Lippu koodilla</h2>
          <div className="mt-3 mb-2">Syötä haettavan lipun koodi ja paina "Hae lipun tiedot"</div>
          <div className="mb-3">
            <input type="text" className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän koodi" onChange={e => setKoodi(e.target.value)}></input>
            <button className="btn btn-primary mt-2" onClick={getTicketInfo}>Hae lipun tiedot</button>
          </div>
        </div>
        <div className="col-6">
        <h2>Merkitse lippu käytetyksi</h2>
          <div className="mt-3 mb-2">Syötä käytetyksi merkattavan lipun ID ja paina "Merkitse käytetyksi"</div>
          <div className="mb-3">
            <input type="text" className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän id" onChange={e => setId(e.target.value)}></input>
            <button className="btn btn-primary mt-2" onClick={markTicketAsUsed}>Merkitse käytetyksi</button>
          </div>
        </div>
      </div>
    </div>
    </>

  )
}