import { useState } from "react"
import { config as defaultConfig } from "../config/default";
//import { config as pixeliConfig } from "../config/pixeli";
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

export default function MainComponent() {
  const [koodi, setKoodi] = useState('');
  const [id, setId] = useState('');
  const [envi, setEnvi] = useState('Localhost');
  const [scrummeritLippu, setScrummeritLippu] = useState(tyhjaScrummeritLippu);
  const [errorMsg, setErrorMsg] = useState('');
  //const [loading, setLoading] = useState(false);

  const getTicketInfo = async () => {
    let result;
    let data;


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
          console.log(scrummeritLippu)
        } else {
          setErrorMsg(result.status + " Lipun haku ei onnistunut.")
          setScrummeritLippu(tyhjaScrummeritLippu);
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
            console.log(scrummeritLippu)
          } else {
            setErrorMsg(result.status + " Lipun haku ei onnistunut.")
            setScrummeritLippu(tyhjaScrummeritLippu);
          }
        } catch(e) {
          setScrummeritLippu(tyhjaScrummeritLippu)
          if (e instanceof TypeError) {
            setErrorMsg("CORS-virhe")
          } else {
            setErrorMsg("Tuntematon virhe")
          }
        }
        break;

      default:
    }

  }

  const markTicketAsUsed = async () => {
    console.log(defaultConfig.apiBaseUrl)
    console.log(id)
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
        <input type="radio" value="Pixelipioneerit" name="envi" className="form-check-input" checked={envi === "Pixelipioneerit"} onChange={e => setEnvi(e.target.value)}/> Pixelipioneerit Rahti
        </div>
        <div className="form-check">
        <input type="radio" value="Scrummerit" name="envi" className="form-check-input" checked={envi === "Scrummerit"} onChange={e => setEnvi(e.target.value)} /> Scrummerit Rahti
        </div>
        </div>
      </div>
      <div className="row my-4">
        <div className="col-6 pe-4">
          <h2>Hae Lippu koodilla</h2>
          <div className="mt-3 mb-2">Syötä haettavan lipun koodi ja paina "Hae lipun tiedot"</div>
          <div className="mb-3">
            <input type="text" className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän koodi" onChange={e => setKoodi(e.target.value)}></input>

            <button className="btn btn-primary mt-2" onClick={getTicketInfo}>
              Hae lipun tiedot

            </button>
          </div>

          {errorMsg &&
            <p className="text-danger">{errorMsg}</p>
          }

          {scrummeritLippu.id &&
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
          }
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