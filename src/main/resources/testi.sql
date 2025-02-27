-- Hakee kaikki tapahtumat ja millaisia lippytyyppejä tapahtumille on
SELECT t.nimi, t.kuvaus, t.pvm_ja_kellonaika, l.lipputyyppi, tl.hinta FROM tapahtumat AS t JOIN tapahtuman_lipputyypit AS tl ON tl.tapahtuma_id=t.tapahtuma_id JOIN lipputyypit AS l ON l.lipputyyppi_id=tl.lipputyyppi_id;

