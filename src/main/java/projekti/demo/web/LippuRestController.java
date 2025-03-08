package projekti.demo.web;

import org.springframework.web.bind.annotation.RestController;

import projekti.demo.model.Lippu;
import projekti.demo.model.LippuRepository;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.PostLippuModel;
import projekti.demo.model.Tapahtuman_lipputyyppi;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;
import projekti.demo.model.TilaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    TilaRepository tilaRepository
    ) {
    this.lippuRepository = lippuRepository;
    this.myyntiRepository = myyntiRepository;
    this.tapahtuman_lipputyyppiRepository = tapahtuman_lipputyyppiRepository;
    this.tilaRepository = tilaRepository;
  }

  // Luo uusi lippu
  @PostMapping(value = {"/api/liput", "/api/liput/"})
  public ResponseEntity<?> createLippu(@RequestBody PostLippuModel lippuTiedot) {

    try {

      // Haetaan tietokannasta pyynnössä annetut myynti + lipputyyppi
      Myynti myynti = myyntiRepository.findById(lippuTiedot.getMyynti_id()).orElse(null);
      Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtuman_lipputyyppiRepository.findById(lippuTiedot.getTapahtuman_lipputyypit_id()).orElse(null);

      // Tarkistetaan, että pyynnössä tulleet myynti + lipputyyppi ovat olemassa. Jos ei, heitetään Exception.
      if (myynti == null && tapahtuman_lipputyyppi == null) {
        throw new Exception("Invalid value for 'myynti' and 'tapahtuman_lipputyyppi', please check.");
      } else if (myynti == null) {
        throw new Exception("Invalid value for 'myynti', please check.");
      } else if (tapahtuman_lipputyyppi == null) {
        throw new Exception("Invalid value for 'tapahtuman_lipputyyppi', please check.");
      }

      //Jos hintaa ei ole annettu pyynnössä, haetaan lipun hinnaksi lipputyypin hinta
      Float hinta;
      if (lippuTiedot.getHinta() == null) {
        hinta = tapahtuman_lipputyyppi.getHinta();
      } else {
        hinta = lippuTiedot.getHinta();
      }

      // Luodaan saaduilla tiedoilla uusi lippu ja tallennetaan se. Oletustilana "Myyty".
      Lippu uusiLippu = new Lippu(
        tapahtuman_lipputyyppi,
        hinta,
        tilaRepository.findByTila("Myyty"),
        myynti
      );
      lippuRepository.save(uusiLippu);

      // Palautetaan luodun lipun tiedot
      return new ResponseEntity<Lippu>(uusiLippu, HttpStatus.CREATED);

    } catch (Exception e) {
      
      // Palautetaab virheviesti
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

  }
  
}
