# Poista tapahtuma

Poista tapahtuma tietokannasta

**URL** : `/api/accounts/:pk/`

**URL Parameters** : `pk=[integer]` where `pk` is the ID of the Account in the
database.

**Method** : `DELETE`

**Data** : `{}`

## Success Response

**Condition** : If the Account exists.

**Code** : `204 NO CONTENT`

**Content** : `{}`

## Error Responses

**Condition** : If there was no Account available to delete.

**Code** : `404 NOT FOUND`

**Content** : `{}`

### Or

**Condition** : Authorized User is not Owner of Account at URL.

**Code** : `403 FORBIDDEN`

**Content** : `{}`


## Notes

* Tulee poistamaan tapahtuman myös kaikilta lipun ostaneilta ja järjestäjiltä