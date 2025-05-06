import { useState, useEffect } from 'react';
import { config as scrummeriConfig } from "../config/scrummerit";

type EditTapahtumaProps = {
    tapahtuma: any;
    index: number;
    onSave: (updatedTapahtuma: any, index: number) => void;
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

export default function EditTapahtuma({ tapahtuma, index, onSave }: EditTapahtumaProps) {
    const [tapahtuma_id] = useState<number>(tapahtuma.tapahtuma_id);
    const [tapahtumanNimi, setTapahtumanNimi] = useState<string>(tapahtuma.nimi);
    const [tapahtumanKuvaus, setTapahtumanKuvaus] = useState<string>(tapahtuma.kuvaus);
    const [paivaMaara, setPaivaMaara] = useState<string>(tapahtuma.paivamaara);
    const [lippuMaara, setLippuMaara] = useState<number>(tapahtuma.lippumaara);
    const [tapahtumapaikat, setTapahtumapaikat] = useState<TTapahtumapaikka[]>([]);
    const [valittuTapahtumapaikka, setValittuTapahtumapaikka] = useState<number>(
        tapahtuma.tapahtumapaikka?.tapahtumapaikka_id || 0 );
    console.log("EDIT TAPAHTUMAAN TULI ", tapahtuma);


    const handleSave = () => {
        const updatedTapahtuma = {
            ...tapahtuma,
            id: tapahtuma_id,
            nimi: tapahtumanNimi,
            kuvaus: tapahtumanKuvaus,
            paivamaara: paivaMaara,
            lippumaara: lippuMaara,
            tapahtumapaikka_id : valittuTapahtumapaikka,
        };
        onSave(updatedTapahtuma, index);
    };

    const handleCancel = () => {
        onSave(tapahtuma, index);
    }

    const fetchTapahtumapaikat = async () => {
        try {
            const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumapaikat`, {
                headers: { 'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}` }
            });
            const data = await response.json();
            if (Array.isArray(data)) {
                setTapahtumapaikat(data);
                console.log("DATA TPAIKAT: ",data);
            } else if (data._embedded?.tapahtumapaikat) {
                setTapahtumapaikat(data._embedded.tapahtumapaikat);
                console.log("DATA EMBEDDED TPAIKAT: ", data._embedded.tapahtumapaikat)
            } else {
                throw new Error("Tapahtumapaikat puuttuvat vastauksesta");
            }
            console.log("TAPAHTUMAPAIKAT: "+tapahtumapaikat);

        } catch (e) {
            console.error("API epäonnistui, lisätään testidata", e);
            const TTpaikka: TTapahtumapaikka = {
                tapahtumapaikka_id: 1,
                nimi: "Monitoimiareena Merirosvo - testidata",
                katuosoite: "Areenakatu 2",
                postinumero: "02100",
                kaupunki: "Espoo",
                maa: "Suomi",
                maksimi_osallistujat: 50000,
                tapahtuma_id: 1,
            }
            setTapahtumapaikat([TTpaikka]);
        }
    };


    useEffect(() => {
        fetchTapahtumapaikat();
    }, []);

    return (
        <>
            <div style={{ width: '600px', margin: '4px', padding: '15px', border: '1px solid lightgray' }}>
                <div><h5>Muokkaa valittua tapahtumaa</h5></div>
                <div className="row my-4">
                    <div className="col-12" >
                        {/* Muokkaa Tapahtuman Nimeä */}
                        <label htmlFor="maara-input" className="form-label">Tapahtuman nimi</label>
                        <input
                            value={tapahtumanNimi}
                            onChange={e => setTapahtumanNimi(e.target.value)}
                            placeholder="Tapahtuman nimi"
                            className="form-control mb-2"
                        />

                        {/* Muokkaa Tapahtuman Kuvausta */}
                        <label htmlFor="maara-input" className="form-label">Tapahtuman kuvaus</label>
                        <input
                            value={tapahtumanKuvaus}
                            onChange={e => setTapahtumanKuvaus(e.target.value)}
                            placeholder="Tapahtuman kuvaus"
                            className="form-control mb-2"
                        />

                        {/* Muokkaa Tapahtuman Päivämäärää */}
                        <label htmlFor="maara-input" className="form-label">Tapahtuman päivämäärä</label>
                        <input
                            id="datetime-picker"
                            type="datetime-local"
                            value={paivaMaara}
                            onChange={(e) => setPaivaMaara(e.target.value)}
                            placeholder="Tapahtuman päivämäärä ja aika. Täytyy olla muotoa 2025-05-11T11:16:00"
                            className="form-control mb-2"
                        />

                        {/* Muokkaa Lippu Määrä */}
                        <label htmlFor="maara-input" className="form-label">Lippujen määrä</label>
                        <input
                            value={lippuMaara}
                            onChange={(e) => setLippuMaara(Number(e.target.value))}
                            min={1}
                            max={500000}
                            type="number"
                            className="form-control"
                        />

                        {/* tapahtumapaikan valinta */}
                        <label className="form-label">Valitse tapahtumapaikka</label>
                        <select
                            className="form-select"
                            value={valittuTapahtumapaikka || ""}
                            onChange={(e) => setValittuTapahtumapaikka(Number(e.target.value))}>
                            <option value="">Valitse tapahtumapaikka</option>
                            {tapahtumapaikat.map(tapahtumapaikka => (
                                <option key={tapahtumapaikka.tapahtumapaikka_id} value={tapahtumapaikka.tapahtumapaikka_id}>
                                    {tapahtumapaikka.nimi}
                                </option>
                            ))}
                        </select>


                        <div >
                            <div style={{ margin: '4px' }}>
                                <button style={{ margin: '2px' }}
                                    className="btn btn-success"
                                    onClick={handleSave}>
                                    Tallenna muokkaukset
                                </button>
                                <button style={{ margin: '2px' }} className="btn btn-danger" color="danger" onClick={handleCancel}>
                                    Peruuta muokkaukset
                                </button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </>
    );

}
