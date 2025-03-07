package projekti.demo.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import jakarta.validation.Valid;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;


@RestController
public class MyyntiRestController {

    @Autowired
   private MyyntiRepository myyntiRepository;


@PostMapping("/api/myynnit")
public ResponseEntity<?> createMyynti(@Valid @RequestBody Myynti myynti) {

    try {
        Myynti savedMyynti = myyntiRepository.save(myynti);

        URI location =  ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(savedMyynti.getMyynti_id())
        .toUri();

        return ResponseEntity.created(location).body(savedMyynti);
    } catch (Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Myynnin luonti epäonnistui.");

    }

}

}
