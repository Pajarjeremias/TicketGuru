# Tarkastaa lippunumerolla annetun lipun ja merkitsee lipun tarkastetuksi tarkastusajankohdan kanssa

Hakee koodilla lipun tiedot. Bodyyn laitetaan json tieto tarkastusajankohdsta. 

**URL** : `/api/liput/{id}` 

**BODY**: tarkastu päivämäärä ja kellonaika JSON muodossa yyyy-MM-dd'T'HH:mm:ss
esimerkki
...json
 { 
    "used": "2023-11-07T07:03:46
    "}
...

**Method** : `PATCH`

**Auth required** : Sallittu käyttäjäyypeille Yllapitaja, Tapahtumavastaava ja Lipunmyyja

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
            "myynti": "[Integer, REQUIRED viittaa Myynti-olioon]",
            "koodi":"[String, luodaan automaattisesti]
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
                "tila_id": 3,
                "tila": "Tarkastettu"
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
            },
            "koodi": "5b9e6a24-a249-40ad-ac38-3681cc62ff9a"
        }
    ],

}

 
**Code** : `202 OK`

**Content**

Lippu on joko jo tarkastettu tai peruttu.

```