import {useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";
// import DatePicker from "react-datepicker";

export default function LuoTapahtumaComponent() {
    const [tapahtumanNimi, setTapahtumanNimi] = useState<string>("");
    const [tapahtumanKuvaus, setTapahtumanKuvaus] = useState<string>("");
    const [paivaMaara, setPaivaMaara] = useState<string>(new Date().toISOString().slice(0, 16));;
    const [lippuMaara, setLippuMaara] = useState("0");
    const [message, setMessage] = useState("");
    const [uusiTapahtuma, setUusiTapahtuma] = useState<any>(null);

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
            if (uusiTapahtuma!==null){
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
                <h2>Luo tapahtuma</h2>
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

            {uusiTapahtuma && (
                <div className="row my-4">
                    <div className="col-12">
                        <h3>Uuden tapahtuman tiedot</h3>
                        <table className="table mb-4">
                            <tbody>
                                <tr>
                                    <th scope="row">Tapahtuman ID</th>
                                    <td>{uusiTapahtuma.tapahtuma_id}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Nimi</th>
                                    <td>{uusiTapahtuma.nimi}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Päivämäärä</th>
                                    <td>{uusiTapahtuma.paivamaara}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Kuvaus</th>
                                    <td>{uusiTapahtuma.kuvaus}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Lippujen määrä</th>
                                    <td>{uusiTapahtuma.lippumaara}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            )}

        </>
    )
}

