# Hae kaikki tapahtumat

Hakee kaikki luodut lipputyypit ja niihin liittyvät tiedot.

**URL** : `/api/tapahtumat`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : -

## Success Responses

**Code** : `200 OK`

**Content**

Kaksi esimerkki lipputyyppiä. Nähdään lipputyyppien id ja nimi


```json
[
    {
        "lipputyyppi_id": 1,
        "lipputyyppi": "Aikuinen"
    },
    {
        "lipputyyppi_id": 3,
        "lipputyyppi": "Eläkeläinen"
    },
    {
        "lipputyyppi_id": 2,
        "lipputyyppi": "Lapsi"
    },
    {
        "lipputyyppi_id": 4,
        "lipputyyppi": "Opiskelija"
    }
]
```