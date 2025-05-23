package projekti.demo.web;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import projekti.demo.model.Kayttaja;
import projekti.demo.model.KayttajaRepository;
import projekti.demo.model.Lippu;
import projekti.demo.model.LippuRepository;
import projekti.demo.model.LipunKayttohetki;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.PostLippuModel;
import projekti.demo.model.Tapahtuman_lipputyyppi;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;
import projekti.demo.model.Tila;
import projekti.demo.model.TilaRepository;
import projekti.demo.model.PutLippuModel;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@EnableMethodSecurity(securedEnabled = true)
public class LippuRestController {

  LippuRepository lippuRepository;
  MyyntiRepository myyntiRepository;
  Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository;
  TilaRepository tilaRepository;
  KayttajaRepository kayttajaRepository;

  public LippuRestController(
      LippuRepository lippuRepository,
      MyyntiRepository myyntiRepository,
      Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository,
      TilaRepository tilaRepository,
      KayttajaRepository kayttajaRepository) {
    this.lippuRepository = lippuRepository;
    this.myyntiRepository = myyntiRepository;
    this.tapahtuman_lipputyyppiRepository = tapahtuman_lipputyyppiRepository;
    this.tilaRepository = tilaRepository;
    this.kayttajaRepository = kayttajaRepository;
  }

