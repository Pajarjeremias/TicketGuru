# Poista tapahtuma

Poista tapahtuma tietokannasta

**URL** : `/api/accounts/:pk/`

**URL Parameters** : `pk=[integer]` where `pk` is the ID of the Account in the
database.

**Method** : `DELETE`

**Auth required** : `Kyllä`

**Permissions required**: `Käyttäjä on ylläpitäjä`

**Data** : `{}`

## Onnistunut tapahtuma

**Condition** : Jos tapahtuma onnistuttiin poistaman.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : Mikäli ei löytynyt tapahtumaa, jonka voi poistaa.

**Code** : `404 NOT FOUND`

**Content** : `{}`

### Or  -- koodataan myöhemmin, kun käyttöoikeudet on määritelty

**Condition** : Mikäli käyttäjällä ei ole oikeuksia poistaa tapahtumaa.

**Code** : `403 FORBIDDEN`

**Content** : `{}`


## Notes

* Tulee poistamaan tapahtuman myös kaikilta lipun ostaneilta ja järjestäjiltä