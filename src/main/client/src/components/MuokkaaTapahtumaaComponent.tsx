/* eslint-disable @typescript-eslint/no-explicit-any */
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
    const [selectedTapahtuma, setSelectedTapahtuma] = useState<any>();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPaivaMaara(e.target.value); // Päivittää valitulla arovolla
    };


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

    const handleEdit = (index: number) => {
        const tapahtuma = kaikkiTapahtumat[index];
        console.log("Tapahtuman nimi indexillä:", index, " nimi:", tapahtuma.nimi);
        setSelectedTapahtuma({ tapahtuma, index });
    };

    const handleSave = (updatedTapahtuma: any) => {
        console.log("MUOKATTU TAPAHTUMA:", updatedTapahtuma);

        const saveTapahtumat = async () => {
            try {
                const thisId = updatedTapahtuma.id;
                const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat/${thisId}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Basic ${btoa("yllapitaja:yllapitaja")}`,
                    },
                    body: JSON.stringify({
                        nimi: updatedTapahtuma.nimi,
                        paivamaara: updatedTapahtuma.paivamaara,
                        kuvaus: updatedTapahtuma.kuvaus,
                        lippumaara: updatedTapahtuma.lippumaara
                    })
                });
                if (response.ok) {
                    const data = await response.json();
                    setMessage("Tapahtuma päivitetty tietokantaan onnistuneesti");
                    fetchTapahtumat();
                }
            } catch (error) {
                window.alert("Virhe tapahtuman muokkauksen tallennuksessa");
                console.error("Virhe muokatessa tapahtumaa:", error);
                setMessage("Virhe tapahtuman muokatessa");
            }
        }

        saveTapahtumat();
        setSelectedTapahtuma(null);
    };

    return (
        <>
            <div className="row my-4">
                {selectedTapahtuma && (
                    <EditTapahtuma
                        tapahtuma={selectedTapahtuma.tapahtuma}
                        index={selectedTapahtuma.index}
                        onSave={handleSave}
                    />
                )}

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
                                    <th></th>
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
                                        <td style={{ minWidth: "22ch", maxWidth: "32ch", overflow: "hidden" }}>
                                            {tapahtuma.tapahtuman_lipputyypit ? (
                                                tapahtuma.tapahtuman_lipputyypit.map((lipputyyppiObj: any, index: number) => (
                                                    <div key={index}>
                                                        lipputyyppi: {lipputyyppiObj.lipputyyppi.lipputyyppi}<br></br> hinta: {lipputyyppiObj.hinta}
                                                    </div>
                                                ))
                                            ) : (
                                                "-"
                                            )}
                                        </td>
                                        <td>{tapahtuma.lippumaara}</td>
                                        <td className="edit-column"><button className="btn btn-success" onClick={() => handleEdit(index)}>Edit</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No tapahtumat found.</p>

                    )}

                </table>
            </div>

        </>
    )
}

