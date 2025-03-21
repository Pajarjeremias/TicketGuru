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
        "myyntipiste": "[Myyntipiste olio, viittaa Long Myyntipiste_id]",
        "maksutapa": "[Maksutapa olio, viittaa Long Maksutapa_id]"
    }
```

## Data example 

```json

{
    "liput": [],
    "asiakas": null,
    "myyntipiste": {
        "myyntipisteId": 1
    },
    "maksutapa": {
        "maksutapa_id": 1
    }
}

```

## Success response

Uusi myynti luodaan onnistuneesti. 
Myyntipäiväksi tulee tämän hetkinen päivä.
HUOM! asiakas täytyy olla vielä tässä vaiheessa null

**Code** : `201 CREATED`

**Content**

```json
{
    "myynti_id": 3,
    "asiakas": null,
    "myyntipaiva": "2025-03-21",
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

## Error respones

**Condition** : Jos annettu data ei ole kelvollista.

**Code** : `400 BAD REQUEST`

**Content** : Antaa virheilmoituksessa sisällön ja viittauksen mihin ongelma liittyy`{}`


**Condition** : Jos annettua myyntipisteId:tä ei ole olemassa.

**Code** : `404 Not Found`

**Content** : Palauttaa myös virheellisen Id:n numeron osana tekstiä
```json
{
    "Not Found": " invalid value for myyntipiste. Please check id. Id must be a valid id number. Myyntipiste ID 2 not found"
}
```


**Condition** : Jos annettu maksutapaId:tä ei ole olemassa.

**Code** : `404 Not Found`

**Content** : 

```json
{
    "Not Found": " invalid value for maksutapa. Please check id. Id must be a valid id number. Maksutapa ID 6 not found"
}
```


**Condition** : Jos molemmat myyntipiste ja maksutapa Id ovat virheellisiä.

**Code** : `404 Not Found`

**Content** : 

```json
{
    "Not Found": " invalid value for myyntipiste and maksutapa. Id must be valid. Myyntipiste ID 5 Maksutapa ID 6 not found"
}
```

Esimerkki:
Post, missä asiakasta 101 ei ole olemassa:
```json
{
    "liput": [],
    "asiakas": 101,
    "myyntipiste": {
        "myyntipisteId": 1
    },
    "maksutapa": {
        "maksutapa_id": 1
    }

}
```

**Code** : `404 Not Found`

**Content** : 

```json
{
    "virhe": "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku.JSON parse error: Cannot construct instance of `projekti.demo.model.Kayttaja` (although at least one Creator exists): no int/Int-argument constructor/factory method to deserialize from Number value (0)"
}
```
