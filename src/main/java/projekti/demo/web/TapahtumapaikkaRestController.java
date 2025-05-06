package projekti.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import jakarta.validation.Valid;
import projekti.demo.model.Tapahtumapaikka;
import projekti.demo.model.TapahtumapaikkaDto;
import projekti.demo.model.TapahtumapaikkaRepository;
import projekti.demo.model.PostitoimipaikkaRepository;
import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;
import projekti.demo.model.Postitoimipaikka;

import java.util.Optional;

@RestController
@RequestMapping()
public class TapahtumapaikkaRestController {

    @Autowired
    private TapahtumapaikkaRepository repository;

    @Autowired
    private PostitoimipaikkaRepository prepository;

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @GetMapping(value = { "/api/tapahtumapaikat", "/api/tapahtumapaikat/" })
    public Iterable<Tapahtumapaikka> getAll() {
        return repository.findAll();
    }

    // hae yksittäinen tapahtumapaikka
    @GetMapping("/api/tapahtumapaikat/{id}")
    public ResponseEntity<Tapahtumapaikka> getById(@PathVariable Long id) {
        Optional<Tapahtumapaikka> result = repository.findById(id);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // tallenna uusi tapahtumapaikka
    @PostMapping(value = { "/api/tapahtumapaikat", "/api/tapahtumapaikat/" })
    public ResponseEntity<Tapahtumapaikka> create(@Valid @RequestBody TapahtumapaikkaDto uusiTapahtumapaikka) {
        System.out.println(uusiTapahtumapaikka);
        if (uusiTapahtumapaikka.postinumero != null) {
            Optional<Postitoimipaikka> postipaikka = prepository
                    .findById(uusiTapahtumapaikka.postinumero);
            System.out.println("ANNETTU POSTINUMERO: " + uusiTapahtumapaikka.postinumero);
            System.out.println("ANNETTU postitoimipaikka: " + uusiTapahtumapaikka.postitoimipaikka);
            System.out.println("ANNETTU maa: " + uusiTapahtumapaikka.maa);

            Postitoimipaikka postitoimipaikka;

            if (postipaikka.isPresent()) {
                postitoimipaikka = postipaikka.get();

            } else {
                postitoimipaikka = new Postitoimipaikka(
                        uusiTapahtumapaikka.postinumero,
                        uusiTapahtumapaikka.postitoimipaikka,
                        uusiTapahtumapaikka.maa);
                prepository.save(postitoimipaikka);
            }

            Tapahtumapaikka saveTapahtumapaikka = new Tapahtumapaikka(
                    uusiTapahtumapaikka.getNimi(),
                    uusiTapahtumapaikka.getKatuosoite(),
                    postitoimipaikka,
                    uusiTapahtumapaikka.getMaksimi_osallistujat());
            return ResponseEntity.ok(repository.save(saveTapahtumapaikka));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Postinumero puuttuu, tapahtuman luominen epäonnistui");
        }
        /*
         * if (uusiTapahtumapaikka.getPostinumero() != null
         * && uusiTapahtumapaikka.getPostinumero().getPostinumero() != null) {
         * Optional<Postitoimipaikka> postipaikka = prepository
         * .findById(uusiTapahtumapaikka.getPostinumero().getPostinumero());
         * System.out.println("ANNETTU POSTINUMERO: " +
         * uusiTapahtumapaikka.getPostinumero().getPostinumero());
         * System.out.println("ANNETTU POSTINUMERO: " +
         * uusiTapahtumapaikka.getPostinumero().getPostitoimipaikka());
         * System.out.println("ANNETTU POSTINUMERO: " +
         * uusiTapahtumapaikka.getPostinumero().getMaa());
         * 
         * if (postipaikka.isPresent()) {
         * 
         * } else {
         * Postitoimipaikka postitoimipaikka = uusiTapahtumapaikka.getPostinumero();
         * prepository.save(postitoimipaikka);
         * 
         * }
         */

    }

    // muokkaa tapahtumapaikkaa
    @PutMapping("/api/tapahtumapaikat/{id}")
    public ResponseEntity<Tapahtumapaikka> update(@PathVariable Long id,
            @Valid @RequestBody Tapahtumapaikka updateTapahtumapaikka) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updateTapahtumapaikka.setTapahtumapaikka_id(id);
        return ResponseEntity.ok(repository.save(updateTapahtumapaikka));
    }

    @DeleteMapping("/api/tapahtumapaikat/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
