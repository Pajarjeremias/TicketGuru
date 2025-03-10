package projekti.demo.web;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.Lippu;
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
    public Optional<Myynti> getMyyntiById(@PathVariable Long id){
        return myyntiRepository.findById(id);
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

    //Yksittäisen myynnin lippujen hakeminen

    @GetMapping("/api/myynnit/{id}/liput")
    public Optional<Object> getMyynninLiputById(@PathVariable Long id){
        return myyntiRepository.findById(id)
                    .map(myynti -> {
                        System.out.println("lippujen määrä" + myynti.getLiput().size());
                        return ResponseEntity.ok(myynti.getLiput());
                    });
    }

     
    
    }

}