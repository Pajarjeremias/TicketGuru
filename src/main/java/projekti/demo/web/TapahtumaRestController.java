package projekti.demo.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;

import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TapahtumaRestController {

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    // Hae kaikki tapahtumat
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

    @GetMapping("/api/tapahtumat/{id}")
    public ResponseEntity<Tapahtuma> getTapahtumaById(@PathVariable Long id) {
        try {
            Tapahtuma tapahtuma = tapahtumaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tapahtumaa ei löydy id:llä" + id));
            return ResponseEntity.ok(tapahtuma);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe tapahtumaa haettaessa", e);
        }
    }
   

    // poista tapahtuma
    @DeleteMapping("/api/tapahtumat/{id}")
    public ResponseEntity<Void> deleteTapahtuma(@PathVariable Long id) {
        if (tapahtumaRepository.existsById(id)) {
            tapahtumaRepository.deleteById(id);
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //muokkaa tapahtumaa
    @PutMapping("/api/tapahtumat/{id}")
    Tapahtuma paivitaTapahtuma(@RequestBody Tapahtuma muokattuTapahtuma, @PathVariable Long id){
        muokattuTapahtuma.setTapahtuma_id(id);
        return tapahtumaRepository.save(muokattuTapahtuma);
    }
    // Luo tapahtuma
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
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tapahtuman luonti epäonnistui.");
        }
    }

}
    //näin jossain käytettävän public ResponseEntity<Tapahtuma> - onko tietoa olisiko tämä parempi?
    //ResponseEntity ainakin mahdollistaa paluuarvojen antamisen varmaankin paremmin


