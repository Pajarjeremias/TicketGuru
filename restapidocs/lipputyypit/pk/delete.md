# Poista lipputyyppi

Poista lipputyyppi tietokannasta

**URL** : `/api/lipputyypit/{id}/`

**URL Parameters** : `pk=[integer]` missä `pk` on lipputyypin id tietokannassa.

**Method** : `DELETE`

**Auth required** : `Kyllä`

**Permissions required**: `Käyttäjä on ylläpitäjä`

**Data** : `{}`

## Onnistunut tapahtuma

**Condition** : Jos tapahtuma onnistuttiin poistaman.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Mikäli ei löytynyt lipputyyppiä, jonka voi poistaa.

**Code** : `404 NOT FOUND`

**Content** : `{}`

### User restriction

**Condition** : Mikäli käyttäjällä ei ole oikeuksia poistaa lipputyyppiä.

**Code** : `403 FORBIDDEN`

**Content** : `{}`
