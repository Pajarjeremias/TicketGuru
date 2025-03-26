package projekti.demo.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import projekti.demo.model.Jarjestaja;
import projekti.demo.model.JarjestajaRepository;
import projekti.demo.model.PutTapahtumaModel;
import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;
import projekti.demo.model.Tapahtumapaikka;
import projekti.demo.model.TapahtumapaikkaRepository;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@EnableMethodSecurity(securedEnabled = true)
public class TapahtumaRestController {

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @Autowired
    private TapahtumapaikkaRepository tapahtumapaikkaRepository;

    @Autowired
    private JarjestajaRepository jarjestajaRepository;


    // Hae kaikki tapahtumat
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @GetMapping(value = {"/api/tapahtumat", "/api/tapahtumat/"})
    public ResponseEntity<List<Tapahtuma>> getAllTapahtumat() {
        try {
            List<Tapahtuma> tapahtumat =tapahtumaRepository.findAll();
            if (tapahtumat.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(tapahtumat);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe tapahtumia haettaessa", e);
        }
    }


    // Hae yksittäinen tapahtuma
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @GetMapping("/api/tapahtumat/{id}")
    public ResponseEntity<?> getTapahtumaById(@PathVariable Long id) {
        try {
            // tarkistetaan onko annetulla id:llä tapahtaumaa
        if (!tapahtumaRepository.existsById(id)){
            // jos ei ole palautetaan 404 not found ja teksti
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("Not Found", " invalid ID value for Tapahtuma. Id must be valid. ID "
                                        + id + " not found"));
        } else {
            // tapahtuma on olemassa, haetaan ja palautetaan se
            Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(id);
            return ResponseEntity.ok(tapahtuma);
        }
        } catch (DataAccessException e) {
            //tähän ei ikinä pitäisi joutua, mut jätettiin tää nyt tänne
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe tapahtumaa haettaessa", e);
        }
    }


    // Poista tapahtuma
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava')")
    @DeleteMapping("/api/tapahtumat/{id}")
    public ResponseEntity<Void> deleteTapahtuma(@PathVariable Long id) {
        try { 
        if (tapahtumaRepository.existsById(id)) {
            tapahtumaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Palauttaa 204 NO CONTENT eli onnistunut poisto
        } 
        else { // Palauttaa 404 NOT_FOUND jos tapahtumaa ei löydy 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumaa ei löydy ID:llä " + id);
        }

    } catch (DataAccessException e) { // Palauttaa 400 BAD_REQUEST tietokantavirheille, jotta ei tule 500 koodia
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Virhe tapahtumaa poistettaessa", e);
    }

    }


    // Muokkaa tapahtumaa
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava')")
    @PutMapping("/api/tapahtumat/{id}")
    public ResponseEntity<?> paivitaTapahtuma(@RequestBody PutTapahtumaModel paivitettavatTiedot, @PathVariable Long id){

        // Tarkistetaan löytyykö annetulla id:llä tapahtuma, jos ei, heitetään IllegalArgumentException
        Tapahtuma paivitettavaTapahtuma = tapahtumaRepository.findById(id).orElse(null);
        if (paivitettavaTapahtuma == null) {
            throw new IllegalArgumentException("Annetulla id:lla ei löydy tapahtumaa.");
        }

        // Jos pyynnössä on nimi, päivitetään se haettuun tapahtumaan
        if (paivitettavatTiedot.getNimi() != null) {
            paivitettavaTapahtuma.setNimi(paivitettavatTiedot.getNimi());
        }

        // Jos pyynnössä on paivamaara, päivitetään se haettuun tapahtumaan
        if (paivitettavatTiedot.getPaivamaara() != null) {
            paivitettavaTapahtuma.setPaivamaara(paivitettavatTiedot.getPaivamaara());
        }

        // Jos pyynnössä on kuvaus, päivitetään se haettuun tapahtumaan
        if (paivitettavatTiedot.getKuvaus() != null) {
            paivitettavaTapahtuma.setKuvaus(paivitettavatTiedot.getKuvaus());
        }

        // Jos pyynnössä on lippumäärä, päivitetään se haettuun tapahtumaan
        if (paivitettavatTiedot.getLippumaara() > 0) {
            paivitettavaTapahtuma.setLippumaara(paivitettavatTiedot.getLippumaara());
        }
        
        // Jos pyynnössä on tapahtumapaikan id, päivitetään se haettuun tapahtumaan
        if (paivitettavatTiedot.getTapahtumapaikka_id() != null) {
            Tapahtumapaikka uusiPaikka = tapahtumapaikkaRepository.findById(paivitettavatTiedot.getTapahtumapaikka_id()).orElse(null);

            // Jos tapahtumapaikkaa ei löydy annetulla id:lla, heitetään IllegalArgumentException
            if (uusiPaikka == null) {
                throw new IllegalArgumentException("Annetulla tapahtumapaikka_id:lla ei löydy tapahtumapaikkaa.");
            }

            paivitettavaTapahtuma.setTapahtumapaikka(uusiPaikka);;
        }

        // Jos pyynnössä on järjestäjän id, päivitetään se haettuun tapahtumaan
        if (paivitettavatTiedot.getJarjestaja_id() != null) {
            Jarjestaja uusiJarjestaja = jarjestajaRepository.findById(paivitettavatTiedot.getJarjestaja_id()).orElse(null);

            // Jos tapahtumapaikkaa ei löydy annetulla id:lla, heitetään IllegalArgumentException
            if (uusiJarjestaja == null) {
                throw new IllegalArgumentException("Annetulla jarjestaja_id:lla ei löydy järjestäjää.");
            }

            paivitettavaTapahtuma.setJarjestaja(uusiJarjestaja);
        }
        
        // Tallennetaan päivitetty tapahtuma kantaan ja palautetaan päivitetty tapahtuma
        tapahtumaRepository.save(paivitettavaTapahtuma);
        return new ResponseEntity<>(paivitettavaTapahtuma ,new HttpHeaders(), HttpStatus.OK);
    }


    // Luo tapahtuma
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava')")
    @PostMapping("/api/tapahtumat")
    public ResponseEntity<?> createTapahtuma(@Valid @RequestBody Tapahtuma tapahtuma) {
        try {
            Tapahtuma savedTapahtuma = tapahtumaRepository.save(tapahtuma);
            
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTapahtuma.getTapahtuma_id())
                .toUri();
        
            return ResponseEntity.created(location).body(savedTapahtuma);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tapahtuman luonti epäonnistui.");

        }
    }

    
    // Käsittelee not found -virheet
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, List<String>>> handleIllegalArgumentErrors(IllegalArgumentException e) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        List<String> errors = new ArrayList<String>();
        errors.add(e.getMessage());
        errorResponse.put("errors", errors);
        return new ResponseEntity<>(errorResponse ,new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // Käsittelee validaatioon liittyvät virheet
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // Käsittelee JSON bodyn lukemiseen liittyvät virheet
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, List<String>>> handleHttpNotReadableErrors(HttpMessageNotReadableException e) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        List<String> errors = new ArrayList<String>();
        errors.add(e.getMessage());
        errorResponse.put("errors", errors);
        return new ResponseEntity<>(errorResponse ,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}

