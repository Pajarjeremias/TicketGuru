# Muokkaa lipputyyppiä

Muokkaa jo luotua lipputyyppiä. 

**URL** : `/api/lipputyypit/{id}/`

**Method** : `PUT`

**Auth required** : Kyllä

**Permissions required** : Käyttäjä on ylläpitäjä tai tapahtumavastaaja

**Data constraints**

```json
{
    "nimi": "[String, 2-200 merkkiä]",
}
```

**Data example** 

```json
{
    "jarjestaja_id": 1,
    "nimi": "kaveri"
    
}
```

## Success Responses

**Condition** : Muokkaus onnistunut ongelmitta.

**Code** : `200 OK`

**Content example** :

```json
{
    "nimi": "Uusi nimi 123",
}
```

**Response example** :

```json
{
    "lipputyyppi_id": 1,
    "nimi": "Uusi nimi 123",
}
```

## Error Response

**Condition** : URL:ssa annetulla id:lla ei löydy lipputyyppiä.

**Code** : `200 ok`

**Response Example** :

```json
{
    null
}
```