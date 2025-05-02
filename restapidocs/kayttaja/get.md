# Hae kaikki käyttäjät

Hakee kaikki luodut käyttäjät ja niihin liittyvät tiedot.

**URL** : `/api/kayttajat` tai `/api/kayttajat/`

**Method** : `GET`

**Auth required** : Kyllä

**Permissions required** : Käyttäjä on ylläpitäjä tai tapahtumavastaaja

**Data constraints**

```json
{
    "kayttajatunnus": "[String, REQUIRED]",
    "salasana_hash": "[String, REQUIRED]",
    "etunimi": "[String, OPTIONAL]",
    "sukunimi": "[String, OPTIONAL]",
    "puh_nro": "[String, OPTIONAL]",
    "email": "[String, OPTIONAL]",
    "katuosoite": "[String, OPTIONAL]",
    "postinumero": {
        "postinumero": "[String, OPTIONAL]",
        "postitoimipaikka": "[String, REQUIRED]",
        "maa": "[String, OPTIONAL]"
    },
    "kayttajatyyppi": {
        "kayttajatyyppi_id": [Integer, Optional],
        "kayttajatyyppi": "[String, REQUIRED]",
        "kuvaus": "[String, REQUIRED]"
    }
}
```

## Success Responses

**Code** : `200 OK`

**Content**

Käyttäjien tiedot. Alla esimerkki.

```json
[
   {
        "kayttaja_id": 1,
        "kayttajatunnus": "asiakas",
        "salasana_hash": "$2a$10$fs/sPJqj.ZdZHSPpen19GONDN7HhVXTB/oXJFflS8kzMyfgLouwoq",
        "etunimi": "Jeremias",
        "sukunimi": "Pajari",
        "puh_nro": "0449834478",
        "email": "jeremias.pajari@gmail.com",
        "katuosoite": "vanhatie 5",
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
    },
    {
        "kayttaja_id": 2,
        "kayttajatunnus": "lipunmyyjä",
        "salasana_hash": "$2a$10$HLxDiZI9wgTbvc4ky0/eletLO9odk0vTh8Nw9lDECQdfZ/DcVm6P2",
        "etunimi": "Matti",
        "sukunimi": "Meiikäläinen",
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
    },
]

```


### Error Response

**Condition**  Ei oikeuksia tai autohorisointiin ei syötetty username ja password

**Code** : `401 Unauthorized`