  // Luo uusi lippu
  @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
  @PostMapping(value = { "/api/liput", "/api/liput/" })
  public ResponseEntity<?> createLippu(@RequestBody PostLippuModel lippuTiedot) {

    try {

      // Haetaan tietokannasta pyynnössä annetut myynti + lipputyyppi
      Myynti myynti = myyntiRepository.findById(lippuTiedot.getMyynti_id()).orElse(null);
      Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtuman_lipputyyppiRepository
          .findById(lippuTiedot.getTapahtuman_lipputyypit_id()).orElse(null);

      // Tarkistetaan, että pyynnössä tulleet myynti + lipputyyppi ovat olemassa. Jos
      // ei, heitetään Exception.
      if (myynti == null && tapahtuman_lipputyyppi == null) {
        throw new Exception(
            "Invalid value for 'myynti' and 'tapahtuman_lipputyyppi', please check. Id must be a valid id-number.");
      } else if (myynti == null) {
        throw new Exception("Invalid value for 'myynti', please check.Id must be a valid id-number.");
      } else if (tapahtuman_lipputyyppi == null) {
        throw new Exception("Invalid value for 'tapahtuman_lipputyyppi', please check.Id must be a valid id-number.");
      }

      // Jos hintaa ei ole annettu pyynnössä, haetaan lipun hinnaksi lipputyypin hinta
      Float hinta;
      if (lippuTiedot.getHinta() == null) {
        hinta = tapahtuman_lipputyyppi.getHinta();
      } else if (lippuTiedot.getHinta() < 0) {
        throw new Exception("Invalid value for 'hinta'. Can't be under 0€, please check.");
      } else {
        hinta = lippuTiedot.getHinta();
      }

      // Luodaan saaduilla tiedoilla uusi lippu ja tallennetaan se. Oletustilana
      // "Myyty".
      Lippu uusiLippu = new Lippu(
          tapahtuman_lipputyyppi,
          hinta,
          tilaRepository.findByTila("Myyty"),
          myynti);
      lippuRepository.save(uusiLippu);

      // Palautetaan luodun lipun tiedot
      return new ResponseEntity<Lippu>(uusiLippu, HttpStatus.CREATED);

    } catch (Exception e) {

      // Palautetaab virheviesti
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

  }

  // Hae yksittäinen lippu
  @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
  @GetMapping("/api/liput/{id}")
  public Lippu getLippuById(@PathVariable Long id) {
    return lippuRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löydy id:llä " + id));
  }

  // Hae kaikki liput tai lippu koodilla
  @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
  @GetMapping(value = { "/api/liput", "/api/liput/" })
  public ResponseEntity<?> getKaikkiLiputTaiKoodilla(@RequestParam(required = false) String koodi) {
    try {
      if (koodi != null) {
        return ResponseEntity.ok(lippuRepository.findByKoodi(koodi)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löydy koodilla " + koodi)));
      } else {
        return ResponseEntity.ok(lippuRepository.findAll());
      }

    } catch (DataAccessException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe: ei voitu hakea lippuja", e);
    }
  }

  // patch lippu - merkkaa lippu käytetyksi
  @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
  @PatchMapping(value = { "/api/liput/{id}" })
  public ResponseEntity<?> patchMerkitseLippuKaytetyksi(@PathVariable Long id,
      @RequestBody LipunKayttohetki tieto) {
    try {
      LocalDateTime kayttohetki = tieto.getUsed();
      System.out.println("!!!!!                  !!!!!!!!  Haetaan lippua id:llä " + id);
      Optional<Lippu> lippuOptional = lippuRepository.findById(id);
      if (lippuOptional.isPresent()) {
        Lippu lippu = lippuOptional.get();
        Tila tila = lippu.getTila();
        System.out.println("Lipun tila on: " + tila.getTila());
        if (tila.getTila().equals("Tarkastettu")) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("virhe", "Lippu_Id " + id + " on jo käytetty. Tarkastusaika: "+lippu.getTarkastus_pvm()));
        } else if (tila.getTila().equals("Peruttu")) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("virhe", "Lippu_Id " + id + " on peruttu."));
        } else {
          tila = tilaRepository.findByTila("Tarkastettu");
          lippu.setTila(tila);
          lippu.setTarkastus_pvm(kayttohetki);
          lippuRepository.save(lippu);
          System.out.println("Tilaid:" + tila.getTila_id() + " tilanimi: " + tila.getTila());
          Lippu tallennettuLippu = lippuRepository.save(lippu);
          Optional<Lippu> haettulippuopt = lippuRepository.findById(id);
          if (haettulippuopt.isPresent()) {
            Lippu haettulippu = haettulippuopt.get();
            System.out.println("haettulippu tila: " + haettulippu.getTila().getTila());
            System.out.println("haettulippu pvm: " + haettulippu.getTarkastus_pvm());
            lippu = haettulippu;
          }

          // Palautetaan ok ja muutettu lippu
          return new ResponseEntity<Lippu>(lippu, HttpStatus.OK);
        }
      } else
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("virhe", "Lippu_Id " + id + " ei ole olemassa."));

    } catch (DataAccessException e) {
      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe: ei voitu hakea lippuja", e);
    }
  }


  // päivitä lippua enemmillä tiedoilla
  @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
  @PutMapping("/api/liput/{id}")
  public ResponseEntity<?> paivitaLipuntietoja(@PathVariable Long id, @RequestBody PutLippuModel lippuTiedot) {
    try {

      // lataa lipun id:llä olevat tiedot ja tallenna ne uusiLippu:n
      Lippu uusiLippu = lippuRepository.findById(id).orElse(null);
      if (uusiLippu == null) {
        // throw new Exception("Invalid value for lippu ID. Lippu does not exist");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("virhe", "Lippu_Id " + id + " ei ole olemassa."));
      }

      // korvaa uusLippuun vain RequestBodyssä annetut tiedot
      // ensin tsekataan, onko tapahtumalle annettu korvattava
      // tapahtuman_lipputyyppi_id ja jos, niin korvataan se uuLippu:un
      System.out.println("XXX - Lipputyyppi ID saatu: " + lippuTiedot.getTapahtuman_lipputyypit_id());
      if (lippuTiedot.getTapahtuman_lipputyypit_id() != null) {
        System.out.println("Lipputyyppi ID saatu: " + lippuTiedot.getTapahtuman_lipputyypit_id());
        Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtuman_lipputyyppiRepository
            .findById(lippuTiedot.getTapahtuman_lipputyypit_id()).orElse(null);
        if (tapahtuman_lipputyyppi == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("virhe", "Tapahtuman lipputyyppi ei ole kelvollinen, anna käytössä oleva id."));
        }
        uusiLippu.setTapahtuman_lipputyyppi(tapahtuman_lipputyyppi);
        System.out.println("Lipputyyppi tallennettu  " + tapahtuman_lipputyyppi);
      }

      // tarkistetaan onko annettu uusi hinta, jos on, niin korvataan se uusiLippu:un
      if (lippuTiedot.getHinta() != null) {
        System.out.println("Uusi hinta saatu: " + lippuTiedot.getHinta());
        if (lippuTiedot.getHinta() < 0) {
          // throw new Exception("Hinta ei kelpaa, ei voi olla alle 0€.");
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("virhe", "Hinta ei ole kelvollinen. Hinta ei voi olla alle 0€. "));
        } else {
          uusiLippu.setHinta(lippuTiedot.getHinta());
        }

      }

      // tarkistetaan onko annettu uusi tila, jos on, niin korvataan se uusiLippu:un
      if (lippuTiedot.getTila_id() != null) {
        Tila tila = tilaRepository.findById(lippuTiedot.getTila_id()).orElse(null);
        if (tila == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("virhe", "Tila ei ole kelvollinen, anna käytössä oleva tila_id."));
        }
        uusiLippu.setTila(tila);
      }

      // tarkistetaan onko annettu uusi myyni_id, jos on, niin korvataan se
      // uusiLippu:un
      if (lippuTiedot.getMyynti_id() != null) {
        Myynti myynti = myyntiRepository.findById(lippuTiedot.getMyynti_id()).orElse(null);
        if (myynti == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("virhe", "Myynti ei ole kelvollinen, anna käytössä oleva myynti_id."));
        }
        uusiLippu.setMyynti(myynti);
      }

      if (lippuTiedot.getKayttaja_id() != null) {
        System.out.println("Checking kayttaja_id: " + lippuTiedot.getKayttaja_id());
        Kayttaja kayttaja = kayttajaRepository.findById(lippuTiedot.getKayttaja_id())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Kayttaja ei ole kelvollinen, anna käytössä oleva kayttaja_id."));
        /*
         * if (kayttaja == null) {
         * System.out.println("Kayttaja not found! Returning BAD_REQUEST.");
         * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("virhe",
         * "Tarkastanut ei ole kelvollinen, anna käytössä oleva kayttaja_id." ));
         * }
         */
        uusiLippu.setTarkastanut(kayttaja);
      }

      if (lippuTiedot.getTarkastus_pvm() != null) {
        uusiLippu.setTarkastus_pvm(lippuTiedot.getTarkastus_pvm());
      }

      lippuRepository.save(uusiLippu);

      // Palautetaan luodun lipun tiedot
      return new ResponseEntity<>(uusiLippu, HttpStatus.CREATED);

    } /*
       * catch (Exception e) {
       * // Palautetaan virheviesti
       * return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       * }
       */
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("virhe", "Odottamaton virhe: " + e.getMessage()));
    }
  }

  // Poista lippu
  @PreAuthorize("hasAnyAuthority('Yllapitaja')")
  @DeleteMapping("/api/liput/{id}")
  public ResponseEntity<Void> deleteLippu(@PathVariable Long id) {
    try {
      if (lippuRepository.existsById(id)) {
        lippuRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Palauttaa 204 NO CONTENT eli onnistunut poisto
      } else { // Palauttaa 404 NOT_FOUND jos lippua ei löydy
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lippua ei löydy ID:llä " + id);
      }

    } catch (DataAccessException e) { // Palauttaa 400 BAD_REQUEST tietokantavirheille, jotta ei tule 500 koodia
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Virhe tapahtumaa poistettaessa", e);
    }

  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {

    return ResponseEntity.badRequest().body(Map.of("virhe",
        "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku. Päivämäärän ja ajan tulee olla muodossa yyyy-MM-dd'T'HH:mm:ss.   "
            + ex.getMessage()));

  }

}
