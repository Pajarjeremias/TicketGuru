# Luo tapahtuma

Luo uusi tapahtuma järjestelmään

**URL** : `/api/tapahtumat/`

**Method** : `POST`

**Auth required** : Kyllä 

**Permissions required** : Käyttäjä on ylläpitäjä

**Data constraints**

```json
{
    "nimi": "[String, 2-200 merkkiä]",
    "paivamaara": "[LocalDateTime]",
    "kuvaus": "[String, 2-200 merkkiä]",
    "tapahtumapaikka": "[List viittaa tapahtumapaikka_id]",
    "jarjestaja": "[Jarjestaja-olio viittaa jarjestaja_id]",
    "tapahtuman_lipputyypit": "[Long, viittaa tapaht_lipputyyp_id]",
    "lippumaara": "[Integer]"
}
```
**Data example**

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

**Content example** 

```json
{
    "tapahtuma_id": 1,
    "nimi": "Bob Marley Reggaefest",
    "paivamaara": "2025-08-23T2419:00",
    "kuvaus": "Bob Marleyn upea kesäinen lavakeikka",
    "tapahtumapaikka": [],
    "tapahtuman_lipputyypit": [],
    "lippumaara": 60,
    "url": "http://localhost:8080/api/tapahtumat/1/"
}
```

## Error Response

**Condition** Jos annettu data ei ole kelvollista. Esimerkiksi syötetyt tiedot eivät vastaa tietotyyppejä.

**Code** : `400`

**Content** : `{}`




