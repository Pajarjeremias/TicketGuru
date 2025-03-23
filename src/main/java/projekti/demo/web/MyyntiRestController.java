package projekti.demo.web;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import projekti.demo.DemoApplication;
import projekti.demo.model.JarjestajaRepository;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.Lippu;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MyyntiRestController {

    private final JarjestajaRepository jarjestajaRepository;

    private final CommandLineRunner demoRunner;

    private final DemoApplication demoApplication;

    @Autowired
    private MyyntiRepository myyntiRepository;

    MyyntiRestController(DemoApplication demoApplication, CommandLineRunner demoRunner, JarjestajaRepository jarjestajaRepository) {
        this.demoApplication = demoApplication;
        this.demoRunner = demoRunner;
        this.jarjestajaRepository = jarjestajaRepository;
    }

    //Hae kaikki myynnit
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
    public ResponseEntity<Myynti> createMyynti(@RequestBody Myynti myynti) {
        try {
            Myynti uusiMyynti = myyntiRepository.save(myynti);
            return new ResponseEntity<>(uusiMyynti, HttpStatus.CREATED);
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