# TicketGuru

Scrummerit4ever: Antti Varis, Matias Nisonen, Roosa Vuorela,
Timo Lampinen, Jeremias Pajari

## Johdanto

TicketGuru on lipunmyyntijärjestelmä, joka on suunniteltu helpottamaan lipunmyyntitoimistojen arkea. Järjestelmä tehostaa lippujen myyntiä, lippujen tulostamista ja niiden tarkastamista tapahtumissaan. TicketGuru-järjestelmä palvelee lipputoimiston eri käyttäjäryhmiä, kuten lipunmyyjiä, tapahtumajärjestäjiä, henkilökuntaa ovella sekä asiakkaita. 

TicketGuru mahdollistaa tapahtuman luomisen ja hallinnan, myyntiraporttien tarkastelun sekä lippujen myynnin. Liput voidaan merkitä käytetyiksi ovella. Myyntipisteessä järjestelmä tukee lippujen tulostamista asikkailleen. Asiakas voi ostaa lipun itselleen lipunmyyntipisteeltä tai tapahtuman ovelta.  

TicketGuru hyödyntää SpringBoot-kehystä ja Javaa toimiakseen. Käyttöliittymä toteutetaan nykyaikaisia työkaluja, kuten Reactia käyttämällä. Käyttöliittymää on selkeä navigoida ja sisältää keskeiset toiminnot sekä kirjautumisen. Järjestelmän tiedot tallennetaan relaatiotietokantaan ja julkaistaan Rahti-palvelun avulla.  


## Järjestelmän määrittely

Määrittelyssä järjestelmää tarkastellaan käyttäjän näkökulmasta. Järjestelmän
toiminnot hahmotellaan käyttötapausten tai käyttäjätarinoiden kautta, sekä kuvataan järjestelmän
käyttäjäryhmät.

### Käyttäjäryhmät

**1. Tapahatumanjärjestäjä**

Taho, joka järjestää tapahtuman ja käyttää tapahtuman lipunmyynnissä apuna TicketGuru -ohjelmistoa. Tämä sisältää mm:
- Uuden tapahtuman määrittäminen ja sen luominen järjestelmään
- Jo olemassa olevan tapahtuman tietojen muuttaminen
- Myyntiraportttien tarkastelu

**2. Lipunmyyjä lipunmyyntipisteessä**

Henkilö, joka myy tapahtuman ovella lippuja asiakkaille lipunmyyntipisteessä.
- Tulostaa ostetun lipun asiakkaalle
  
**3. Henkilökunta ovella**
  
Henkilö, joka työskentelee tapahtuman ovella.
- Myy jäljelle jääneitä lippuja asiakkaille ovella
- Merkitsee lipun käytetyksi

**4. Asiakas**

Henkilö, joka ostaa lipun tapahtumaan lipunmyyntipisteestä tai ovelta.

**5. Järjestelmän ylläpitäjä**

Henkilö/taho, joka vastaa ohjelmiston teknisestä ylläpidosta. Esim. käyttäjäryhmien- ja oikeuksien määrittelemisestä.

### Käyttötapauskaavio

