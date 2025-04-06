	Lippu lippu1 = new Lippu(tapahtuma1_aikuinen, (float) 3, myyty, myynti2);

# Muokkaa lippua 

Muokkaa lippua. 

**URL** : `/api/liput/:pk/`

**Method** : `PUT`

**Auth required** : Kyllä (myöhemmässä vaiheessa, kun käyttöoikeudet on määritelty)

**Permissions required** : Käyttäjä on ylläpitäjä

**Data constraints**
Voi käyttää kaikki neljää, vain yhtä tai muunlaista kombinaatiota muutettavista tiedoista
```json
{
    "tapahtuman_lipputyyppi_id": "[Long identity]",
    "hinta": "[float]",
    "tila_id": "[Long identity]",
    "myynti_id": "[Long identity]"
}
```

**Data example** 

```json
{
    "tapahtuman_lipputyyppi_id": 1,
    "hinta": 30.0,
    "tila_id": 1,
    "myynti_id": 2
}
```

```json
{
   
    "hinta": 30.0
  
}
```

```json
{
    "tapahtuman_lipputyyppi_id": 1,
    "hinta": 30.0
    
}
```

```json
{
    "tila_id": 1
    
}
```


...json
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
    "hinta": 30.0,
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
...



## Success Responses

**Condition** : Muutos voidaan suorittaa

**Code** : `201 Created`

**Content example** : Esimerkki, kun nimi on päivitetty ja "postattu" osoitteeseen `/api/tapahtumat/:pk/`...

```json
{
    "tapahtuma_id": 1,
    "nimi": "UusiNimi",
    "paivamaara": "2025-04-18T00:00:00",
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
    "url": "http://localhost8080/api/tapahtumat/1/"
}
```

## Error Response

**Condition** : Jos annettu data ei ole kelvollista. Esimerkiksi pvm on väärässä muodossa

**Code** : `400 Bad Request` 

**Content** : `{
    "tapahtuman_lipputyyppi_id": null,
    "hinta": 10,
    "tila_id": null,
    "myynti_id": null,
    "kayttaja_id": null,
    "tarkastus_pvm": "2025-06-15 10:10:00"
}`

Päivämäärä on väärin: "virhe": "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku. Päivämäärän ja ajan tulee olla muodossa yyyy-MM-dd'T'HH:mm:ss.   JSON parse error: Cannot deserialize value of type `java.time.LocalDateTime` from String \"2025-06-15 10:10:00\": Failed to deserialize java.time.LocalDateTime: (java.time.format.DateTimeParseException) Text '2025-06-15 10:10:00' could not be parsed at index 10"

Hinta on alle 0€: virhe": "Hinta ei ole kelvollinen. Hinta ei voi olla alle 0€.

Käyttäjä_id on väärin: "virhe": "Odottamaton virhe: 400 BAD_REQUEST \"Kayttaja ei ole kelvollinen, anna käytössä oleva kayttaja_id."

### Or

**Condition** : Käyttäjä ei ole typiltään järjestäjä (toteutetaan myöhemmin).

**Code** : `403 FORBIDDEN`

**Content** : `{}`

**Condition** : Jos kyselyssä annettua IDtä ei ole olemassa

**Code** : `404 Not found`

**Content** : `{"virhe": "Lippu_Id 10 ei ole olemassa."}`

## Notes

Vain annetut tiedot päivitetään. Huom. Jos annetaan tyhjä tieto, esim kuvaus, se ajaa vanhan tiedon yli. 

