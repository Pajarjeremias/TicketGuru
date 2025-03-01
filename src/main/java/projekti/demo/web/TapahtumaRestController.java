package projekti.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
