# Tarkastaa lippunumerolla annetun lipun ja merkitsee lipun tarkastetuksi tarkastusajankohdan kanssa

Hakee koodilla lipun tiedot. Bodyyn laitetaan json tieto tarkastusajankohdsta. 

**URL** : `/api/liput/:pk` 

**Method** : `PATCH`

**Auth required** : Sallittu käyttäjäyypeille Yllapitaja, Tapahtumavastaava ja Lipunmyyja

**Permissions required** : 

**Data constraints**: tarkastu päivämäärä ja kellonaika JSON muodossa yyyy-MM-dd'T'HH:mm:ss
esimerkki

...json
 { 
    "used": "2023-11-07T07:03:46
    "}
...


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
```
 


**Code** : `202 OK`

**Content**

Lippu on joko jo tarkastettu tai peruttu.


**Code** : `--- NOT FOUND`
Mikäli pk numerolla ei löydy lippua

virhe, Lippu_Id  {pk}  ei ole olemassa.


**Code** : `--- BAD REQUEST`
Mikäli lippu on jo käytetty eli tarkastettu

virhe, Lippu_Id  {pk}  on jo käytetty. Tarkastusaika: 2023-11-07T07:03:46


**Code** : `--- BAD REQUEST`
Mikäli lippu on peruttu

virhe, Lippu_Id  {pk}  on peruttu.




```