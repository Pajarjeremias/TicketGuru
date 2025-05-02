# Hae yksittäinen tapahtuman lipputyyppi

Hakee yksittäisen tapahtuman lipputyypin.

**URL** : `/api/tapahtumanlipputyypit/{id}`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : -

## Success Responses

**Condition** : Jos yksittäinen tapahtuman lipputyyppi onnistuttiin löytämään.

**Code** : `200 OK`

## Error Responses

**Condition** : Mikäli yksilöivällä tunnisteellä ei ole tapahtuma lipputyyppiä.

**Code** : `404 Not Found`

**Content** :

```json
{
    null
}
```

**Content**

esimerkki tapahtuman lipputyyppi, jossa näkyy tapahtuma lipputyypin id, lipputyypin id ja nimi sekä hinta


```json
[
    {
    "tapahtuma_lipputyyppi_id": 1,
    "lipputyyppi": {
        "lipputyyppi_id": 1,
        "lipputyyppi": "Aikuinen"
    },
    "hinta": 30.0
}
]