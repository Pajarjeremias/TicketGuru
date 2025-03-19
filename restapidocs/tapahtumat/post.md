# Luo tapahtuma

Luo uusi tapahtuma järjestelmään

**URL** : `/api/tapahtumat/`

**Method** : `POST`

**Auth required** : Kyllä 

**Permissions required** : Käyttäjä on ylläpitäjä

**Data constraints**

Kaikki kentät, jotka voi antaa kutsussa sekä niiden vaatimukset datan muodon osalta.

```json
{
    "nimi": "[String, 2-200 merkkiä]",
    "paivamaara": "[LocalDateTime]",
    "kuvaus": "[String, 2-200 merkkiä]",
    "tapahtumapaikka": "[List viittaa tapahtumapaikka_id]",
    "jarjestaja": "[Jarjestaja-olio viittaa jarjestaja_id]",
    "tapahtuman_lipputyypit": "[Long, viittaa tapaht_lipputyyp_id]",
    "lippumaara": "[Integer, Greater than 0]"
}
```

**Minimum required data**

Minimidata, joka vaaditaan uuden tapahtuman luontiin (nimi, päivämäärä ja lippumäärä).

```json
{
    "nimi": "[String, 2-200 merkkiä]",
    "paivamaara": "[LocalDateTime]",
    "lippumaara": "[Integer, Greater than 0]"
}
```

**Data example**

Esimerkki validista request body:sta.

```json
{
    "nimi": "Cheek-keikka",
    "paivamaara": "2025-05-11T11:16:00",
    "kuvaus": "Cheekin upea konsertti",
    "lippumaara": 1200
}
```

## Success Response

**Condition** : Tapahtuma luodaan onnistuneesti

**Code** : `201 CREATED`

**Response example** 

```json
{
    "tapahtuma_id": 1,
    "nimi": "Cheek-keikka",
    "paivamaara": "2025-05-11T11:16:00",
    "kuvaus": "Cheekin upea konsertti",
    "tapahtumapaikka": null,
    "tapahtuman_lipputyypit": null,
    "lippumaara": 1200,
    "url": null
}
```

## Error Responses

**Condition** Pakollista tietoa puuttuu.

**Code** : `400 BAD REQUEST`

**Content example**

Päivämäärää puuttuu pyynnöstä.

```json
{
    "nimi": "Bib Marleyn Reggaefest",
    "lippumaara": 600
}
```

**Response example** 

```json
{
    "errors": [
        "Päivämäärä ja kellonaika täytyy olla annettu ja sen on oltava tulevaisuudessa."
    ]
}
```

---

**Condition** Tietoa annetaan virheellisessä muodossa.

**Code** : `400 BAD REQUEST`

**Content example** 

Päivämäärä ei ole oikeassa muodossa.

```json
{
    "nimi": "Bib Marleyn Reggaefest",
    "paivamaara": "5.6.2026",
    "lippumaara": 600
}
```

**Response example** 

```json
{
    "errors": [
        "JSON parse error: Cannot deserialize value of type `java.time.LocalDateTime` from String \"5.6.2026\": Failed to deserialize java.time.LocalDateTime: (java.time.format.DateTimeParseException) Text '5.6.2026' could not be parsed at index 0"
    ]
}
```

---