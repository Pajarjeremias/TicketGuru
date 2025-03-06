# Muokkaa tapahtumaa

Muokkaa tapahtumaa. 

**URL** : `/api/tapahtumat/:pk/`

**Method** : `PUT`

**Auth required** : Kyllä (myöhemmässä vaiheessa, kun käyttöoikeudet on määritelty)

**Permissions required** : Käyttäjä on ylläpitäjä

**Data constraints**

```json
{
    "tapahtuma_id": "[Long identity]",
    "nimi": "[String, 2-200 merkkiä]",
    "paivamaara": "[LocalDateTime]",
    "kuvaus": "[String, 2-200 merkkiä]",
    "tapahtumapaikka": "[List vittaa tapahtumapaikka_id]",
    "tapahtuman_lipputyypit": "[List, viittaa Long tapahtuma_lipputyyppi_id]",
    "jarjestaja": "[Jarjestaja olio, viittaa Long Jarjestaja_id]",
    "lippumäärä": "[Integer]"
}
```

**Data example** 

```json
{
    "nimi": "UusiNimi",
    "paivamaara": "2025-04-18T00:00:00",
    "kuvaus": "Paras konsertti ikinä.",
    "tapahtumapaikka": [],
    "lippumäärä": 50
}
```

## Success Responses

**Condition** : Muutos voidaan suorittaa

**Code** : `200 OK`

**Code** : `201 Created`

**Code** : `204 No Content`

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

**Condition** : Jos annettu data ei ole kelvollista. Esimerkiksi teksti on liian pitkä. 

**Code** : `400 Bad Request`

**Content** : `{}`

### Or

**Condition** : Käyttäjä ei ole typiltään järjestäjä (toteutetaan myöhemmin).

**Code** : `403 FORBIDDEN`

**Content** : `{}`

## Notes

Vain annetut tiedot päivitetään. Huom. Jos annetaan tyhjä tieto, esim kuvaus, se ajaa vanhan tiedon yli. 

