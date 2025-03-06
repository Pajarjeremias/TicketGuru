# Hae kaikki myynnit

Hakee kaikki luodut tapahtumat ja niihin liittyvät tiedot.

**URL** : `/api/myynnit`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : -

## Success Responses

**Code** : `200 OK`

**Content**

Myynnin tiedot.

Alla oleva pitää muokata

```json
[
    {
        "tapahtuma_id": 1,
        "nimi": "Konsertti 1",
        "paivamaara": "2025-03-18T00:00:00",
        "kuvaus": "Paras konsertti ikinä.",
        "tapahtumapaikka": [],
        "tapahtuman_lipputyypit": [
            {
                "tapahtuma_lipputyyppi_id": 1,
                "lipputyyppi": {
                    "lipputyyppi_id": 1,
                    "lipputyyppi": "Aikuinen"
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
        ],
        "lippumaara": 50
    },
    {
        "tapahtuma_id": 2,
        "nimi": "Urheilutapahtuma 3",
        "paivamaara": "2025-04-01T00:00:00",
        "kuvaus": "Paras urheilutapahtuma ikinä.",
        "tapahtumapaikka": [],
        "tapahtuman_lipputyypit": [
            {
                "tapahtuma_lipputyyppi_id": 3,
                "lipputyyppi": {
                    "lipputyyppi_id": 1,
                    "lipputyyppi": "Aikuinen"
                },
                "hinta": 19.9
            },
            {
                "tapahtuma_lipputyyppi_id": 4,
                "lipputyyppi": {
                    "lipputyyppi_id": 2,
                    "lipputyyppi": "Lapsi"
                },
                "hinta": 5.9
            },
            {
                "tapahtuma_lipputyyppi_id": 5,
                "lipputyyppi": {
                    "lipputyyppi_id": 3,
                    "lipputyyppi": "Eläkeläinen"
                },
                "hinta": 0.0
            }
        ],
        "lippumaara": 250
    }
]
```