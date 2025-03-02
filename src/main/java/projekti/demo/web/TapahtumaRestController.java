package projekti.demo.web;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;

@RestController
public class TapahtumaRestController {

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    // poista tapahtuma
    @DeleteMapping("/api/tapahtumat/{id}")
    public Iterable<Tapahtuma> deleteTapahtuma(@PathVariable Long id) {
        tapahtumaRepository.deleteById(id);
        return tapahtumaRepository.findAll();
    }

    //muokkaa tapahtumaa

    @PutMapping("/api/tapahtumat/{id}")
    Tapahtuma paivitaTapahtuma(@RequestBody Tapahtuma muokattuTapahtuma, @PathVariable Long id){
        muokattuTapahtuma.setTapahtuma_id(id);
        return tapahtumaRepository.save(muokattuTapahtuma);
    }

    //näin jossain käytettävän public ResponseEntity<Tapahtuma> - onko tietoa olisiko tämä parempi?

}
