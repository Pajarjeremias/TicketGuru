# Hae yksittäinen lipputyyppi

Hakee yksittäisen lipputyypin.

**URL** : `/api/lipputyypit/{id}`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : -

## Success Responses

**Condition** : Jos yksittäinen lipputyyppi onnistuttiin löytämään.

**Code** : `200 OK`

## Error Responses

**Condition** : Mikäli yksilöivällä tunnisteellä ei ole lipputyyppiä.

**Code** : `404 Not Found`

**Content** :

```json
{
    null
}
```

**Content**

esimerkki lipputyyppi, jossa näkyy lipputyypin id ja nimi


```json
[
    {
    "lipputyyppi_id": 1,
    "lipputyyppi": "Aikuinen"
}
]