import { useEffect, useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";
import EditTapahtuma from "./EditTapahtuma";
// import DatePicker from "react-datepicker";

export default function LuoTapahtumaComponent() {
    const [tapahtumanNimi, setTapahtumanNimi] = useState<string>("null");
    const [tapahtumanKuvaus, setTapahtumanKuvaus] = useState<string>("null");
    const [paivaMaara, setPaivaMaara] = useState<string>(new Date().toISOString().slice(0, 16));
    const [lippuMaara, setLippuMaara] = useState("0");
    const [message, setMessage] = useState("");
    const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
    const [uusiTapahtuma, setUusiTapahtuma] = useState<any>(null);
    const [loading, setLoading] = useState<boolean>(true); // Loading state
    const [valittuTapahtumaId, setValittuTapahtumaId] = useState("-1");
    const [liput, setLiput] = useState<any[]>([]);

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
        console.log("TAPAHTUMISTA HAETTIIN NÄMÄ TIEDOT", kaikkiTapahtumat);
    }, []);

    const fetchTapahtumat = async () => {
        console.log("TAPAHTUMIEN HAKUOSOITE:", `${scrummeriConfig.apiBaseUrl}/tapahtumat`,)
        try {
            const result = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Basic ${btoa("yllapitaja:yllapitaja")}`,
                },
            });

            if (result.ok) {
                const data = await result.json();
                setKaikkiTapahtumat(data);

            } else {
                window.alert("Tapahtumien haku epäonnistui " + result.status + " " + result.statusText);
            }
        } catch (error) {
            console.error("Error fetching tapahtumat:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleEdit = (index: number, id: number) => {
        let nimi = kaikkiTapahtumat[index].nimi;
        console.log("tapahtuman nimi indexillä:",index," nimi ",nimi);
        EditTapahtuma(kaikkiTapahtumat[index], index);
    }



    const createTapahtuma = async () => {
        // Luodaan tapahtuma ja otetaan talteen tapahtumaiId
        try {
            const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Basic ${btoa("yllapitaja:yllapitaja")}`,
                },
                body: JSON.stringify({
                    nimi: tapahtumanNimi,
                    paivamaara: paivaMaara,
                    kuvaus: tapahtumanKuvaus,
                    lippumaara: lippuMaara,
                }),
            });

            if (response.ok) {
                const data = await response.json();
                setUusiTapahtuma(data);
                setMessage("Tapahtuma luotu tietokantaan onnistuneesti");
                fetchTapahtumat();
            } else {
                window.alert("Virhe tapahtuman luonnissa");
                setMessage("Virhe tapahtuman luonnissa");
            }
        } catch (error) {
            console.error("Virhe luotaessa tapahtumaa:", error);
            setMessage("Virhe tapahtuman luonnissa");
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
                                {kaikkiTapahtumat.map((tapahtuma, index) => (
                                    <tr key={tapahtuma.tapahtuma_id}>
                                        <td>{tapahtuma.tapahtuma_id}</td>
                                        <td>{tapahtuma.nimi}</td>
                                        <td>{tapahtuma.paivamaara}</td>
                                        <td>{tapahtuma.kuvaus}</td>
                                        <td>{tapahtuma.tapahtumapaikka || "-"}</td>
                                        <td style={{minWidth: "22ch", maxWidth: "32ch", overflow: "hidden"}}>
                                            {tapahtuma.tapahtuman_lipputyypit ? (
                                                tapahtuma.tapahtuman_lipputyypit.map((lipputyyppiObj: any, index : number) => (
                                                    <div key={index}>
                                                        lipputyyppi: {lipputyyppiObj.lipputyyppi.lipputyyppi}<br></br> hinta: {lipputyyppiObj.hinta}
                                                    </div>
                                                ))
                                            ) : (
                                                "-"
                                            )}
                                        </td>
                                        <td>{tapahtuma.lippumaara}</td>
                                        <td>{tapahtuma.url || "-"}</td>
                                        <td className="edit-column"><button onClick={() => handleEdit(index, tapahtuma.tapahtuma_id)}>Edit</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No tapahtumat found.</p>

                    )}

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

