# Hae kaikki liput

Hakee kaikki luodut liput ja niihin liittyvät tiedot.

**URL** : `/api/liput`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints**

```json
    {
          {
            "lippu_id": "[Integer, REQUIRED]",
            "tapahtuman_lipputyyppi": "[Integer, REQUIRED viittaa Tapahtuman_lipputyyppi-olioon]",
            "hinta": "[Float, OPTIONAL]",
            "tila": "[Integer, REQUIRED viittaa Tila-olioon]",
            "tarkastanut": "[Integer, PTIONAL viittaa lipun tarkastaneeseen Kayttaja-olioon]",
            "tarkastus_pvm": "[LocalDateTime]",
            "myynti": "[Integer, REQUIRED viittaa Myynti-olioon]"
        }
}
```

## Success Responses

**Code** : `200 OK`

**Content**

Lippujen tiedot. Alla esimerkki.

```json

    {

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

}
  

```