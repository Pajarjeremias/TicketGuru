# TicketGuru

Scrummerit4ever: Antti Varis, Matias Nisonen, Roosa Vuorela,
Timo Lampinen, Jeremias Pajari

## Johdanto

TicketGuru on lipunmyyntijärjestelmä, joka on suunniteltu helpottamaan lipunmyyntitoimistojen arkea. Järjestelmä tehostaa lippujen myyntiä, lippujen tulostamista ja niiden tarkastamista tapahtumissaan. TicketGuru-järjestelmä palvelee lipputoimiston eri käyttäjäryhmiä, kuten lipunmyyjiä, tapahtumajärjestäjiä, henkilökuntaa ovella sekä asiakkaita. 

TicketGuru mahdollistaa tapahtuman luomisen ja hallinnan, myyntiraporttien tarkastelun sekä lippujen myynnin. Liput voidaan merkitä käytetyiksi ovella. Myyntipisteessä järjestelmä tukee lippujen tulostamista asikkailleen. Ennakkomyynnistä ylijääneet liput voidaan tulostaa ovelle myytäviksi. Asiakas voi ostaa lipun itselleen lipunmyyntipisteeltä tai tapahtuman ovelta.  

TicketGuru hyödyntää Javaa toimiakseen. Käyttöliittymä on responsiivinen ja toteutetaan nykyaikaisia työkaluja, kuten Reactia käyttämällä. Käyttöliittymää on selkeä navigoida ja sisältää keskeiset toiminnot. Eri käyttäjäryhmille on omat näkymät. Järjestelmän tiedot tallennetaan relaatiotietokantaan.  


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
- Tulostaa myymättä jääneet liput tapahtuman ovella
  
**3. Henkilökunta ovella**
  
Henkilö, joka työskentelee tapahtuman ovella.
- Myy jäljelle jääneitä lippuja asiakkaille ovella
- Merkitsee lipun käytetyksi

**4. Asiakas**

Henkilö, joka ostaa lipun tapahtumaan lipunmyyntipisteestä tai ovelta.

**5. Järjestelmän ylläpitäjä**

Henkilö/taho, joka vastaa ohjelmiston teknisestä ylläpidosta. Esim. käyttäjäryhmien- ja oikeuksien määrittelemisestä.

**6. Tulostin**

Järjestelmäkäyttäjä, jonka kautta tulostetaan ylijääneet liput myytäväksi.

### Käyttötapauskaavio

