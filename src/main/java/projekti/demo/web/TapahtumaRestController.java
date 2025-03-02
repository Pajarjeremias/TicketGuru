package projekti.demo.web;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;

@RestController
@RequestMapping("/api/tapahtumat")
public class TapahtumaRestController {

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

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

    //näin jossain käytettävän public ResponseEntity<Tapahtuma> - onko tietoa olisiko tämä parempi?
    //ResponseEntity ainakin mahdollistaa paluuarvojen antamisen varmaankin paremmin

}
