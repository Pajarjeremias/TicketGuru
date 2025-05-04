package projekti.demo.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import projekti.demo.model.LippuRepository;
import projekti.demo.model.Lipputyyppi;
import projekti.demo.model.LipputyyppiRepository;

@RestController

public class LippuTyyppiRestController {

    private final LippuRepository lippuRepository;
    @Autowired
    private LipputyyppiRepository lipputyyppiRepository;

    LippuTyyppiRestController(LippuRepository lippuRepository) {
        this.lippuRepository = lippuRepository;
    }

    //Hae kaikki lipputyypit
    @GetMapping("/api/lipputyypit")
    public Iterable<Lipputyyppi> getAllLipputyypit() {
        return lipputyyppiRepository.findAll();
    }

    //Hae yksittäinen lipputyyppi
    @GetMapping("/api/lipputyypit/{id}")
    public Optional<Lipputyyppi> getLipputyyppiById(@PathVariable Long id) {
        return lipputyyppiRepository.findById(id);
    }

    //Luo uusi lipputyyppi
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @PostMapping("/api/lipputyypit")
    public Lipputyyppi createLipputyyppi(@RequestBody Lipputyyppi lipputyyppi) {
        return lipputyyppiRepository.save(lipputyyppi);
    }

    //Päivitä tietty lipputyyppi
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @PutMapping("/api/lipputyypit/{id}")
    public Lipputyyppi updateLipputyyppi(@PathVariable Long id, @RequestBody Lipputyyppi lipputyyppi) {
        lipputyyppi.setLipputyyppi_id(id);
        return lipputyyppiRepository.save(lipputyyppi);
    }

   //Poista tietty lipputyyppi
   @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
   @DeleteMapping("/api/lipputyypit/{id}")
   public ResponseEntity<Void> deleteLipputyyppi(@PathVariable Long id) {
       try{
           if(lipputyyppiRepository.existsById(id)) {
               lipputyyppiRepository.deleteById(id);
               return ResponseEntity.noContent().build();
           } else {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lipputyyppiä ei löydy ID:llä" + id);
           }
       } catch (DataAccessException e) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Virhe lipputyyppiä poistaessa", e);
       }
   }

   // Käsittelee not found -virheet
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Map<String, List<String>>> handleIllegalArgumentErrors(IllegalArgumentException e) {
       Map<String, List<String>> errorResponse = new HashMap<>();
       List<String> errors = new ArrayList<String>();
       errors.add(e.getMessage());
       errorResponse.put("errors", errors);
       return new ResponseEntity<>(errorResponse ,new HttpHeaders(), HttpStatus.NOT_FOUND);
   }
   // Käsittelee validaatioon liittyvät virheet
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
       List<String> errors = e.getBindingResult().getFieldErrors()
               .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
       return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
   }

   // Käsittelee JSON bodyn lukemiseen liittyvät virheet
   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<Map<String, List<String>>> handleHttpNotReadableErrors(HttpMessageNotReadableException e) {
       Map<String, List<String>> errorResponse = new HashMap<>();
       List<String> errors = new ArrayList<String>();
       errors.add(e.getMessage());
       errorResponse.put("errors", errors);
       return new ResponseEntity<>(errorResponse ,new HttpHeaders(), HttpStatus.BAD_REQUEST);
   }

   private Map<String, List<String>> getErrorsMap(List<String> errors) {
       Map<String, List<String>> errorResponse = new HashMap<>();
       errorResponse.put("errors", errors);
       return errorResponse;
   }
}
