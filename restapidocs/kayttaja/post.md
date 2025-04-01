# Luo käyttäjä

Luo käyttäjä järjestelmään

**URL** : `/api/kayttajat/`

**Method** : `POST`

**Auth required** : Kyllä

**Permissions required** : Kyllä. Sallittu käyttätyypeille: 'Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja'

**Data constraints**

```json
{
    "kayttajatunnus": "[String, REQUIRED]",
    "salasana_hash": "[String, REQUIRED]",
    "etunimi": "[String, OPTIONAL]",
    "sukunimi": "[String, OPTIONAL]",
    "puh_nro": "[String, OPTIONAL]",
    "email": "[String, OPTIONAL]",
    "katuosoite": "[String, OPTIONAL]",
    "postinumero": {
        "postinumero": "[String, OPTIONAL]",
        "postitoimipaikka": "[String, REQUIRED]",
        "maa": "[String, OPTIONAL]"
    },
    "kayttajatyyppi": {
        "kayttajatyyppi_id": [Integer, Optional],
        "kayttajatyyppi": "[String, REQUIRED]",
        "kuvaus": "[String, REQUIRED]"
    }
}
```
- kayttajatunnus
  - kayttajatunnus, mitä käytetään kirjautumisen yhteydessä
  - käyttäjätunnuksen täytyy olla uniikki, eli jos sama käyttjätunnus on jo järjestelämässä tulee virhe
  - <u>pakollinen</u>
- salasana_hash
  - se salasana johon lippu liittyy
  - salasana kirjoitettuna, salana muuutetaan hash muotoon, ennen tietokantaan tallentamiseta
  - <u>pakollinen</u>
- etunimi
  - käyttäjän etunimi
  <u>vapaaehtoinen</u>
- sukunimi
  - käyttäjän sukunimi
  <u>vapaaehtoinen</u>
- puh_nro
  - käyttäjän puhelinnumero
  <u>vapaaehtoinen</u>
- email
  - käyttäjän sähköpostiosoite
  <u>vapaaehtoinen</u>
- katuosoite
  - käyttäjän katuosoite
  <u>vapaaehtoinen</u>
- postinumero: 
    - postinumero <u>pakollinen</u>
    - postitoimipaikka <u>pakollinen</u>
    - maa <u>vapaaehtoinen</u>
  - jos postinumero on annettu, sen täytyy sisältää postinumero ja postitoimipaikka kentät
  - postinumeroa ei ole pakko määritellä
  <u>vapaaehtoinen</u>
- kayttajatyyppi
  - käyttäjätyyppi. Käyttäjätyyppi määrittää käyttäjän oikeudet 
  - jos käyttäjätyyppiä ei ole annettu, asetetaan Asiakkaan oikeudet, eli pienimmät mahdolliset oikeudet
  <u>vapaaehtoinen</u>
 

**Data example 1**
Kayttaja 

```json
{
    "kayttajatunnus": "asiakas",
    "salasana_hash": "asiakas",   
    "etunimi": "Jeremias",
    "sukunimi": "Pajari",
    "puh_nro": "0449834478",
    "email": "jeremias.pajari@gmail.com",
    "katuosoite": "vanhatie 5",
    "postinumero": {
        "postinumero": "00520",
        "postitoimipaikka": "Helsinki",
        "maa": "Suomi"
    },
    "kayttajatyyppi": {
        "kayttajatyyppi_id": 1,
        "kayttajatyyppi": "Asiakas",
        "kuvaus": "Tuiki tavallinen palveluiden kuluttaja"
    }
}
```

**Data example 2**
Pienellä määrää tietoja. Myös e-mailin voi jättää antamatta. Käyttäjätyypiksi asetetaan Asiakas. Salasana tallentuu hash muodossa.
```json
{
    "kayttajatunnus": "Tarkkaukko",
    "salasana_hash": "valkoinenkuolema1939",   
    "email": "simo.hayha@gmail.com"
}
```

## Success Response

**Condition** : Kayttaja on luotu ja tallennettu onnistuneesti

**Code** : `201 CREATED`

**Content** : Luodun kayttajan tiedot.

**Content example** 

```json
{
    "kayttaja_id": 6,
    "kayttajatunnus": "Tarkkaukko",
    "salasana_hash": "$2a$10$V7rfKTpZmUhDJScD4Z5hwOt2FabQQd/GE.Gg.lnUKqX8NpaTY/V7u",
    "etunimi": null,
    "sukunimi": null,
    "puh_nro": null,
    "email": "simo.hayha@gmail.com",
    "katuosoite": null,
    "postinumero": null,
    "kayttajatyyppi": {
        "kayttajatyyppi_id": 1,
        "kayttajatyyppi": "Asiakas",
        "kuvaus": "Tuiki tavallinen palveluiden kuluttaja"
    }
}
```

## Error Response

**Condition** Jos kutsussa lähetettyä dataa puuttuu tai se ei ole kelvollista. Esim. annetulla postinumerolla ei ole kaikkia tietoja

{
    "kayttajatunnus": "Tarkkaukko",
    "salasana_hash": "valkoinenkuolema1939",   
    "email": "simo.hayhai@gmail.com"
    "postinumero": "88484"
}

**Code** : `400 BAD REQUEST`

**Content** : `{
    "virhe": "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku. Päivämäärän ja ajan tulee olla muodossa yyyy-MM-dd'T'HH:mm:ss.   JSON parse error: Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries"
}`


**Content example** : 

Jos käyttäjänimi on jo varattu. Kaikkien käyttäjänimien täytyy olla yksilöllisiä.

{
    "kayttajatunnus": "asiakas",
    "salasana_hash": "asiakas",
    "etunimi": "Jaska",
    "sukunimi": "Jokunen",
    "email": "charlie.b@gmail.com"
}

**Code** : `400 BAD REQUEST`

**Content** : `Username is allready taken.`



**Content example** : 

Jos kayttajaryhmä on jotain, mitä ei ole määritelty

```json
{
    "kayttajatunnus": "Jumala",
    "salasana_hash": "asiakas",
    "etunimi": "Jaska",
    "sukunimi": 5,
    "email": "charlie.b@gmail.com",
    "kayttajatyyppi": {
        "kayttajatyyppi_id": 77,
        "kayttajatyyppi": "Jumala",
        "kuvaus": "kissa nimetä jumala"
    }
}

```

**Content** : `Invalid value for kayttajatyyppi. Please check ID`

{
  "myynti_id": null,
  "tapahtuman_lipputyypit_id": null,
  "hinta": -1
}

