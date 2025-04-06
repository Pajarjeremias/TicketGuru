# Muokkaa tapahtumaa

Muokkaa jo luotua tapahtumaa. 

**URL** : `/api/tapahtumat/:id/`

**Method** : `PUT`

**Auth required** : Kyllä (myöhemmässä vaiheessa, kun käyttöoikeudet on määritelty)

**Permissions required** : Käyttäjä on ylläpitäjä

**Data constraints**

```json
{
    "nimi": "[String, 2-200 merkkiä]",
    "paivamaara": "[LocalDateTime]",
    "kuvaus": "[String, 2-200 merkkiä]",
    "tapahtumapaikka_id": "[Long]",
    "lippumäärä": "[Integer, greater than 0]",
    "jarjestaja_id": "[Long]"
}
```

**Data example** 

```json
{
    "nimi": "UusiNimi",
    "paivamaara": "2025-04-18T00:00:00",
    "kuvaus": "Paras konsertti ikinä.",
    "tapahtumapaikka_id": 1,
    "lippumäärä": 50,
    "jarjestaja_id": 1
}
```

## Success Responses

**Condition** : Muokkaus onnistunut ongelmitta.

**Code** : `200 OK`

**Content example** :

```json
{
    "nimi": "Uusi nimi 123",
    "kuvaus": "Paras konsertti ikinä 123",
    "lippumaara": 50000
}
```

**Response example** :

```json
{
    "url": null,
    "tapahtuma_id": 1,
    "nimi": "Uusi nimi 123",
    "paivamaara": "2025-07-18T00:00:00",
    "kuvaus": "Paras konsertti ikinä 123",
    "tapahtumapaikka": null,
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
    "jarjestaja": null,
    "lippumaara": 50000
}
```

## Error Response

**Condition** : URL:ssa annetulla id:lla ei löydy tapahtumaa.

**Code** : `404 NOT FOUND`

**Response Example** :

```json
{
    "errors": [
        "Annetulla id:lla ei löydy tapahtumaa."
    ]
}
```

---

**Condition** : Annettu data on väärässä muodossa.

**Code** : `400 BAD REQUEST`

**Content Example** :

Annetu päivämäärä on väärässä muodossa.

```json
{
    "nimi": "Uusi nimi 123",
    "kuvaus": "Paras konsertti ikinä 123",
    "lippumaara": 50000,
    "paivamaara": "1.1.2026"
}
```

**Response Example** :

```json
{
    "errors": [
        "JSON parse error: Cannot deserialize value of type `java.time.LocalDateTime` from String \"1.1.2026\": Failed to deserialize java.time.LocalDateTime: (java.time.format.DateTimeParseException) Text '1.1.2026' could not be parsed at index 0"
    ]
}
```

---

**Condition** : Annettu tapahtumapaikka- tai jarjestaja id, jota ei ole olemassa.

**Code** : `404 NOT FOUND`

**Content Example** :

Annetulla tapahtumapaikka_id:lla ei löydy tapahtumapaikkaa.

```json
{
    "nimi": "Uusi nimi 123",
    "kuvaus": "Paras konsertti ikinä 123",
    "lippumaara": 50000,
    "tapahtumapaikka_id": 11
}
```

**Response Example** :

```json
{
    "errors": [
        "Annetulla tapahtumapaikka_id:lla ei löydy tapahtumapaikkaa."
    ]
}
```



