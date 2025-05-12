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
    lippumaara: string;
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

export type Ptoimipaikka = {
    postinumero: string;
    postitoimipaikka: string;
    maa: string;
}

export default function LuoTapahtumaPaikkaComponent() {
    const [paikanNimi, setPaikanNimi] = useState<string>("");
    const [katuosoite, setKatuosoite] = useState<string>("");
    const [maxOsallistujat, setMaxOsallistujat] = useState<number>(0);
    const [message, setMessage] = useState("");
    const [uusiTapahtumaPaikka, setUusiTapahtumaPaikka] = useState<TTapahtumapaikka | null>(null);
    const [valittuTapahtuma, setValittuTapahtuma] = useState<number | null>(-1);
    const [postinumero, setPostinumero] = useState<string>("");
    const [kaupunki, setKaupunki] = useState<string>("");
    const [maa, setMaa] = useState<string>("");
   // const [tapahtumat, setTapahtumat] = useState<Tapahtuma[]>([]);
    //    const [tapahtumaid, setTapahtumaid] = useState("-1");
    //   const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
    //   const [loading, setLoading] = useState<boolean>(true); // Loading state
    //   const [lipputyypit, setLipputyypit] = useState<TLipputyyppi[]>([]);


    const createTapahtumapaikka = async () => {
        let jsonrivi = "--";
        if (valittuTapahtuma===-1) {
            jsonrivi = JSON.stringify({
                nimi: paikanNimi,
                katuosoite: katuosoite,
                    postinumero: postinumero,
                    postitoimipaikka: kaupunki,
                    maa: maa,
                maksimi_osallistujat: maxOsallistujat,
            });
        } else  {
             jsonrivi = JSON.stringify({
            nimi: paikanNimi,
            katuosoite: katuosoite,
            maksimi_osallistujat: maxOsallistujat,
            tapahtuma_id: valittuTapahtuma,
        });
    }
        console.log('JSONINA lähetetaan paikkatiedot: ',jsonrivi);
        // Luodaan tapahtumapaikka
        try {
            const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumapaikat`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                },
                body: jsonrivi
            })
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Failed to create tapahtumapaikka: ${response.status} -${errorText}`);
            }
            const data = await response.json();
            setUusiTapahtumaPaikka(data);
                setMessage('Tapahtumapaikka luotu tietokantaan onnistuneesti');
        } catch (error) {
            window.alert("uuden tapahtuman luonti epäonnistui");
            console.error("Virhe luotaessa tapahtumaa: ", error);
            setMessage('Virhe tapahtuman luonnissa');
        }
        setValittuTapahtuma(-1);
    };


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

                    
                    {/* Input paikan postinumero */}
                    <label htmlFor="maara-input" className="form-label">Postinumero</label>
                    <input
                        value={postinumero}
                        onChange={e => setPostinumero(e.target.value)}
                        placeholder="Katuosoite"
                        className="form-control mb-2"
                    />

                    
                    {/* Input paikan Kaupunki */}
                    <label htmlFor="maara-input" className="form-label">Kaupunki</label>
                    <input
                        value={kaupunki}
                        onChange={e => setKaupunki(e.target.value)}
                        placeholder="Katuosoite"
                        className="form-control mb-2"
                    />

                    
                    {/* Input paikan Maa */}
                    <label htmlFor="maara-input" className="form-label">Maa</label>
                    <input
                        value={maa}
                        onChange={e => setMaa(e.target.value)}
                        placeholder="Katuosoite"
                        className="form-control mb-2"
                    />

                    {/* Input maksimiosallistujat */}
                    <label htmlFor="maara-input" className="form-label">Maksimi osallistujien määrä.</label>
                    <input
                        value={maxOsallistujat}
                        onChange={e => setMaxOsallistujat(Number(e.target.value))}
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
                        Luo tapahtumapaikka
                    </button>

                    {message &&
                        <p className="text-success mt-3">
                            {message}
                        </p>
                    }
                </div>
            </div>

            {uusiTapahtumaPaikka && (
                <div className="row my-4">
                    <div className="col-12">
                        <h3>Uuden tapahtumapaikan tiedot</h3>
                        <table className="table mb-4">
                            <tbody>
                                <tr>
                                    <th scope="row">Tapahtumapaikan ID</th>
                                    <td>{uusiTapahtumaPaikka.tapahtumapaikka_id}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Nimi</th>
                                    <td>{uusiTapahtumaPaikka.nimi}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Katuosoite</th>
                                    <td>{uusiTapahtumaPaikka.katuosoite}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Kaupunki</th>
                                    <td>{uusiTapahtumaPaikka.kaupunki}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Maksimi osallistujat</th>
                                    <td>{uusiTapahtumaPaikka.maksimi_osallistujat}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            )}

        </>
    )
}

