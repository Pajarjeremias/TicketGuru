# Hae kaikki tapahtuman lipputyypit

Hakee kaikki luodut tapahtuman lipputyypit ja niihin liittyvät tiedot.

**URL** : `/api/tapahtumanlipputyypit`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : -

## Success Responses

**Code** : `200 OK`

**Content**

Kaksi esimerkkitapahtumaa. Nähdään lipputyypin id, nimi ja lipun hinta.


```json
[
    {
        "tapahtuma_lipputyyppi_id": 1,
        "lipputyyppi": {
            "lipputyyppi_id": 1,
            "lipputyyppi": "uusilipputyyppi"
        },
        "hinta": 30.0
    },
    {
        "tapahtuma_lipputyyppi_id": 2,
        "lipputyyppi": {
            "lipputyyppi_id": 2,
            "lipputyyppi": "Lapsi"
        },
        "hinta": 15.0
    }
]
```