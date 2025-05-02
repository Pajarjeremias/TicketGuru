# Luo lipputyyppi

Luo uusi lipputyyppi tietokantaan.

**URL** : `/api/lipputyypit`

**Method** : `POST`

**Auth required** : Kyllä 

**Permissions required** : Käyttäjä on ylläpitäjä tai tapahtumavastaaja

**Data constraints**

Kaikki kentät, jotka voi antaa kutsussa sekä niiden vaatimukset datan muodon osalta.

```json
{
    "nimi": "[String, 2-200 merkkiä]",
}
```

**Minimum required data**

Minimidata, joka vaaditaan uuden tapahtuman luontiin on nimi.

```json
{
    "nimi": "[String, 2-200 merkkiä]",
}
```

**Data example**

Esimerkki validista request body:sta.

```json
{
    "nimi": "Opiskelija"
}
```

## Success Response

**Condition** : Lipputyyppi luodaan onnistuneesti

**Code** : `200 ok`

**Response example** 

```json
{
    "tapahtuma_id": 1,
    "nimi": "Opiskelija",
}
```
---