![käyttötapauskaavio](https://github.com/Pajarjeremias/TicketGuru/blob/develop/k%C3%A4ytt%C3%A4j%C3%A4roolit.png)

### Käyttäjätarinat

**Tapahtumajärjestäjänä**, **haluan** tarkistaa liput ovella helposti, **jotta** voin merkitä käytetyt liput ja estää väärinkäytökset.

**Lipputoimiston työntekijänä**, **haluan** nähdä myyntiraportit, **jotta** voin seurata myynnin kehitystä.

**Lippumyyjänä**, **haluan** tulostaa loput ennakkomyynnistä ylijääneet liput, **jotta** voin myydä ne tapahtuman ovella.

**Lipputoimiston työntekijänä**, **haluan** pystyä hallinnoimaan tapahtumia järjestelmässä, **jotta** voin hallita lippuja sekä päivittää tapahtumatietoja.

**Asiakkaana**, **haluan** mahdollisuuden maksaa liput eri maksutavoilla, **jotta** voin valita itselleni sopivan maksutavan.

**Tapahtuman järjestäjänä**, **haluan** määritellä erilaisia lipputyyppejä, **jotta** osaan tarjota oikeanlaisia hintoja eläkeläisille, lapsille sekä opiskelijoille.

**Järjestelmän ylläpitäjänä**, **haluan** määritellä eri käyttäjärooleja, **jotta** voin hallita järjestelmän käyttöoikeuksia turvallisesti.

**Lippumyyjänä**, **haluan** myydä ja tulostaa liput asiakkalle vaivattomasti, **jotta** asiakas saa ostoksensa sujuvasti.

## Käyttöliittymä

**Käyttöliittymä ja lippujen myynti**
![alt text](tgimage1.png "Käyttöliittymä ja lippujen myynti")

**Tapahtumien muokkaminen ja lisääminen**
![alt text](tgimage2.png "Käyttöliittymä ja lippujen myynti")

**Myyntiraportit**
![alt text](tgimage3.png "Käyttöliittymä ja lippujen myynti")



## Tietokanta

Järjestelmään säilöttävä ja siinä käsiteltävät tiedot ja niiden väliset suhteet
kuvataan käsitekaaviolla. Käsitemalliin sisältyy myös taulujen välisten viiteyhteyksien ja avainten
määritykset. Tietokanta kuvataan käyttäen jotain kuvausmenetelmää, joko ER-kaaviota ja UML-luokkakaaviota.

Lisäksi kukin järjestelmän tietoelementti ja sen attribuutit kuvataan
tietohakemistossa. Tietohakemisto tarkoittaa yksinkertaisesti vain jokaisen elementin (taulun) ja niiden
attribuuttien (kentät/sarakkeet) listausta ja lyhyttä kuvausta esim. tähän tyyliin:

![tietokaavio](https://github.com/Pajarjeremias/TicketGuru/blob/develop/tietokaavio.png)

> ### _Tilit_
> _Tilit-taulu sisältää käyttäjätilit. Käyttäjällä voi olla monta tiliä. Tili kuuluu aina vain yhdelle käyttäjälle._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> id | int PK | Tilin id
> nimimerkki | varchar(30) |  Tilin nimimerkki
> avatar | int FK | Tilin avatar, viittaus [avatar](#Avatar)-tauluun
> kayttaja | int FK | Viittaus käyttäjään [käyttäjä](#Kayttaja)-taulussa
>
> ### _Jarjestajat_
> _Jarjestajat-taulu sisältää organisaatiot, jotka järjestävät tapahtuman. Järjestäjä voi järjestää monta tapahtumaa. Järjestäjillä on yksilöivät tunnisteet._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Jarjestaja_id | int PK | [Jarjestajat](#Jarjestajat)-taulun yksilöivä  tunniste ja samalla primary key
> Nimi | Varchar(30) | Järjestäjä organisaation nimi
> Yhteyshenkilo_id | int FK | Viittaus [Kayttajat](#Kayttajat)-taulun kayttaja_id:hen
> Katuosoite | varchar(30) | Järjestäjä organisaation toimipisteen osoite
> Postinumero | int FK | Viittaus [Postitoimipaikat](#Postitoimipaikat)-taulun, postinumero avaimeen
>
> ### _Postitoimipaikat_
> _Postitoimipaikat-taulu sisältää postitoimipaikkoja._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Postinumero | int PK | [Postitoimipaikat](#Postitoimipaikat)-taulun yksilöivä avain
> Postitoimipaikka | varchar(30) | Postitoimipaikan paikkakunta
> Maa | varchar(30) | Postitoimipaikan maa
>
> ### _Lipputyypit_
> _Lipputyypit-taulu sisältää lipputyyppien vaihtoehdot._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Lipputyyppi_id | int PK | Lipputyypin yksilöivä avain/id
> Lipputyyppi | varchar(30) | Lipptyypin nimi, esim. aikuinen, eläkeläinen, opsikelija, lapsi
> 
> ### _Tilat_
> _Tilat-taulu sisältää lippujen eri tila-vaihtoehdot._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Tila_id | int PK | Tilan yksilöivä avain/id
> Tila | varchar(30) | Tilan nimi, esim. myymättä, myyty, tarkastettu, peruttu
>
> ### _Liput_
> _Liput-taulu sisältää yksilölliset liput. Jokainen lippu liittyy vain yhteen tapahtumaan, tyyppiin, lipputyyppiin, tilaan ja käyttäjään._
>
> Kenttä | Tyyppi | Kuvaus
> ------ | ------ | ------
> Lippu_id | int PK | Lipun yksilöivä avain/id
> Tapahtuma_id | int FK | Tapahtuma, johon lippu liittyy, viittaus [Tapahtumat](#Tapahtumat)-tauluun
> Tyyppi_id | int FK | Lipun tyyppi, viittaus [Lipputyypit](#Lipputyypit)-tauluun
> Hinta | int | Lipun perushinta
> Tila_id | int FK | Lipun tila, viittaus [Tilat](#Tilat)-tauluun
> Tarkastanut_id | int FK | Lipun tarkastanut henkilö, viittaus käyttäjään [Kayttajat](#Kayttajat)-taulussa
> Tarkistus_pvm | date | Päivämäärä, jolloin lippu on tarkasettu ovella, eli käytetty
>


## Tekninen kuvaus

Teknisessä kuvauksessa esitetään järjestelmän toteutuksen suunnittelussa tehdyt tekniset
ratkaisut, esim.

-   Missä mikäkin järjestelmän komponentti ajetaan (tietokone, palvelinohjelma)
    ja komponenttien väliset yhteydet (vaikkapa tähän tyyliin:
    https://security.ufl.edu/it-workers/risk-assessment/creating-an-information-systemdata-flow-diagram/)
-   Palvelintoteutuksen yleiskuvaus: teknologiat, deployment-ratkaisut yms.
-   Keskeisten rajapintojen kuvaukset, esimerkit REST-rajapinta. Tarvittaessa voidaan rajapinnan käyttöä täsmentää
    UML-sekvenssikaavioilla.
-   Toteutuksen yleisiä ratkaisuja, esim. turvallisuus.

Tämän lisäksi

-   ohjelmakoodin tulee olla kommentoitua
-   luokkien, metodien ja muuttujien tulee olla kuvaavasti nimettyjä ja noudattaa
    johdonmukaisia nimeämiskäytäntöjä
-   ohjelmiston pitää olla organisoitu komponentteihin niin, että turhalta toistolta
    vältytään

## Testaus

Tässä kohdin selvitetään, miten ohjelmiston oikea toiminta varmistetaan
testaamalla projektin aikana: millaisia testauksia tehdään ja missä vaiheessa.
Testauksen tarkemmat sisällöt ja testisuoritusten tulosten raportit kirjataan
erillisiin dokumentteihin.

Tänne kirjataan myös lopuksi järjestelmän tunnetut ongelmat, joita ei ole korjattu.

## Asennustiedot

Järjestelmän asennus on syytä dokumentoida kahdesta näkökulmasta:

-   järjestelmän kehitysympäristö: miten järjestelmän kehitysympäristön saisi
    rakennettua johonkin toiseen koneeseen

-   järjestelmän asentaminen tuotantoympäristöön: miten järjestelmän saisi
    asennettua johonkin uuteen ympäristöön.

Asennusohjeesta tulisi ainakin käydä ilmi, miten käytettävä tietokanta ja
käyttäjät tulee ohjelmistoa asentaessa määritellä (käytettävä tietokanta,
käyttäjätunnus, salasana, tietokannan luonti yms.).

## Käynnistys- ja käyttöohje

Tyypillisesti tässä riittää kertoa ohjelman käynnistykseen tarvittava URL sekä
mahdolliset kirjautumiseen tarvittavat tunnukset. Jos järjestelmän
käynnistämiseen tai käyttöön liittyy joitain muita toimenpiteitä tai toimintajärjestykseen liittyviä asioita, nekin kerrotaan tässä yhteydessä.

Usko tai älä, tulet tarvitsemaan tätä itsekin, kun tauon jälkeen palaat
järjestelmän pariin !
