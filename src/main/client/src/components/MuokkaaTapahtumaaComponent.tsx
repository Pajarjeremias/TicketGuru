import { useEffect, useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";
// import DatePicker from "react-datepicker";

export default function LuoTapahtumaComponent() {
    const [tapahtumanNimi, setTapahtumanNimi] = useState<string>("null");
    const [tapahtumanKuvaus, setTapahtumanKuvaus] = useState<string>("null");
    const [paivaMaara, setPaivaMaara] = useState<string>(new Date().toISOString().slice(0, 16));;
    const [lippuMaara, setLippuMaara] = useState("0");
    const [message, setMessage] = useState("");
    const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
    const [uusiTapahtuma, setUusiTapahtuma] = useState<any>(null);
    const [loading, setLoading] = useState<boolean>(true); // Loading state

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPaivaMaara(e.target.value); // Päivittää valitulla arovolla
    };

    /*
    const handleSubmit = () => {
        console.log("Selected Date and Time:", paivaMaara);
    };

    const reset = () => {
        setTapahtumanNimi("");
        setTapahtumanKuvaus("");
        setPaivaMaara("");
        setLippuMaara("0");
    }
*/
    useEffect(() => {
        fetchTapahtumat();
    }, []);

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

        } catch (e) {
            console.error(e)
        }
    }


        const createTapahtuma = async () => {
            // Luodaan tapahtuma ja otetaan talteen tapahtumaiId
            try {
                const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                    },
                    body: JSON.stringify({
                        nimi: tapahtumanNimi,
                        paivamaara: paivaMaara,
                        kuvaus: tapahtumanKuvaus,
                        lippumaara: lippuMaara
                    })
                })
                const data = await response.json();
                setUusiTapahtuma(data);
                if (uusiTapahtuma !== null) {
                    setMessage('Tapahtuma luotu tietokantaan onnistuneesti');
                }
            } catch (error) {
                console.error("Virhe luotaessa tapahtumaa: ", error);
                setMessage('Virhe tapahtuman luonnissa');
            }
        };

        return (
            <>
                <div className="row my-4">
                    <table>
                        <h2>Tapahtumat</h2>

                        {loading ? (
                            <p>Loading...</p>
                        ) : kaikkiTapahtumat.length > 0 ? (
                            <table className="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nimi</th>
                                        <th>Päivämäärä</th>
                                        <th>Kuvaus</th>
                                        <th>Tapahtumapaikka</th>
                                        <th>Lipputyypit</th>
                                        <th>Lippujen määrä</th>
                                        <th>URL</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {kaikkiTapahtumat.map((tapahtuma) => (
                                        <tr key={tapahtuma.tapahtuma_id}>
                                            <td>{tapahtuma.tapahtuma_id}</td>
                                            <td>{tapahtuma.nimi}</td>
                                            <td>{tapahtuma.paivamaara}</td>
                                            <td>{tapahtuma.kuvaus}</td>
                                            <td>{tapahtuma.tapahtumapaikka || "-"}</td>
                                            <td>
                                                {tapahtuma.tapahtuman_lipputyypit
                                                    ? JSON.stringify(tapahtuma.tapahtuman_lipputyypit)
                                                    : "-"}
                                            </td>
                                            <td>{tapahtuma.lippumaara}</td>
                                            <td>{tapahtuma.url || "-"}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        ) : (
                            <p>No tapahtumat found.</p>
                            
                        )}
                        setLoading('not loading');
                    </table>
                </div>


                <div className="row my-4">
                    <h2>Muokkaa tapahtumaa</h2>
                    <div className="col-12">

                        {/* Input Tapahtuman Nimi */}
                        <label htmlFor="maara-input" className="form-label">Tapahtuman nimi</label>
                        <input
                            value={tapahtumanNimi}
                            onChange={e => setTapahtumanNimi(e.target.value)}
                            placeholder="Tapahtuman nimi"
                            className="form-control mb-2"
                        />

                        {/* Input Tapahtuman Kuvaus */}
                        <label htmlFor="maara-input" className="form-label">Tapahtuman kuvaus</label>
                        <input
                            value={tapahtumanKuvaus}
                            onChange={e => setTapahtumanKuvaus(e.target.value)}
                            placeholder="Tapahtuman kuvaus"
                            className="form-control mb-2"
                        />

                        {/* Input Tapahtuman Päivämäärä */}
                        <label htmlFor="maara-input" className="form-label">Tapahtuman päivämäärä</label>
                        <input
                            id="datetime-picker"
                            type="datetime-local"
                            value={paivaMaara}
                            onChange={handleChange}
                            placeholder="Tapahtuman päivämäärä ja aika. Täytyy olla muotoa 2025-05-11T11:16:00"
                            className="form-control mb-2"
                        />

                        {/* Input Lippu Määrä */}
                        <label htmlFor="maara-input" className="form-label">Määrä</label>
                        <input
                            value={lippuMaara}
                            onChange={e => setLippuMaara(e.target.value)}
                            min={1}
                            max={500000}
                            type="number"
                            className="form-control"
                            id="maara-input">
                        </input>


                        <button
                            className="btn btn-primary mt-3"
                            onClick={createTapahtuma}
                        >
                            Luo tapahtuma
                        </button>

                        {message &&
                            <p className="text-success mt-3">
                                {message}
                            </p>
                        }
                    </div>
                </div>


            </>
        )
    }

