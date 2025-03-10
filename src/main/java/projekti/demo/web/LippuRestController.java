package projekti.demo.web;

import org.springframework.web.bind.annotation.RestController;

import projekti.demo.model.Lippu;
import projekti.demo.model.LippuRepository;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.PostLippuModel;
import projekti.demo.model.Tapahtuman_lipputyyppi;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;
import projekti.demo.model.Tila;
import projekti.demo.model.TilaRepository;
import projekti.demo.model.PutLippuModel;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class LippuRestController {

  LippuRepository lippuRepository;
  MyyntiRepository myyntiRepository;
  Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository;
  TilaRepository tilaRepository;

  public LippuRestController(
      LippuRepository lippuRepository,
      MyyntiRepository myyntiRepository,
      Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository,
      TilaRepository tilaRepository) {
    this.lippuRepository = lippuRepository;
    this.myyntiRepository = myyntiRepository;
    this.tapahtuman_lipputyyppiRepository = tapahtuman_lipputyyppiRepository;
    this.tilaRepository = tilaRepository;
  }

  // Luo uusi lippu
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
        throw new Exception("Invalid value for 'myynti' and 'tapahtuman_lipputyyppi', please check.");
      } else if (myynti == null) {
        throw new Exception("Invalid value for 'myynti', please check.");
      } else if (tapahtuman_lipputyyppi == null) {
        throw new Exception("Invalid value for 'tapahtuman_lipputyyppi', please check.");
      }

      // Jos hintaa ei ole annettu pyynnössä, haetaan lipun hinnaksi lipputyypin hinta
      Float hinta;
      if (lippuTiedot.getHinta() == null) {
        hinta = tapahtuman_lipputyyppi.getHinta();
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

  // Päivitä lippu
  @PutMapping("/api/liput/{id}")
  Lippu paivitaLippu(@PathVariable Long id, @RequestBody Lippu muokattuLippu) {
    // tallennetaan muokatut tiedot lipulle - tarvitaan kaikki lipun tiedot
    muokattuLippu.setLippu_id(id);
    return lippuRepository.save(muokattuLippu);
  }

  @GetMapping("/api/getlippu/{id}")
  public Optional<Lippu> getLippuById(@PathVariable Long id) {
    return lippuRepository.findById(id);
  }

  // päivitä lippua enemmillä tiedoilla
  @PutMapping("/api/liputtiedoilla/{id}") 
  public ResponseEntity<Lippu> paivitaLipuntietoja(@PathVariable Long id, @RequestBody PutLippuModel lippuTiedot) {
    try {

      // lataa lipun id:llä olevat tiedot ja tallenna ne uusiLippu:n
      Lippu uusiLippu = lippuRepository.findById(id).orElse(null);
      if (uusiLippu==null){
        throw new Exception("Invalid value for lippu ID. Lippu does not exist");
      } 

  // korvaa uusLippuun vain RequestBodyssä annetut tiedot
// ensin tsekataan, onko tapahtumalle annettu korvattava tapahtuman_lipputyyppi_id ja jos, niin korvataan se uuLippu:un
System.out.println("XXX - Lipputyyppi ID saatu: " + lippuTiedot.getTapahtuman_lipputyypit_id());      
if (lippuTiedot.getTapahtuman_lipputyypit_id()!=null){
        System.out.println("Lipputyyppi ID saatu: " + lippuTiedot.getTapahtuman_lipputyypit_id());
        Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtuman_lipputyyppiRepository
        .findById(lippuTiedot.getTapahtuman_lipputyypit_id()).orElse(null);
        uusiLippu.setTapahtuman_lipputyyppi(tapahtuman_lipputyyppi);
        System.out.println("Lipputyyppi tallennettu  "+tapahtuman_lipputyyppi);
      }

      // tarkistetaan onko annettu uusi hinta, jos on, niin korvataan se uusiLippu:un
      if (lippuTiedot.getHinta()!=null){
        System.out.println("Uusi hinta saatu: " + lippuTiedot.getHinta());
         uusiLippu.setHinta(lippuTiedot.getHinta());
      }

      // tarkistetaan onko annettu uusi tila, jos on, niin korvataan se uusiLippu:un
      if (lippuTiedot.getTila_id()!=null){
        Tila tila = tilaRepository.findById(lippuTiedot.getTila_id()).orElse(null);
        uusiLippu.setTila(tila);

      }

      // tarkistetaan onko annettu uusi myyni_id, jos on, niin korvataan se uusiLippu:un
    if (lippuTiedot.getMyynti_id()!=null){
      Myynti myynti = myyntiRepository.findById(lippuTiedot.getMyynti_id()).orElse(null);
      uusiLippu.setMyynti(myynti);
    }
      lippuRepository.save(uusiLippu);

      // Palautetaan luodun lipun tiedot
      return new ResponseEntity<>(uusiLippu, HttpStatus.CREATED);

    } catch (Exception e) {
      // Palautetaan virheviesti
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }
}


