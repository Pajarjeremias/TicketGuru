
# RESTAPIDocs TicketGuru

BASE URL 'http://localhost:8080/'.



### Tapahtumat liittyvät 

Endpointit Tapahtumat taulun katsomiseen ja muokkaamiseen

* [Näytä kaikki tapahtumat](tapahtumat/get.md) : `GET /api/tapahtumat/`
* [Luo tapahtuma](tapahtumat/create.md) : `POST /api/tapahtumat/`
* [Näytä tapahtuma](tapahtumat/pk/get.md) : `GET /api/tapahtumat/:pk/`
* [Muokkaa tapahtumaa](tapahtumat/pk/put.md) : `PUT /api/tapahtumat/:pk/`
* [Poista tapahtuma](tapahtumat/pk/delete.md) : `DELETE /api/tapahtumat/:pk/`

### Myynnit liittyvät

* [Näytä kaikki myynnit](myynnit/get.md) : `GET /api/myynnit/`
* [Näytä yksi myynti](myynnit/pk/get.md) : `GET /api/myynnit/:pk/`
* [Näytä yhden myynnin liput](myynnit/pk/get.md) : `GET /api/myynnit/:pk/liput`

### Lippuihin liittyvät
* [Luo lippu](liput/create.md) : `POST /api/liput/`
* [Muokkaa lippua](liput/put.md) : `POST /api/liputtiedoilla/:pk`
