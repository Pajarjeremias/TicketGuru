# Poista tapahtuman lipputyyppi

Poista tapahtuman lipputyyppi tietokannasta

**URL** : `/api/lipputyypit/{id}/`

**URL Parameters** : `pk=[integer]` missä `pk` on tapahtuman lipputyypin id tietokannassa.

**Method** : `DELETE`

**Auth required** : `Kyllä`

**Permissions required**: `Käyttäjä on ylläpitäjä`

**Data** : `{}`

## Onnistunut tapahtuma

**Condition** : Jos tapahtuma onnistuttiin poistaman.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Mikäli ei löytynyt tapahtuman lipputyyppiä, jonka voi poistaa.

**Code** : `404 NOT FOUND`

**Content** : `{}`

### User restriction

**Condition** : Mikäli käyttäjällä ei ole oikeuksia poistaa tapahtuman lipputyyppiä.

**Code** : `403 FORBIDDEN`

**Content** : `{}`