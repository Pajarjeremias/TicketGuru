# Hae yksittäinen myynti

Hakee yksittäisen myynnin.

**URL** : `/api/myynnit/{id}`

**Method** : `GET`

**Auth required** : Ei

**Permissions required** : Ei

**Data constraints** : 


```json
{
        "myynti_id": "[Long identity]",
        "liput": "[List vittaa lippu_id]",
        "asiakas": null,
        "myyntipaiva": "[LocalDateTime]",
        "myyntipiste": "[Myyntipiste olio, viittaa Long Myyntipiste_id]",
        "maksutapa": "[Maksutapa olio, viittaa Long Maksutapa_id]"
    }
```

## Success Responses

**Condition** : Jos yksittäinen myynti onnistuttiin löytämään.

**Code** : `200 OK`

**Content**

Myynnin tiedot. Alla esimerkki.

```json

    {
        "myynti_id": 1,
        "liput": [],
        "asiakas": null,
        "myyntipaiva": "2025-06-15",
        "myyntipiste": {
            "nimi": "Ensimmäinen piste",
            "katuosoite": "Messuaukio 1",
            "postitoimipaikka": {
                "postinumero": "00520",
                "postitoimipaikka": "Helsinki",
                "maa": "Suomi"
            },
            "myyntipisteId": 1
        },
        "maksutapa": {
            "maksutapa_id": 1,
            "maksutapa": "Käteinen"
        }
    }

```

## Error Responses

**Condition** : Mikäli yksilöivällä tunnisteellä ei ole myyntejä.

**Code** : `200 ok`

**Content** : `null`


