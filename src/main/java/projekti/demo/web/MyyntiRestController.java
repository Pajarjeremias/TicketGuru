package projekti.demo.web;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.Lippu;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MyyntiRestController {

    @Autowired
    private MyyntiRepository myyntiRepository;

    //Hae kaikki myynnit
    @GetMapping(value = {"/api/myynnit", "/api/myynnit/"})
    public List<Myynti> getAllMyynnit(){
        return myyntiRepository.findAll();
    }

    //Hae yksi myynti
    @GetMapping("/api/myynnit/{id}")
    public ResponseEntity<Myynti> getMyyntiById(@PathVariable Long id){
        try {
            Myynti myynti = myyntiRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Myyntiä ei löydy id:llä " + id));
            return ResponseEntity.ok(myynti);
        } catch (DataAccessException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tietokantavirhe myyntiä haettaessa", e);
        }
    }

    //Yksittäisen myynnin lippujen hakeminen
    @GetMapping("/api/myynnit/{id}/liput")
    public List<Lippu> getMyynninLiputById(@PathVariable Long id){
        return myyntiRepository.findById(id)
                    .map(Myynti::getLiput)
                    .orElse(Collections.emptyList());
    }


    //Luo myynti
    @PostMapping("/api/myynnit")
    public ResponseEntity<?> createMyynti(@Valid @RequestBody Myynti myynti) {
        try {
            LocalDate date = LocalDate.now();
        
            if (myynti.getMyyntipaiva().isBefore(date)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Myyntipäivä ei voi olla menneisyydessä");
            }

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

/*  
   // nämä oli varmaan jotain vanhoja
    @GetMapping("/api/myynnit/{id}/liput")
    public Optional<Object> getMyynninLiputById(@PathVariable Long id){
        return myyntiRepository.findById(id)
                    .map(myynti -> {
                        System.out.println("lippujen määrä" + myynti.getLiput().size());
                        return ResponseEntity.ok(myynti.getLiput());
                    });
    }

        /* testataan, kun on lippujen controller? 
    @GetMapping("/api/myynnit/{id}/liput")
    public List<Lippu> getMyynninLiputById(@PathVariable Long id){
        return myyntiRepository.findById(id)
                    .map(Myynti::getLiput)
                    .orElse(Collections.emptyList());
    }
     
    */
    }

}