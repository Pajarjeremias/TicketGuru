import { useState, useEffect } from "react";
import { config as scrummeriConfig } from "../config/scrummerit";

export type TLipputyyppi = {
    lipputyyppi_id: number;
    lipputyyppi: string;
};

export type Tapahtuma = {
    tapahtuma_id: number;
    nimi: string;
    paivamaara: string;
    kuvaus: string;
    lippumaara: string;
};

export default function TapahtumaLipputyyppiComponent() {
    const [lipputyypit, setLipputyypit] = useState<TLipputyyppi[]>([]);
    const [uusiLipputyyppi, setUusiLipputyyppi] = useState<string>("");
    const [lipputyyppiHinta, setLipputyyppiHinta] = useState<number>(0);
    const [tapahtumat, setTapahtumat] = useState<Tapahtuma[]>([]);
    const [valittuTapahtuma, setValittuTapahtuma] = useState<number | null>(null);
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

    const createLipputyyppi = async (nimi: string) => {
        const response = await fetch(`${scrummeriConfig.apiBaseUrl}/lipputyypit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Basic ${btoa('yllapitaja:yllapitaja')}`,
            },
            body: JSON.stringify({ lipputyyppi: nimi }),
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.errors?.[0] ?? 'Lipputyypin luonti epäonnistui');
        }

        return await response.json();
    };

    const createTapahtumanLipputyyppi = async (tapahtumaId: number, lipputyyppiId: number, hinta: number) => {
        const response = await fetch(
            `${scrummeriConfig.apiBaseUrl}/tapahtumanlipputyypit?tapahtumaId=${tapahtumaId}&lipputyyppiId=${lipputyyppiId}&hinta=${hinta}`,
            {
                method: 'POST',
                headers: {
                    Authorization: `Basic ${btoa('yllapitaja:yllapitaja')}`,
                },
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

        if (!uusiLipputyyppi.trim()) {
            setLuontiVirheViesti("Muista lisätä lipputyypin nimi.");
            return;
        }

        if (uusiLipputyyppi.length > 30) {
            setLuontiVirheViesti("Lipputyypin nimi ei saa ylittää 30 merkkiä.");
            return;
        }

        if (!valittuTapahtuma) {
            setLuontiVirheViesti("Valitse tapahtuma.");
            return;
        }

        if (lipputyyppiHinta <= 0) {
            setLuontiVirheViesti("Hinnan on oltava suurempi kuin 0.");
            return;
        }

        try {
            const uusi = await createLipputyyppi(uusiLipputyyppi);
            await createTapahtumanLipputyyppi(valittuTapahtuma, uusi.lipputyyppi_id, lipputyyppiHinta);
            setLuontiViesti(`Lipputyyppi "${uusi.lipputyyppi}" lisätty tapahtumaan onnistuneesti.`);
            setUusiLipputyyppi("");
            setLipputyyppiHinta(0);
            fetchLipputyypit();
        } catch (err: any) {
            setLuontiVirheViesti(err.message ?? "Tuntematon virhe lipputyyppin luonnissa.");
        }
    };

    const handleEditLipputyyppiSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const id = Number(e.target.value);
        const selected = lipputyypit.find(l => l.lipputyyppi_id === id);
        if (selected) {
            setMuokattuLipputyypinId(selected.lipputyyppi_id);
            setMuokattuLipputyypinNimi(selected.lipputyyppi);
        }
    };
    const updateTapahtumanLipputyyppiHinta = async (lipputyyppiId: number, hinta: number) => {
    const response = await fetch(
        `${scrummeriConfig.apiBaseUrl}/tapahtumanlipputyypit/${lipputyyppiId}`,
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Basic ${btoa('yllapitaja:yllapitaja')}`,
            },
            body: JSON.stringify({ hinta }),
        }
    );

    if (!response.ok) {
        throw new Error('Hinnan päivittäminen epäonnistui');
    }
};


    const handleUpdateLipputyyppi = async () => {
        if (muokattuLipputyypinId === null) return;

        setMuokkausViesti(null);
        setMuokkausVirheViesti(null);

        try {
            const response = await fetch(`${scrummeriConfig.apiBaseUrl}/lipputyypit/${muokattuLipputyypinId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Basic ${btoa('yllapitaja:yllapitaja')}`
                },
                body: JSON.stringify({ lipputyyppi: muokattuLipputyypinNimi })
            });

            if (!response.ok) {
                const error = await response.json();
                setMuokkausVirheViesti(error.errors?.[0] ?? "Muista lisätä uusi nimi");
            } else {
                setMuokkausViesti("Tiedot päivitetty onnistuneesti");
                fetchLipputyypit();
            }
        } catch (e) {
            setMuokkausVirheViesti("Odottamaton virhe");
        }
    };

    return (
        <div className="container">
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
                    <div className="tab-pane fade show active" id="luonti" role="tabpanel" aria-labelledby="luonti-tab">
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
                            <label className="form-label">Lipputyyppi</label>
                            <input
                                className="form-control"
                                type="text"
                                value={uusiLipputyyppi}
                                onChange={(e) => setUusiLipputyyppi(e.target.value)}
                                placeholder="Esim. Aikuinen"/>
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

                    <div className="tab-pane fade" id="muokkaus" role="tabpanel" aria-labelledby="muokkaus-tab">
                        <h2>Lipputyypin muokkaus</h2>

                        <div className="mb-3">
                            <label className="form-label">Valitse lipputyyppi</label>
                            <select className="form-select" onChange={handleEditLipputyyppiSelect} value={muokattuLipputyypinId || ""}>
                                <option value="">Valitse muokattava</option>
                                {lipputyypit.map(l => (
                                    <option key={l.lipputyyppi_id} value={l.lipputyyppi_id}>
                                        {l.lipputyyppi}
                                    </option>
                                ))}
                            </select>
                        </div>

                        {muokattuLipputyypinId !== null && (
                            <>
                                <div className="mb-3">
                                    <label className="form-label">Uusi nimi</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        value={muokattuLipputyypinNimi}
                                        onChange={(e) => setMuokattuLipputyypinNimi(e.target.value)}/>
                                </div>

                                <div className="mb-3">
                                    <label className="form-label">Uusi hinta</label>
                                    <input
                                        type="number"
                                        className="form-control"
                                        value={muokattuHinta}
                                        onChange={(e) => setMuokattuHinta(Number(e.target.value))}/>
                                </div>

                                <button className="btn btn-success" onClick={handleUpdateLipputyyppi}>Tallenna muutokset</button>
                                {muokkausViesti && <div className="alert alert-success mt-2">{muokkausViesti}</div>}
                                {muokkausVirheViesti && <div className="alert alert-danger mt-2">{muokkausVirheViesti}</div>}
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}