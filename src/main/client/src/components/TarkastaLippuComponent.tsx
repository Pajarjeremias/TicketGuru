import { tarkastaLippu} from './screens/TarkastaLippu';

export default function TarkastaLippuComponent() {
  const {
    koodi,
    setKoodi,
    scrummeritLippu,
    getTicket,
    markAsUsed,
    successMsg,
    errorMsg,
    loading
  } = tarkastaLippu();

  return (
    <>
      <div className="row my-4">
        <div className="col-12">
          <h2>Tarkasta lippu</h2>
        </div>
      </div>
  
      <div className="row">
        <div className="col-6">
          <input
            value={koodi}
            onChange={e => setKoodi(e.target.value)}
            placeholder="Lipun koodi"
            className="form-control mb-2"
          />
          <button
            className="btn btn-primary me-2"
            onClick={getTicket}
            disabled={loading}
          >
            Hae lipun tiedot
          </button>
          <button
            className="btn btn-success"
            onClick={markAsUsed}
            disabled={loading}
          >
            Merkitse käytetyksi
          </button>
        </div>
      </div>
  
      <div className="row my-4">
        <div className="col-12">
          {successMsg && (
            <div className="alert alert-success" role="alert">
              {successMsg}
            </div>
          )}
          {errorMsg && (
            <div className="alert alert-danger" role="alert">
              {errorMsg}
            </div>
          )}
        </div>
      </div>
  
      {scrummeritLippu.id && (
        <div className="row">
          <div className="col-12">
            <h3>Lipun tiedot</h3>
            <table className="table table-bordered">
              <tbody>
                <tr>
                  <th scope="row">ID</th>
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
                  <td>{scrummeritLippu.hinta} €</td>
                </tr>
                <tr>
                  <th scope="row">Tila</th>
                  <td>{scrummeritLippu.tila.tila}</td>
                </tr>
                <tr>
                  <th scope="row">Tarkastettu</th>
                  <td>{scrummeritLippu.tarkastuspvm || 'Ei vielä'}</td>
                </tr>
                <tr>
                  <th scope="row">Tarkastanut</th>
                  <td>{scrummeritLippu.tarkastanut || '-'}</td>
                </tr>
                <tr>
                  <th scope="row">Myyntipäivä</th>
                  <td>{scrummeritLippu.myynti.myyntipaiva}</td>
                </tr>
                <tr>
                  <th scope="row">Maksutapa</th>
                  <td>{scrummeritLippu.myynti.maksutapa}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      )}
    </>
  );
}
