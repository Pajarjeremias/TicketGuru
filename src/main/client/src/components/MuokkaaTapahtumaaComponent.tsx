import { useEffect, useState } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";
import EditTapahtuma from "./EditTapahtuma";

export default function MuokkaaTapahtumaaComponent() {
    const [kaikkiTapahtumat, setKaikkiTapahtumat] = useState<any[]>([]);
    const [loading, setLoading] = useState<boolean>(true); // Loading state
    const [selectedTapahtuma, setSelectedTapahtuma] = useState<any>();

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
        console.log("HAETUT TAPAHTUMAT:", kaikkiTapahtumat);
    };

    const handleEdit = (index: number) => {
        const tapahtuma = kaikkiTapahtumat[index];
        console.log("Tapahtuman nimi indexillä:", index, " nimi:", tapahtuma.nimi);
        setSelectedTapahtuma({ tapahtuma, index });
    };

    const handleSave = (updatedTapahtuma: any) => {
        console.log("MUOKATTU TAPAHTUMA:", updatedTapahtuma.tapahtumapaikka_id);

        const saveTapahtumat = async () => {
            console.log(updatedTapahtuma);
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
                        lippumaara: updatedTapahtuma.lippumaara,
                        tapahtumapaikka_id: updatedTapahtuma.tapahtumapaikka_id,
                    })
                });
                if (response.ok) {
                    fetchTapahtumat();
                }

            } catch (error) {
                window.alert("Virhe tapahtuman muokkauksen tallennuksessa");
                console.error("Virhe muokatessa tapahtumaa:", error);
                //setMessage("Virhe tapahtuman muokatessa");
            }
        }

        fetchTapahtumat();
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
                                        <td>
                                            {tapahtuma.tapahtumapaikka ? (
                                                <div>
                                                    {tapahtuma.tapahtumapaikka.nimi}<br />
                                                    osoite: {tapahtuma.tapahtumapaikka.katuosoite}<br />
                                                    max liput: {tapahtuma.tapahtumapaikka.maksimi_osallistujat}
                                                </div>
                                            ) : (
                                                "-"
                                            )}
                                        </td>
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