![käyttötapauskaavio](https://github.com/Pajarjeremias/TicketGuru/blob/develop/k%C3%A4ytt%C3%A4j%C3%A4roolit.png)

### Käyttäjätarinat

**Tapahtumajärjestäjänä**, **haluan** tarkistaa liput ovella helposti, **jotta** voin merkitä käytetyt liput ja estää väärinkäytökset.

**Lipputoimiston työntekijänä**, **haluan** nähdä myyntiraportit, **jotta** voin seurata myynnin kehitystä.

**Lipputoimiston työntekijänä**, **haluan** pystyä hallinnoimaan tapahtumia järjestelmässä, **jotta** voin hallita lippuja sekä päivittää tapahtumatietoja.

**Asiakkaana**, **haluan** mahdollisuuden maksaa liput eri maksutavoilla, **jotta** voin valita itselleni sopivan maksutavan.

**Tapahtuman järjestäjänä**, **haluan** määritellä erilaisia lipputyyppejä, **jotta** osaan tarjota oikeanlaisia hintoja eläkeläisille, lapsille sekä opiskelijoille.

**Lippumyyjänä**, **haluan** myydä ja tulostaa liput asiakkalle vaivattomasti, **jotta** asiakas saa ostoksensa sujuvasti.

## Käyttöliittymä

Suunnitelma käyttöliittymästä:

**Käyttöliittymä ja lippujen myynti**
![alt text](tgimage1.png "Käyttöliittymä ja lippujen myynti")

**Tapahtumien muokkaminen ja lisääminen**
![alt text](tgimage2.png "Käyttöliittymä ja lippujen myynti")

**Myyntiraportit**
![alt text](tgimage3.png "Käyttöliittymä ja lippujen myynti")


Toteutunut käyttöliittymä: 



## Tietokanta

![tietokantakaavio](https://github.com/Pajarjeremias/TicketGuru/blob/develop/tietokantakaavio.png)

> ### _Jarjestajat_
> _Jarjestajat-taulu sisältää organisaatiot, jotka järjestävät tapahtuman. Järjestäjä voi järjestää monta tapahtumaa. Järjestäjillä on yksilöivät tunnisteet._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Jarjestaja_id | int PK | [Jarjestajat](#Jarjestajat)-taulun yksilöivä  tunniste ja samalla primary key
> Nimi | Varchar(30) | Järjestäjä organisaation nimi
> Yhteyshenkilo_id | int FK | Viittaus [Kayttajat](#Kayttajat)-taulun kayttaja_id:hen
> Katuosoite | varchar(30) | Järjestäjä organisaation toimipisteen osoite
> Postinumero | varchar FK | Viittaus [Postitoimipaikat](#Postitoimipaikat)-taulun, postinumero avaimeen
>
> ### _Postitoimipaikat_
> _Postitoimipaikat-taulu sisältää postitoimipaikkoja._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Postinumero | varchar PK | [Postitoimipaikat](#Postitoimipaikat)-taulun yksilöivä avain
> Postitoimipaikka | varchar(30) | Postitoimipaikan paikkakunta
> Maa | varchar(30) | Postitoimipaikan maa
>
> ### _Lipputyypit_
> _Lipputyypit-taulu sisältää lipputyyppien vaihtoehdot._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Lipputyyppi_id | int PK, not null | Lipputyypin yksilöivä avain/id
> Tapahtuman_Lipputyypit | list | Lista tapahtumien_lipputyypestä, jotka liittyvät lipputyyppiin
> Lipputyyppi | varchar(30), not null | Lipptyypin nimi, esim. aikuinen, eläkeläinen, opsikelija, lapsi
> 
> ### _Tilat_
> _Tilat-taulu sisältää lippujen eri tila-vaihtoehdot._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Tila_id | int PK | Tilan yksilöivä avain/id
> Tila | varchar(30) | Tilan nimi, esim. myymättä, myyty, tarkastettu, peruttu
> Liput | list | Lista lipuista, joilla on ko tila
>
> ### _Liput_
> _Liput-taulu sisältää yksilölliset liput. Jokainen lippu liittyy vain yhteen tapahtumaan, tyyppiin, lipputyyppiin, tilaan ja käyttäjään._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Lippu_id | int PK, not null | Lipun yksilöivä avain/id
> Tapahtuma_lipputyyppi | int FK, not null | Tapahtuma + lipputyyppi, viittaus [Tapahtuman_lipputyypit](#Tapahtuman_lipputyypit)-tauluun
> Hinta | decimal(2) | Lipun lopullinen myyntihinta
> Tila | int FK | Lipun tila, viittaus [Tilat](#Tilat)-tauluun
> Tarkastanut | int FK | Lipun tarkastanut henkilö, viittaus käyttäjään [Kayttajat](#Kayttajat)-taulussa
> Tarkistus_pvm | date | Päivämäärä, jolloin lippu on tarkasettu ovella, eli käytetty
> Myynti | int, FK, not null | Myyntitapahtuma, johon lippu liittyy, viittaus [Myynnit](#Myynnit)-tauluun
> Koodi | string, not nullable | Lipun tarkastuksessa käytettävä uniikki koodi. Luodaan lipulle automaattisesti.
>
> ### _Tapahtuman_lipputyypit_
> _Tapahtuman_lipputyypit-taulu sisältää tietoa siitä millaisia lippuja mihinkin tapahtumaan on myynnissä ja paljonko ne maksavat._
>
> Kenttä | Tyyppi | Kuvaus 
> ------ | ------ | ------
> Tapahtuma_lipputyyppi_id | int, PK, NOT NULL | Yksilöivä tunniste ja primary key 
> Tapahtuma | int, FK, NOT NULL | Viittaus [Tapahtumat](#Tapahtumat) -tauluun
> Lipputyyppi | int, FK, NOT NULL | Viittaus [Lipputyypit](#Lipputyypit) -tauluun
> Hinta | decimal(2), NOT NULL | Lipun myyntihinta
> Liput | list | Tapahtman lipputyyppiin liittyvät liput
>
> ### _Tapahtumat_
> _Tapahtumat-taulu sisältää tapahtumat, jotka järjestetään tietyssä paikassa tietyllä päivämäärällä. Tapahtumalla on myös kuvaus ja viittaus järjestäjään. Tapahtumilla on yksilöivät tunnisteet._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Tapahtuma_id | int PK | [Tapahtumat](#Tapahtumat)-taulun yksilöivä tunnisste ja samalla primary key
> Nimi | Varchar(200) | Tapahtuman nimi
> Paivamaara | date | Päivämäärä ja kellonaika, jolloin tapahtuma alkaa
> Kuvaus | varchar(2500) | Tapahtuman kuvaus
> Tapahtumapaikka | int FK | Viittaus [Tapahtumapaikat](#Tapahtumapaikat)-taulun, Tapahtumapaikka_id avaimeen
> Jarjestaja | int FK | Tapahtuman järjestäjä. Viittaus [Jarjestajat](#jarjestajat)-tauluun
> Lippumaara | int, not null | Tapahtumaan myytävien lippujen määrä
> Tapahtuman_lipputyypit | list | Tapahtuman lipputyypit
>
> ### _Tapahtumapaikat_
> _Tapahtumapaikat-taulu sisältää paikat, joissa tapahtumat järjestetään. Nimen lisäksi se sisältää osoitteen, viittauksen postinumerotauluun ja ihmisten maksimimäärän._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Tapahtumapaikka_id | int PK | [Tapahtumapaikat](#Tapahtumapaikat)-taulun yksilöivä tunnisste ja samalla primary key
> Nimi | Varchar(200) | Paikan nimi
> Katuosoite | varchar(30) | Tapahtumapaikan osoite
> Postinumero | int FK | Viittaus [Postitoimipaikat](#Postitoimipaikat)-taulun, postinumero avaimeen
> Maksimi_osallistujat | int | Maksimimmäärä, mitä paikassa saa olla ihmisiä mukaanlukien esiintyjät, henkilökunta ja lipun ostajat
> Tapahtuma | int FK | Viittaus [Tapahtumat](#Tapahtumat)-taulun, tapahtuma avaimeen
>
> ### _Kayttajat_
> _Kayttajat-taulu sisältää tietoa liittyen yksittäiseen käyttäjään. Käyttäjiä voivat olla esim. lipunmyyjä, asiakas sekä ylläpitäjä._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Kayttaja_id | int, autonumber, PK, not null | Yksiloiva tunniste ja primary key.
> Kayttajatunnus | varchar(100), not null | Käyttäjätunnus
> Salasana_hash | varchar(60), not null | Salasanan hash
> Etunimi | varchar(100) | Käyttäjän etunimi
> Sukunimi | varchar(100) | Käyttäjän sukunimi
> Puh_nro | varchar(20) | Käyttäjän puhelinnumero
> Email | varchar(100) | Käyttäjän sähköpostiosoite
> Katuosoite | varchar(100) | Käyttäjän katuosoite
> Postinumero | varchar(6), FK | Käyttäjän postinumero, viittaus [Postitoimipaikat](#postitoimipaikat)-tauluun
> Kayttajatyyppi_id | int, not null, FK | Käyttäjän tyyppi, viittaus [Käyttäjätyypit](#kayttajatyypit)-tauluun
>
> ### Kayttajatyypit
> Kayttajatyypit-taulu sisältää tietoa erilaisista kayttajatyypeista.
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Kayttajatyyppi_id | int, autonumber, PK, not null | Yksilöivä tunniste ja primary key
> Kayttajatyyppi | varchar(20), not null |  Kayttajatyypin nimi esim. asiakas, lipuntarkastaja, lipunmyyjä, tapahtumavastaava tai ylläpitäjä.
> Kuvaus | varchar(500) | Vapaaehtoinen kuvaus käyttäjätyypille
> Käyttäjät | list | Lista käyttäjistä, joilla on ko käyttäjätyyppi
>
> ### _Myynnit_
> _Myynnit-taulu sisältää tietoa lippujen myymisestä asiakkaalle (myyntitapahtumat)._
>
> Kenttä | Tyyppi | Kuvaus 
> ------ | ------ | ------
> Myynti_id | int, PK, NOT NULL | Yksilöivä tunniste ja primary key 
> Asiakas | int, FK | Viittaus [Kayttajat](#Kayttaja_id) -tauluun
> Myyntipaiva | date, NOT NULL | Päivämäärä sekä kellonaika jolloin myynti tapahtui
> Myyntipiste | int, FK, NOT NULL | Viittaus [Myyntipisteet](#Myyntipiste_id) -tauluun
> Maksutapa | int, FK, NOT NULL | Maksutapa, viittaus [Maksutavat](#Maksutavat) -tauluun
> Liput | list | Lista myynnin lipuista
>
> ### _Maksutavat_
> _Maksutavat_-taulu sisältää tietoa maksutavoista
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Maksutapa_id | int, PK, NOT NULL | Yksilöivä tunniste ja primary key 
> Maksutapa | varchar(100), NOT NULL | Maksutapa
>
> ### _Myyntipisteet_
> _Myyntipisteet-taulu sisältää tietoa myyntipisteistä_
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Myyntipiste_id | int, PK, NOT NULL | Yksilöivä tunniste ja primary key 
> Nimi | varchar(100), NOT NULL | Myyntipisteen nimi
> Katuosoite | varchar(100), NOT NULL | Myyntipisteen katuosoite
> Postinumero | int, FK, NOT NULL | Viittaus [Postitoimipaikat](#Postinumero) - tauluun
> Myynnit | list | Lista myyntipisteen myynneistä


## Tekninen kuvaus

Järjestelmän komponentit:
- Backend ajetaan CSC Rahti ympäristössä Docker-konttina.
- Tietokanta sijaitsee samassa CSC Rahti projektissa erillisenä palveluna.
- Frontend toimii selaimessa ja se on yhteydessä backendiin REST-rajapinnan kautta. 

Käytetyt teknologiat: Spring Boot, Java, PostreSQL, React, Node.js
Deployment on totetutettu CSCn Rahti-pavelussa. 

Rajapinnat: Käytössä on REST-rajapinta. Endpointit ovat kaikki muodossa /api/entieetinNimiMonikossa, esim /api/liput. Mikäli tietoa haetaan tai muokataan id:n perusteella, osoitteen perässä on /id, kuten /api/liput/1. Rajapinta käyttää HTTP-metodeja GET, POST, PUT ja DELETE. 

Turvallisuus on varmistettu HTTP Basic-autentikoinnilla. Rajapinnan metodien käyttöoikeudet on määritelty käyttäjätyyppien mukaan. Sovelluksen käyttäjien salasanat on hash-koodattu. Tietokannan kirjautumistiedoton tallennettu Rahtiin ympäristömuuttujiin, eikä niitä ole kovakoodattu tai julkaistu versionhallinnassa. 

### Tämän lisäksi

-   ohjelmakoodin tulee olla kommentoitua
-   luokkien, metodien ja muuttujien tulee olla kuvaavasti nimettyjä ja noudattaa
    johdonmukaisia nimeämiskäytäntöjä
-   ohjelmiston pitää olla organisoitu komponentteihin niin, että turhalta toistolta
    vältytään

## Testaus

Järjestelmää testataan yksikkötesteillä, integraatiotesteillä ja end-to-end testeillä.
Yksikkötesteillä testataan entieettejä ja niiden metodeja ja repositoryja. Integraatiotesteillä testataan sovelluksen ja tietokannan välistä toimintaa: controlleireita ja repositorioita. Yksikkö- ja integraatiotesteissä käytetään ajonaikaista testitietokantaa.

Tarkemmat testikuvaukset löytyvät erillisestä dokumentista: [testausdokumentaatio](testausdokumentaatio.pdf)

### Korjausta vaativat ongelmat

Ei taida olla?

## Asennustiedot

### järjestelmän kehitysympäristö: 
Järjestelmän kehitysympäristön rakentaminen toiseen koneeseen:

Vaatimukset:
- Java
- Node.js
- PostgreSQL
- Ohjelmointiympäristö, esim VS Code

Projekti löytyy githubista ja se kloonataan paikalliseen tiedostoon.
Frontendia varten tulee siirtyä client hakemistoon ja siellä suorittaa npm install -komento, jolla ajetaan tarvittavat asennukset. Clientin kehitysympäristön saa käyntiin npm run dev -komennolla. Kehitysympäristö on yhteydessä CSC Rahti-palvelussa määriteltyyn tietokantaan, käytössä on PostgreSQL. 


### järjestelmän asentaminen uuteen tuotantoympäristöön:

Kloonaa Repository ja suorita asennukset clientia varten samoin, kuin kehitysympäristössä.
Järjestelmä on suunniteltu julkaistavaksi CSC Rahti -palevelussa. Ottaaksesi uuden tuotantoympäristön käyttöön:
- luo uusi projekti my.csc.fi -sivulla ja linkitä siihen tämä repository
- luo projekti rahti.csc.fi -sivulla ja yhdistä se CSC projektiin
- lisää projektille PostgreSQL tietokanta ja ympäristömuuttujiin URL, dbname, username, password, profiles_active 


## Käynnistys- ja käyttöohje

Käynnistykseen tarvittava URL: https://ticket-guru-2-ticketguru4ever2.2.rahtiapp.fi/login
Tunnukset ylläpitäjänä kirjautumiseen:
Käyttäjätunnus: yllapitaja
Salasana:
"