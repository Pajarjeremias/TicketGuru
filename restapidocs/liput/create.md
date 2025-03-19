# Luo lippu

Luo lippu tapahtuma järjestelmään

**URL** : `/api/liput/`

**Method** : `POST`

**Auth required** : Ei tällä hetkellä, tulevaisuudessa kyllä.

**Permissions required** : Ei tällä hetkellä, tulevaisuudessa käyttäjän tulee olla ylläpitäjä.

**Data constraints**

```json
{
  "myynti_id": "[Integer, REQUIRED]",
  "tapahtuman_lipputyypit_id": "[Integer, REQUIRED]",
  "hinta": "[Float, OPTIONAL]"
}
```
- myynti_id
  - sen myynnin id, johon lippu liittyy
  - <u>pakollinen</u>
- tapahtuman_lipputyypit_id
  - se tapahtuman_lipputyyppi_id, johon lippu liittyy
  - kertoo lipun tyypin (esim. aikuinen) ja tapahtuman johon lippu liittyy
  - <u>pakollinen</u>
- hinta
  - hinta, jolla lippu on myyty
  - jos hintaa ei ole annettu, oletuksena haetaan lipulle sen tapahtuman_lipputyypin hinta, johon se liittyy
  <u>vapaaehtoinen</u>

  Tarkastanut ja tarkastus_pvm ei anneta lippua luodessa, ne lisätään tarkastusvaiheessa.
  Lipun tilaa ei voi määrittää luontivaiheessa, se on automaattisesti myyty.

**Data example 1**
Myynnin id on 1 ja tapahtuman_lipputyypin id on niin ikään 1. Hinnaksi on annettu 29.00.

```json
{
  "myynti_id": 1,
  "tapahtuman_lipputyypit_id": 1,
  "hinta": 29.00
}
```

**Data example 2**
Myynnin id on taas 1 ja tapahtuman_lipputyypin id on niin ikään 1. Hintaa ei ole annettu, joten järjestelmä hakee lipun hinnaksi sen hinnan, joka on annettu tapahtuman_lipputyypille, jonka id on 1.

```json
{
  "myynti_id": 1,
  "tapahtuman_lipputyypit_id": 1,
}
```

## Success Response

**Condition** : Lippu on luotu ja tallennettu onnistuneesti

**Code** : `201 CREATED`

**Content** : Luodun lipun tiedot.

**Content example** 

```json
{
    "lippu_id": 2,
    "tapahtuman_lipputyyppi": {
        "tapahtuma_lipputyyppi_id": 1,
        "lipputyyppi": {
            "lipputyyppi_id": 1,
            "lipputyyppi": "Aikuinen"
        },
        "hinta": 30.0
    },
    "hinta": 30.0,
    "tila": {
        "tila_id": 1,
        "tila": "Myyty"
    },
    "tarkastanut": null,
    "tarkastus_pvm": null,
    "myynti": {
        "myynti_id": 1,
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
}
```

## Error Response

**Condition** Jos kutsussa lähetettyä dataa puuttuu tai se ei ole kelvollista. Esim. annetulla myynti id:llä ei löydy myyntiä tietokannasta

**Code** : `400 BAD REQUEST`

**Content** : `error message`

Jos myynti_id tai tapahtuman_lipputyypit_id on laitettu id, jota ei ole olemassa: Invalid value for 'myynti' and 'tapahtuman_lipputyyppi', please check. Id must be a valid id-number.

Jos myynti_id tai tapahtuman_lipputyypit_id puuttuu kokonaan tai on null-arvoinen: The given id must not be null.

Jos myynti_idtä ei ole olemassa: Invalid value for 'myynti', please check.Id must be a valid id-number.

Jos tapahtuman_lipputyypit_idtä ei ole olemassa: Invalid value for 'tapahtuman_lipputyyppi', please check.Id must be a valid id-number.

Jos annettu hinta on alle 0€: Invalid value for 'hinta'. Can't be under 0€, please check.

Jos syöte on väärässä muodossa, esim. Long tilalla String: "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku."


**Content example** : 

```
Invalid value for 'myynti', please check.
```

