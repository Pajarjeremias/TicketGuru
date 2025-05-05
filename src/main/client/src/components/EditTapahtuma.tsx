//import * as React from 'react';
import { useState } from 'react';

type EditTapahtumaProps = {
    tapahtuma: any;
    index: number;
    onSave: (updatedTapahtuma: any, index: number) => void;
};

export default function EditTapahtuma({ tapahtuma, index, onSave }: EditTapahtumaProps) {
    const [tapahtuma_id] = useState<number>(tapahtuma.tapahtuma_id);
    const [tapahtumanNimi, setTapahtumanNimi] = useState<string>(tapahtuma.nimi);
    const [tapahtumanKuvaus, setTapahtumanKuvaus] = useState<string>(tapahtuma.kuvaus);
    const [paivaMaara, setPaivaMaara] = useState<string>(tapahtuma.paivamaara);
    const [lippuMaara, setLippuMaara] = useState<number>(tapahtuma.lippumaara);
    console.log("EDIT TAPAHTUMAAN TULI ", tapahtuma);


    const handleSave = () => {
        const updatedTapahtuma = {
            ...tapahtuma,
            id: tapahtuma_id,
            nimi: tapahtumanNimi,
            kuvaus: tapahtumanKuvaus,
            paivamaara: paivaMaara,
            lippumaara: lippuMaara,
        };
        onSave(updatedTapahtuma, index);
    };

    const handleCancel = () => {
        onSave(tapahtuma, index);
    }

    return (
        <>
            <div style={{ width: '600px', margin: '4px', padding: '15px', border: '2px solid blue' }}>
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

                        {/* Lipputyypit */}



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
