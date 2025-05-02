# Muokkaa tapahtuman lipputyyppiä

Muokkaa jo luotua tapahtuman lipputyyppiä. 

**URL** : `/api/tapahtumanlipputyypit/{id}/`

**Method** : `PUT`

**Auth required** : Kyllä

**Permissions required** : Käyttäjä on ylläpitäjä tai tapahtumavastaaja

**Data constraints**

```json
{
    "hinta": "[float]",
}
```

**Data example** 

```json
{
    "tapahtuma_lipputyyppi_id": 1,
    "lipputyyppi": {
        "lipputyyppi_id": 1,
        "lipputyyppi": "Aikuinen"
    },
    "hinta": 25.5
}
```

## Success Responses

**Condition** : Muokkaus onnistunut ongelmitta.

**Code** : `200 OK`

**Content example** :

```json
{
    "hinta": 25.7
}
```

**Response example** :

```json
{
    "tapahtuma_lipputyyppi_id": 1,
    "lipputyyppi": {
        "lipputyyppi_id": 1,
        "lipputyyppi": "Aikuinen"
    },
    "hinta": 25.7
}
```

---

**Condition** : Annettu data on väärässä muodossa.

**Code** : `400 BAD REQUEST`

**Content Example** :

Annettu hinta on väärässä muodossa.

```json
{
    "hinta": kaksikymmentäviisi
}
```

**Response Example** :

```json
{
     "status": 400,
    "error": "Bad Request",
    "trace": "org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Unrecognized token 'kaksikymmentäviisi': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\r\n\tat org.springframework.http.converter.json.
    ]
}
```
