import { useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";

export type TLipputyyppi = {
    lipputyyppi_id: Number;
    lipputyyppi: String;
}

export type Tapahtuma = {
    tapahtuma_id: number;
    nimi: string;
    paivamaara: string;
    kuvaus: string;
    lippumaara: number;
};

export type TTapahtumapaikka = {
    tapahtumapaikka_id: number;
    nimi: string;
    katuosoite: string;
    postinumero: string;
    kaupunki: string;
    maa: string;
    maksimi_osallistujat: number,
    tapahtuma_id: number,
}

<<<<<<< HEAD
=======
//const base_url = 'http://localhost:8080/api';

>>>>>>> readmeViimeistely
export default function LuoTapahtumaComponent() {
    const [tapahtumanNimi, setTapahtumanNimi] = useState<string>("");
    const [tapahtumanKuvaus, setTapahtumanKuvaus] = useState<string>("");
    const [paivaMaara, setPaivaMaara] = useState<string>(new Date().toISOString().slice(0, 16));;
    const [lippuMaara, setLippuMaara] = useState<number>(0);
    const [message, setMessage] = useState("");
    const [uusiTapahtuma, setUusiTapahtuma] = useState<Tapahtuma | null>(null);
   // const [lipputyypit, setLipputyypit] = useState<TLipputyyppi[]>([]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPaivaMaara(e.target.value); // Päivittää valitulla arovolla
    };

        const createTapahtuma = async () => {
            // Luodaan tapahtuma mutta tarkastetaan ensin, että jos tapahtumapaikka on valittu, lisätään se
          
            let jsondata = JSON.stringify({
                nimi: tapahtumanNimi,
                paivamaara: paivaMaara,
                kuvaus: tapahtumanKuvaus,
                lippumaara: lippuMaara,
            });

            console.log("TAPAHTUMAN JSON DATA: ",jsondata);

            try {
                const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {             
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                    },
                    body: jsondata
                })
                if (!response.ok) {
                    throw new Error("Failed to create tapahtuma");
                }
                const data = await response.json();
                setUusiTapahtuma(data);          
                setMessage('Tapahtuma luotu tietokantaan onnistuneesti');
            } catch (error) {
                window.alert("uuden tapahtuman luonti epäonnistui. "+error);
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
                        <label htmlFor="maara-input" className="form-label">Myytävien lippujen määrä</label>
                        <input
                            value={lippuMaara}
                            onChange={e => setLippuMaara(Number(e.target.value))}
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

