# Luo myynti

Luo uuden myynnin tietokantaan

**URL** : `/api/myynnit`

**Method** : `POST`

**Auth required** : Kyllä

**Permissions required** : Kyllä

**Data Constraints**

```json

{
        "liput": "[List vittaa lippu_id]",
        "asiakas": null,
        "myyntipaiva": "[LocalDateTime]",
        "myyntipiste": "[Myyntipiste olio, viittaa Long Myyntipiste_id]",
        "maksutapa": "[Maksutapa olio, viittaa Long Maksutapa_id]"
    }
```

## Data example 

```json

{
    "liput": [],
    "asiakas": null,
    "myyntipaiva": "2025-06-15",
    "myyntipiste": {
        "myyntipisteId": 1
    },
    "maksutapa": {
        "maksutapa_id": 1
    }
}

```

## Success response

Uusi myynti luodaan onnistuneesti

**Code** : `201 CREATED`

**Content**

```json
{
    "myynti_id": 3,
    "asiakas": null,
    "myyntipaiva": "2025-06-15",
    "myyntipiste": {
        "nimi": null,
        "katuosoite": null,
        "postitoimipaikka": null,
        "myyntipisteId": 1
    },
    "maksutapa": {
        "maksutapa_id": 1,
        "maksutapa": null
    }
}
```

## Error respones

**Condition** : Jos annettu data ei ole kelvollista.

**Code** : `400 BAD REQUEST`

**Content** : Ei sisältöä `{}`