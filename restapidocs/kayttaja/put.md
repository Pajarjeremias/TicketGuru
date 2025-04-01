# Muokkaa käyttäjän tietoja 

Muokkaa lippua. 

**URL** : `/api/kayttajat/:pk/`

**Method** : `PUT`

**Auth required** : Kyllä 

**Permissions required** : Käyttäjä on oltava ylläpitäjä, tapahtumavastaava tai lipunmyyjä

**Data constraints**
Voi päivittää yhtä tai vaikka kaikkia tietoja. Tässä ei voi muuttaa käyttäjätyyppiä.
```json
{
    "kayttajatunnus": "[String, REQUIRED]",
    "salasana_hash": "[String, OPTIONAL]",
    "etunimi": "[String, OPTIONAL]",
    "sukunimi": "[String, OPTIONAL]",
    "puh_nro": "[String, OPTIONAL]",
    "email": "[String, OPTIONAL]",
    "katuosoite": "[String, OPTIONAL]",
    "postinumero": {
        "postinumero": "[String, OPTIONAL]",
        "postitoimipaikka": "[String, REQUIRED]",
        "maa": "[String, OPTIONAL]"
    }
}
```

**Data example** 

```json
{

    "kayttajatunnus": "asiakas",
    "etunimi": "Max"

}
```

```json
{

"salasana_hash": "123456"

}
```

```json
{
      
    "puh_nro": "055555123",
    "email": "maxpower@gmail.com"
    
}
```

```json
{
    "tila_id": 1
    
}
```


...json
{
    "kayttaja_id": 2,
    "kayttajatunnus": "lipunmyyjä",
    "salasana_hash": "$2a$10$w5orghZV13zPinkB/h37qeJqAEvHC46d2DI1qNez3NVCFSSRCdNle",
    "etunimi": "Matti",
    "sukunimi": "Meikäläinen",
    "puh_nro": "040459596",
    "email": "matti.meikalaienn@gmail.com",
    "katuosoite": "jokukatu1",
    "postinumero": {
        "postinumero": "00520",
        "postitoimipaikka": "Helsinki",
        "maa": "Suomi"
    },
    "kayttajatyyppi": {
        "kayttajatyyppi_id": 2,
        "kayttajatyyppi": "Lipunmyyja",
        "kuvaus": "Kovan luokan laillinen trokari"
    }
}
...



## Success Responses

**Condition** : Muutos voidaan suorittaa

**Code** : `200 Ok`

**Content example** : Esimerkki, kun etunimi, salasana, sukunimi ja email on päivitetty ja "postattu" osoitteeseen `/api/kayttajat/:pk/`...

```json
{
    "kayttaja_id": 1,
    "kayttajatunnus": "asiakas",
    "salasana_hash": "$2a$10$2ge5oNi9/58efldNWIAa6.sGEaj82mcshNdzfPqrmwNZkXTyIRXGW",
    "etunimi": "Max",
    "sukunimi": "PowerUU",
    "puh_nro": "055555123",
    "email": "demo@gmail.com",
    "katuosoite": "demotie 6",
    "postinumero": {
        "postinumero": "00520",
        "postitoimipaikka": "Helsinki",
        "maa": "Suomi"
    },
    "kayttajatyyppi": {
        "kayttajatyyppi_id": 1,
        "kayttajatyyppi": "Asiakas",
        "kuvaus": "Tuiki tavallinen palveluiden kuluttaja"
    }
}
```

## Error Response

**Condition** : Jos annettu data ei ole kelvollista. Esimerkiksi pvm on väärässä muodossa. Puhelinnumero yritetään antaaa numerona, eikä Stringinä. 

**Code** : `400 Bad Request` 

**Content** : `{
    "kayttaja_id": 2,
    "kayttajatunnus": "lipunmyyjä",
    "salasana_hash": "$2a$10$HLxDiZI9wgTbvc4ky0/eletLO9odk0vTh8Nw9lDECQdfZ/DcVm6P2",
    "etunimi": "Matti",
    "sukunimi": "Meikäläinen",
    "puh_nro": 040459596,
    "email": "matti.meikalaienn@gmail.com",
    "katuosoite": "jokukatu1",
    "postinumero": {
        "postinumero": "00520",
        "postitoimipaikka": "Helsinki",
        "maa": "Suomi"
    }
}`



{
    "virhe": "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku. Päivämäärän ja ajan tulee olla muodossa yyyy-MM-dd'T'HH:mm:ss.   JSON parse error: Invalid numeric value: Leading zeroes not allowed"
}

### Or

**Condition** : Käyttäjä ei ole typiltään ylläpitäjä

**Code** : `401 Unauthorized`

**Content** : `{}`


**Condition** : Jos kyselyssä annettua IDtä ei ole olemassa  - EI TOIMI VIELÄ ODOTETUSTI

**Code** : `404 Not found`

**Content** : `{"There is no user (kayttaja) in given id "}`

## Notes

Vain annetut tiedot päivitetään. Huom. Jos annetaan tyhjä tieto, esim kuvaus, se ajaa vanhan tiedon yli. 