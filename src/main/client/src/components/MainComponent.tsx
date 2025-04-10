
export default function MainComponent() {
  return(
    <>
    <div className="container">
      <div className="row my-4">
        <div className="col-6 pe-4">
          <h2>Hae Lippu koodilla</h2>
          <div className="mt-3 mb-2">Syötä haettavan lipun koodi ja paina "Hae lipun tiedot"</div>
          <div className="mb-3">
            <input type="text" className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän koodi"></input>
            <button className="btn btn-primary mt-2">Hae lipun tiedot</button>
          </div>
        </div>
        <div className="col-6">
        <h2>Merkitse lippu käytetyksi</h2>
          <div className="mt-3 mb-2">Syötä käytetyksi merkattavan lipun ID ja paina "Merkitse käytetyksi"</div>
          <div className="mb-3">
            <input type="text" className="form-control" id="exampleFormControlInput1" placeholder="Kirjoita tähän id"></input>
            <button className="btn btn-primary mt-2">Merkitse käytetyksi</button>
          </div>
        </div>
      </div>
    </div>
    </>

  )
}