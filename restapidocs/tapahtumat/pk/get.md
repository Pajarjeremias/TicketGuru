# Hae yksittäinen tapahtuma

Hakee yksittäisen tapahtuman.

**URL** : `/api/tapahtumat/{id}`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : -

## Success Responses

**Condition** : Jos yksittäinen tapahtuma onnistuttiin löytämään.

**Code** : `200 OK`

## Error Responses

**Condition** : Mikäli yksilöivällä tunnisteellä ei ole tapahtumaa.

**Code** : `404 Not Found`

**Content** :

```json
{
    "Not Found": " invalid ID value for Tapahtuma. Id must be valid. ID 3 not found"
}
```

**Content**

Kaksi esimerkkitapahtumaa. Nähdään tapahtuman id, nimi, päivämäärä, kuvaus, tapahtumapaikka sekä tapahtumaan liittyvien lipputyyppien tiedot.


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
    }
]

