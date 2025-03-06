package projekti.demo.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MyyntiRestController {

    @Autowired
    private MyyntiRepository myyntiRepository;

    //Hae kaikki myynnit
    @GetMapping(value = {"/api/myynnit", "/api/myynnit/"})
    public List<Myynti> getAllMyynnit(){
        return myyntiRepository.findAll();
    }
    
    
}
