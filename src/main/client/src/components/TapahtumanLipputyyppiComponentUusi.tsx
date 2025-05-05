import { useState, useEffect } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";

export type TLipputyyppi = {
    tapahtuma_lipputyyppi_id: any,
    lipputyyppi: {
        lipputyyppi_id: number
    },
    hinta: number
};



export type Tapahtuma = {
    tapahtuma_id: any;
    nimi: string;
    paivamaara: string;
    kuvaus: string;
    lippumaara: string;
};

export default function TapahtumanLipputyyppiComponentUusi() {
    const [lipputyypit, setLipputyypit] = useState<any[]>([]);
    const [tapahtumanlipputyypit, setTapahtumanlipputyypit] = useState<TLipputyyppi[]>([]);
    const [uusiLipputyyppi, setUusiLipputyyppi] = useState<string>("");
    const [lipputyyppiHinta, setLipputyyppiHinta] = useState<number>(0);
    const [tapahtumat, setTapahtumat] = useState<Tapahtuma[]>([]);
    const [valittuTapahtuma, setValittuTapahtuma] = useState<number | null>(null);
    const [valittuLipputyyppi, setValittuLipputyyppi] = useState<number | null>(null);
    const [muokattuLipputyypinId, setMuokattuLipputyypinId] = useState<number | null>(null);
    const [muokattuLipputyypinNimi, setMuokattuLipputyypinNimi] = useState<string>("");
    const [muokattuHinta, setMuokattuHinta] = useState<number>(0);
    const [muokkausViesti, setMuokkausViesti] = useState<string | null>(null);
    const [muokkausVirheViesti, setMuokkausVirheViesti] = useState<string | null>(null);
    const [luontiViesti, setLuontiViesti] = useState<string | null>(null);
    const [luontiVirheViesti, setLuontiVirheViesti] = useState<string | null>(null);

    useEffect(() => {
        fetchLipputyypit();
        fetchTapahtumat();
    }, []);

    const fetchLipputyypit = async () => {
        try {
            const response = await fetch(`${scrummeriConfig.apiBaseUrl}/lipputyypit`, {
                headers: { 'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}` }
            });
            const data = await response.json();
            if (Array.isArray(data)) {
                setLipputyypit(data);
            } else if (data._embedded?.lipputyypit) {
                setLipputyypit(data._embedded.lipputyypit);
            } else {
                setLipputyypit([]);
            }
        } catch (e) {
            console.error(e);
        }
    };

    const fetchTapahtumat = async () => {
        try {
            const response = await fetch(`${scrummeriConfig.apiBaseUrl}/tapahtumat`, {
                headers: { 'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}` }
            });
            const data = await response.json();
            setTapahtumat(data);
        } catch (e) {
            console.error(e);
        }
    };

    const createTapahtumanLipputyyppi = async (tapahtumaId: number, lipputyyppiId: number, hinta: number) => {
        const response = await fetch(
            `${scrummeriConfig.apiBaseUrl}/tapahtumanlipputyypit`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Basic ${btoa('yllapitaja:yllapitaja')}`,
                },
                body: JSON.stringify({
                tapahtuma_id: tapahtumaId,
                lipputyyppi_id: lipputyyppiId,
                hinta: hinta,
                }),
            }
        );

        if (!response.ok) {
            throw new Error('Tapahtuman lipputyypin liittäminen epäonnistui');
        }

        return await response.json();
    };

    const handleLipputyyppiLuonti = async () => {
        setLuontiViesti(null);
        setLuontiVirheViesti(null);

        if (!valittuTapahtuma) {
            setLuontiVirheViesti("Valitse tapahtuma.");
            return;
        }

        if (lipputyyppiHinta <= 0) {
            setLuontiVirheViesti("Hinnan on oltava suurempi kuin 0.");
            return;
        }

        try {
            await createTapahtumanLipputyyppi(valittuTapahtuma, valittuLipputyyppi!, lipputyyppiHinta);
            setUusiLipputyyppi("");
            setLipputyyppiHinta(0);
            fetchLipputyypit();
        } catch (err: any) {
            setLuontiVirheViesti(err.message ?? "Tuntematon virhe lipputyyppin luonnissa.");
        }
    };

    

    return (
        <div className="container my-4">
            <div className="tabs">
                <ul className="nav nav-tabs" role="tablist">
                    <li className="nav-item">
                        <a className="nav-link active" id="luonti-tab" data-bs-toggle="tab" href="#luonti" role="tab">Lipputyypin luonti</a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" id="muokkaus-tab" data-bs-toggle="tab" href="#muokkaus" role="tab">Lipputyypin muokkaus</a>
                    </li>
                </ul>
                <div className="tab-content">
                    <div className="tab-pane fade show active my-4" id="luonti" role="tabpanel" aria-labelledby="luonti-tab">
                        <h2>Lipputyypin luonti</h2>
                        <div className="mb-3">
                            <label className="form-label">Valitse tapahtuma</label>
                            <select
                                className="form-select"
                                value={valittuTapahtuma || ""}
                                onChange={(e) => setValittuTapahtuma(Number(e.target.value))}>
                                <option value="">Valitse tapahtuma</option>
                                {tapahtumat.map(tapahtuma => (
                                    <option key={tapahtuma.tapahtuma_id} value={tapahtuma.tapahtuma_id}>
                                        {tapahtuma.nimi}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="mb-3">
                            <label className="form-label">Valitse lipputyyppi</label>
                            <select
                                className="form-select"
                                value={valittuLipputyyppi || ""}
                                onChange={(e) => setValittuLipputyyppi(Number(e.target.value))}>
                                <option value="">Valitse lipputyyppi</option>
                                {lipputyypit.map(lipputyyppi => (
                                    <option key={lipputyyppi.lipputyyppi_id} value={lipputyyppi.lipputyyppi_id}>
                                        {lipputyyppi.lipputyyppi}
                                    </option>
                                ))}
                            </select>
                        </div>

                    
                        <div className="mb-3">
                            <label className="form-label">Lipputyypin hinta</label>
                            <input
                                className="form-control"
                                type="number"
                                value={lipputyyppiHinta}
                                onChange={(e) => setLipputyyppiHinta(Number(e.target.value))}
                                placeholder="Lipun hinta"/>
                        </div>

                        <button className="btn btn-primary" onClick={handleLipputyyppiLuonti}>Luo lipputyyppi</button>
                        {luontiViesti && <div className="alert alert-success mt-2">{luontiViesti}</div>}
                        {luontiVirheViesti && <div className="alert alert-danger mt-2">{luontiVirheViesti}</div>}
                    </div>

                </div>
            </div>
        </div>
    );
}