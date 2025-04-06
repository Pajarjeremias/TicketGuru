# Hae yksittäisen myynnin liput

Hakee yksittäisen myynnin liput.

**URL** : `/api/myynnit/{id}/liput`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : 


```json
{
  "myynti_id": "[Integer, REQUIRED]",
  "tapahtuman_lipputyypit_id": "[Integer, REQUIRED]",
  "hinta": "[Float, OPTIONAL]"
}
```

- myynti_id
  - sen myynnin id, johon lippu liittyy
  - <u>pakollinen</u>
- tapahtuman_lipputyypit_id
  - se tapahtuman_lipputyyppi_id, johon lippu liittyy
  - kertoo lipun tyypin (esim. aikuinen) ja tapahtuman johon lippu liittyy
  - <u>pakollinen</u>
- hinta
  - hinta, jolla lippu on myyty
  - jos hintaa ei ole annettu, oletuksena haetaan lipulle sen tapahtuman_lipputyypin hinta, johon se liittyy
  <u>vapaaehtoinen</u>

## Success Responses

**Condition** : Jos myynnillä on lippuja

**Code** : `200 OK`

**Content**

Lippujen tiedot listana. Alla esimerkki.

```json

    {
    "headers": {},
    "body": [
        {
            "lippu_id": 1,
            "tapahtuman_lipputyyppi": {
                "tapahtuma_lipputyyppi_id": 1,
                "lipputyyppi": {
                    "lipputyyppi_id": 1,
                    "lipputyyppi": "Aikuinen"
                },
                "hinta": 30.0
            },
            "hinta": 3.0,
            "tila": {
                "tila_id": 1,
                "tila": "Myyty"
            },
            "tarkastanut": null,
            "tarkastus_pvm": null,
            "myynti": {
                "myynti_id": 2,
                "asiakas": null,
                "myyntipaiva": "2025-06-15",
                "myyntipiste": {
                    "nimi": "Ensimmäinen piste",
                    "katuosoite": "Messuaukio 1",
                    "postitoimipaikka": {
                        "postinumero": "00520",
                        "postitoimipaikka": "Helsinki",
                        "maa": "Suomi"
                    },
                    "myyntipisteId": 1
                },
                "maksutapa": {
                    "maksutapa_id": 1,
                    "maksutapa": "Käteinen"
                }
            }
        }
    ],
    "statusCode": "OK",
    "statusCodeValue": 200
}

```

## Error Responses

**Condition** : Mikäli myynnillä ei ole lippuja.

**Code** : `200 ok`

**Content** : `null`


