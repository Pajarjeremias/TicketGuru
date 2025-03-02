package projekti.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import projekti.demo.model.TapahtumaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;
import projekti.demo.model.Tapahtuma;



@RestController
public class TapahtumaController {

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @GetMapping("/tapahtumat")
    public List<Tapahtuma> getAllTapahtumat() {
        return tapahtumaRepository.findAll();
    }
    
    @GetMapping("/tapahtumat/{id}")
    public Optional<Tapahtuma> getTapahtumaById(@PathVariable Long id) {
        return tapahtumaRepository.findById(id);
    }
    
    
    

}

