package projekti.demo.web;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import projekti.demo.model.Myynti;
import projekti.demo.model.Myyntipiste;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.MyyntipisteRepository;
import projekti.demo.model.Lippu;
import projekti.demo.model.Maksutapa;
import projekti.demo.model.MaksutapaRepository;
import projekti.demo.DemoApplication;
import projekti.demo.model.JarjestajaRepository;
import projekti.demo.model.Kayttaja;
import projekti.demo.model.KayttajaRepository;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MyyntiRestController {
   
        /* Nämä herjaavat käynnistettäessä.
    private final JarjestajaRepository jarjestajaRepository;

    private final CommandLineRunner demoRunner;

    private final DemoApplication demoApplication; */

    @Autowired
    private MyyntiRepository myyntiRepository;

        @Autowired
        MyyntipisteRepository myyntipisteRepository;

        @Autowired
        MaksutapaRepository maksutapaRepository;

        @Autowired
        KayttajaRepository kayttajaRepository;

        // Hae kaikki myynnit
        
    @GetMapping(value = {"/api/myynnit", "/api/myynnit/"})
    public List<Myynti> getAllMyynnit(){
        try {
            List<Myynti> myynnit = myyntiRepository.findAll();
            
            if (myynnit.isEmpty()) { // Palauttaa 404 NOT_FOUND jos lista tyhjä
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Yhtään myyntiä ei löytynyt.");
            }
            return myynnit; // Palauttaa 200 haetaan myynnit onnistuneesti
        
        } catch (DataAccessException e) { // Palauttaa 400 BAD_REQUEST tietokantavirheille, jotta ei tule 500 koodia
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Virhe myyntejä haettaessa", e);
        }
    }

        // Hae yksi myynti
        @GetMapping("/api/myynnit/{id}")
        public ResponseEntity<Myynti> getMyyntiById(@PathVariable Long id) {
                try {
                        Myynti myynti = myyntiRepository.findById(id)
                                        .orElseThrow(
                                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                        "Myyntiä ei löydy id:llä " + id));
                        return ResponseEntity.ok(myynti);
                } catch (DataAccessException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                        "Tietokantavirhe myyntiä haettaessa",
                                        e);
                }
        }

        // Yksittäisen myynnin lippujen hakeminen
        @GetMapping("/api/myynnit/{id}/liput")
        public ResponseEntity<?> getMyynninLiputById(@PathVariable Long id) {
                Optional<Myynti> optionalMyynti = myyntiRepository.findById(id);

                if (optionalMyynti.isPresent()) {
                        List<Lippu> liput = optionalMyynti.get().getLiput();
                        return ResponseEntity.ok(liput);
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("virhe", "Myyntiä ei löydy id:llä " + id));
                }
        }

        /* Yksittäisen myynnin lippujen hakeminen - palautetaan tyhjä lista
        @GetMapping("/api/myynnit/{id}/liput")
        public List<Lippu> getMyynninLiputById(@PathVariable Long id) {
                return myyntiRepository.findById(id)
                                .map(Myynti::getLiput)
                                .orElse(Collections.emptyList());
        } */

        // Luo myynti
        @PostMapping("/api/myynnit")
        public ResponseEntity<?> createMyynti(@Valid @RequestBody Myynti myynti) {
                try {
                        // haetaan tietokannassa on myyntipiste Id:llä myyntipiste ja maksutapa Id:llä
                        // maksustapa
                        boolean myyntipisteidexists = myyntipisteRepository
                                        .findById(myynti.getMyyntipiste().getMyyntipisteId())
                                        .isPresent();
                        boolean maksutapaidexists = maksutapaRepository
                                        .findById(myynti.getMaksutapa().getMaksutapa_id())
                                        .isPresent();

 /*                                        
                        // asiakkaan, eli käyttäjän tarkistus - ei vielä luotu asiakkaita, niin jätetään
                        // tämä tekemättä - TÄSSÄ SAATTAA OLLA JOTAIN VIALLA            
                        if (myynti.getAsiakas() == null) { // asiakas voi olla null                              
                        } else { 
                                // jos asiakkasnumero on annettu, se tarkistetaan ja palautetaan virheilmoitus,
                                // mikäli asiakasta ei ole olemassa
                                boolean asiakasexists = kayttajaRepository.findById(myynti.getAsiakas().getKayttaja_id()).isPresent();
                                System.out.println("asiakasexists "+asiakasexists);
                                if (!asiakasexists) {
                                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                        .body(Map.of("Not Found",
                                                                        " invalid id value for Asiakas. Id must be valid. Asiakas ID "
                                                                                        + myynti.getAsiakas()
                                                                                                        .getKayttaja_id()
                                                                                        + " not found"));
                                }                      
                        }
 */  
 

                        // tarkastetaan, että haettu myyntipiste ja maksutapa on olemassa, muuten
                        // heitetää exception
                        if (!myyntipisteidexists && !maksutapaidexists) {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(Map.of("Not Found",
                                                                " invalid id value for myyntipiste and maksutapa. Id must be valid. Myyntipiste ID "
                                                                                + myynti.getMyyntipiste()
                                                                                                .getMyyntipisteId()
                                                                                + " Maksutapa ID "
                                                                                + myynti.getMaksutapa()
                                                                                                .getMaksutapa_id()
                                                                                + " not found"));
                        } else if (!myyntipisteidexists) {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(Map.of("Not Found",
                                                                " invalid id value for myyntipiste. Please check id. Id must be a valid id number. Myyntipiste ID "
                                                                                + myynti.getMyyntipiste()
                                                                                                .getMyyntipisteId()
                                                                                + " not found"));
                        } else if (!maksutapaidexists) {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(Map.of("Not Found",
                                                                " invalid id value for maksutapa. Please check id. Id must be a valid id number. Maksutapa ID "
                                                                                + myynti.getMaksutapa()
                                                                                                .getMaksutapa_id()
                                                                                + " not found"));
                        }

                        // haetaan myyntipisteen ja maksutavan tiedot
                        Optional<Myyntipiste> myyntipisteOptional = myyntipisteRepository
                                        .findById(myynti.getMyyntipiste().getMyyntipisteId());
                        Myyntipiste myyntipiste = myyntipisteOptional.get();
                        Optional<Maksutapa> maksutapaoptional = maksutapaRepository
                                        .findById(myynti.getMaksutapa().getMaksutapa_id());
                        Maksutapa maksutapa = maksutapaoptional.get();

                        // sijoitetaan kaikki tiedot myyntiin
                        myynti.setMyyntipiste(myyntipiste);
                        myynti.setMaksutapa(maksutapa);

                        // haetaan tämän hetkinen päivämäärä ja asetetaan se myyntipäiväksi. Myyntipäivä
                        // on aina
                        LocalDate date = LocalDate.now();
                        myynti.setMyyntipaiva(date);
                        Myynti uusiMyynti = myyntiRepository.save(myynti);

                        URI location = ServletUriComponentsBuilder
                                        .fromCurrentRequest()
                                        .path("/{id}")
                                        .buildAndExpand(uusiMyynti.getMyynti_id())
                                        .toUri();

                        return ResponseEntity.created(location).body(uusiMyynti);
                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {

                return ResponseEntity.badRequest().body(Map.of("virhe",
                                "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku."
                                                + ex.getMessage()));

        }

        /*
         * // nämä oli varmaan jotain vanhoja
         * 
         * @GetMapping("/api/myynnit/{id}/liput")
         * public Optional<Object> getMyynninLiputById(@PathVariable Long id){
         * return myyntiRepository.findById(id)
         * .map(myynti -> {
         * System.out.println("lippujen määrä" + myynti.getLiput().size());
         * return ResponseEntity.ok(myynti.getLiput());
         * });
         * }
         * 
         * /* testataan, kun on lippujen controller?
         * 
         * @GetMapping("/api/myynnit/{id}/liput")
         * public List<Lippu> getMyynninLiputById(@PathVariable Long id){
         * return myyntiRepository.findById(id)
         * .map(Myynti::getLiput)
         * .orElse(Collections.emptyList());
         * }
         * 
         */

}