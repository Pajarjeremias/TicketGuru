import { useState, useEffect } from "react";
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
    lippumaara: string;
};

export type Ptoimipaikka = {
    postinumero : string;
    postitoimipaikka : string;
    maa : string;
}

export default function LuoTapahtumaPaikkaComponent() {
    const [paikanNimi, setPaikanNimi] = useState<string>("");
    const [katuosoite, setKatuosoite] = useState<string>("");
    const [postinumero, setPostinumero] = useState<string>("");
    const [kaupunki, setKaupunki] = useState<string>("");
    const [maa, setMaa] = useState<string>("");
    const [maxOsallistujat, setMaxOsallistujat] = useState("0");
    const [message, setMessage] = useState("");
    const [uusiTapahtuma, setUusiTapahtuma] = useState<Tapahtuma | null>(null);
    const [lipputyypit, setLipputyypit] = useState<TLipputyyppi[]>([]);


        const createTapahtumapaikka = async () => {
            // Luodaan tapahtuma ja otetaan talteen tapahtumaiId
            try {
                const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumapaikat`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                    },
                    body: JSON.stringify({
                        nimi: paikanNimi,
                        katuosoite: katuosoite,
                        postinumero: postinumero,
                        kaupunki : kaupunki,
                        maa : maa,
                        maksimi_osallistujat : maxOsallistujat,
                    })
                })
                if (!response.ok) {
                    throw new Error("Failed to create tapahtumapaikka");
                }
                const data = await response.json();
                setUusiTapahtuma(data);
                if (uusiTapahtuma !== null) {
                    setMessage('Tapahtumapaikka luotu tietokantaan onnistuneesti');
                }

            } catch (error) {
                window.alert("uuden tapahtuman luonti epäonnistui");
                console.error("Virhe luotaessa tapahtumaa: ", error);
                setMessage('Virhe tapahtuman luonnissa');
            }
        };

        const fetchLipputyypit = async () => {
            console.log("LIPPUTYYPIT OSOITTEESTA:",`${scrummeriConfig.apiBaseUrl}/lipputyypit`);
            try {
                const response = await fetch(`${scrummeriConfig.apiBaseUrl}/lipputyypit`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`,
                    }
                })
                if (!response.ok) {
                    window.alert("Lipputyyppien haku epäonnistui");
                    throw new Error("Failed to fetch lipputyypit");
                }
                const data = await response.json();
                setLipputyypit(data._embedded.lipputyypit || []);
                if (lipputyypit != null) {
                    console.log('haettiin lipputyypit', lipputyypit);
                }
            } catch (e) {
                console.error(e)
            }
        }

        useEffect(() => {
            fetchLipputyypit();
            console.log("Lipputyypit:",lipputyypit);
        }, []);

        return (
            <>
                <div className="row my-4">
                    <h2>Luo tapahtumapaikka</h2>
                    <div className="col-12">

                        {/* Input tapahtumapaikan Nimi */}
                        <label htmlFor="maara-input" className="form-label">Tapahtumapaikan nimi</label>
                        <input
                            value={paikanNimi}
                            onChange={e => setPaikanNimi(e.target.value)}
                            placeholder="Tapahtumanpaikan nimi"
                            className="form-control mb-2"
                        />

                        {/* Input paikan katuosoite */}
                        <label htmlFor="maara-input" className="form-label">Katuosoite</label>
                        <input
                            value={katuosoite}
                            onChange={e => setKatuosoite(e.target.value)}
                            placeholder="Katuosoite"
                            className="form-control mb-2"
                        />

                        {/* Input postinumero */}
                        <label htmlFor="maara-input" className="form-label">Postinumero</label>
                        <input
                            value={postinumero}
                            onChange={e => setPostinumero(e.target.value)}
                            placeholder="Postinumero"
                            className="form-control mb-2"
                        />

                        
                        {/* Input kaupunki */}
                        <label htmlFor="maara-input" className="form-label">Kaupunki</label>
                        <input
                            value={kaupunki}
                            onChange={e => setKaupunki(e.target.value)}
                            placeholder="Postinumero"
                            className="form-control mb-2"
                        />

                        
                        {/* Input postinumero */}
                        <label htmlFor="maara-input" className="form-label">Maa</label>
                        <input
                            value={maa}
                            onChange={e => setMaa(e.target.value)}
                            placeholder="Postinumero"
                            className="form-control mb-2"
                        />

                        {/* Input maksimiosallistujat */}
                        <label htmlFor="maara-input" className="form-label">Maksimi osallistujien määrä</label>
                        <input
                            value={maxOsallistujat}
                            onChange={e => setMaxOsallistujat(e.target.value)}
                            min={1}
                            max={500000}
                            type="number"
                            className="form-control"
                            id="maara-input">
                        </input>

                        <button
                            className="btn btn-primary mt-3"
                            onClick={createTapahtumapaikka}
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

