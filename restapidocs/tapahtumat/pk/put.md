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
    "tapahtumapaikka": "[Long vittaa tapahtumapaikka_id]",
    "tapahtuman_lipputyypit": "[List, viittaa Long tapahtuma_lipputyyppi_id]",
    "jarjestaja": "[Jarjestaja olio, viittaa Long Jarjestaja_id]",
}
```

**Data example** Partial data is allowed???

```json
{
    "nimi": "UusiNimi",
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
    "paivamaara": "[LocalDateTime]",
    "kuvaus": "Tähän tapahtuman kuvaus",
    "tapahtumapaikka": 1,
    "tapahtuman_lipputyypit": [1, 2],
    "jarjestaja": 1,
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

### Data ignored

Endpoint jättää huomiotta parametrit, joita ei ole olemassa tai ovat vain luettavissa. ??
