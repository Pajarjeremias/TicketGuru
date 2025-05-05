package projekti.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import projekti.demo.model.Postitoimipaikka;
import projekti.demo.model.PostitoimipaikkaRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/postitoimipaikat")

public class PostitoimipaikkaRestController {

    @Autowired
    private PostitoimipaikkaRepository repository;

    @GetMapping
    public Iterable<Postitoimipaikka> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{postinumero}")
    public ResponseEntity<Postitoimipaikka> getById(@PathVariable String postinumero) {
        Optional<Postitoimipaikka> result = repository.findById(postinumero);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Postitoimipaikka> create(@Valid @RequestBody Postitoimipaikka newPostitoimipaikka) {
        if (repository.existsById(newPostitoimipaikka.getPostinumero())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.save(newPostitoimipaikka));
    }

    @PutMapping("/{postinumero}")
    public ResponseEntity<Postitoimipaikka> update(@PathVariable String postinumero,
                                                   @Valid @RequestBody Postitoimipaikka updated) {
        if (!repository.existsById(postinumero)) {
            return ResponseEntity.notFound().build();
        }
        updated.setPostinumero(postinumero);
        return ResponseEntity.ok(repository.save(updated));
    }

    @DeleteMapping("/{postinumero}")
    public ResponseEntity<Void> delete(@PathVariable String postinumero) {
        if (!repository.existsById(postinumero)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(postinumero);
        return ResponseEntity.noContent().build();
    }
}
