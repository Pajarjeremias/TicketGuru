package projekti.demo.web;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import projekti.demo.model.*;

@RestController
public class TapahtumanLippuTyypitRestController {
    @Autowired
    private Tapahtuman_lipputyyppiRepository tapahtumanLipputyyppiRepository;

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @Autowired
    private LipputyyppiRepository lipputyyppiRepository;

    //Hae kaikki tapahtuman lipputyypit
    @GetMapping("/api/tapahtumanlipputyypit")
    public Iterable<Tapahtuman_lipputyyppi> getAllTapahtumanLipputyypit() {
        return tapahtumanLipputyyppiRepository.findAll();
    }

    //Hae yksittäinen tapahtuman lipputyyppi
    @GetMapping("/api/tapahtumanlipputyypit/{id}")
    public Optional<Tapahtuman_lipputyyppi> getTapahtumanLipputyyppiById(@PathVariable Long id) {
        return tapahtumanLipputyyppiRepository.findById(id);
    }

    //Luo uusi tapahtuman lipputyyppi
   /*  @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @PostMapping("/api/tapahtumanlipputyypit")
    public Tapahtuman_lipputyyppi createTapahtumanLipputyyppi(@RequestParam Long tapahtumaId, @RequestParam Long lipputyyppiId, @RequestParam Float hinta) {

        //Haetaan tapahtuma ja lipputyyppi idn perusteella
        Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(tapahtumaId);
        Optional<Lipputyyppi> lipputyyppi = lipputyyppiRepository.findById(lipputyyppiId);

        //Tarkistetaan että löytyykö haetut tiedot
        if (tapahtuma.isPresent() && lipputyyppi.isPresent()) {
            //Luodaan uusi tapahtuman lipputyyppi
            Tapahtuman_lipputyyppi uusi = new Tapahtuman_lipputyyppi(
                tapahtuma.get(), lipputyyppi.get(), hinta);
                // Tallennetaan
            return tapahtumanLipputyyppiRepository.save(uusi);
        } else {
            throw new RuntimeException("Tapahtumaa tai lipputyyppiä ei löytynyt");
        }
    }*/

    //Muuta yksittäistä tapahtuman lipputyyppiä
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @PutMapping("/api/tapahtumanlipputyypit/{id}")
    public Tapahtuman_lipputyyppi paivitaTapahtumanLipputyyppi(@PathVariable Long id, @RequestBody Tapahtuman_lipputyyppi lipputyyppi) {
    // Haetaan olemassa oleva lipputyyppi idn perusteella
    Optional<Tapahtuman_lipputyyppi> optional = tapahtumanLipputyyppiRepository.findById(id);
    if (optional.isPresent()) {
        // Jos löytyi, otetaan se, päivitetään ja tallennetaan
        Tapahtuman_lipputyyppi onOlemassa = optional.get();
        onOlemassa.setHinta(lipputyyppi.getHinta()); // käytetään pyynnön bodystä saatua hintaa
        return tapahtumanLipputyyppiRepository.save(onOlemassa);
    } else {
        throw new RuntimeException("Tapahtuman lipputyyppiä ei löytynyt");
    }
}

    //Poista yksittäinen tapahtuman lipputyyppi
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @DeleteMapping("/api/tapahtumanlipputyypit/{id}")
    public void deleteTapahtumanLipputyyppi(@PathVariable Long id) {
        tapahtumanLipputyyppiRepository.deleteById(id);
    }

    // Hae yksittäisen tapahtuman kaikki lipputyypit
@GetMapping("/tapahtuma/{tapahtumaId}")
public List<Tapahtuman_lipputyyppi> getByTapahtuma(@PathVariable Long tapahtumaId) {
    // Haetaan tapahtuma idn mukaan
    Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(tapahtumaId);
    // Jos tapahtuma löytyi, haetaan siihen liittyvät lipputyypit
    if (tapahtuma.isPresent()) {
        return tapahtumanLipputyyppiRepository.findByTapahtuma(tapahtuma.get());
    } else {
        throw new RuntimeException("Tapahtumaa ei löydy");
    }
}
@PostMapping("/api/tapahtumanlipputyypit")
public Tapahtuman_lipputyyppi createTapahtumanLipputyyppi(@RequestBody Map<String, Object> request) {
    Long tapahtumaId = ((Number) request.get("tapahtuma_id")).longValue();
    Long lipputyyppiId = ((Number) request.get("lipputyyppi_id")).longValue();
    Float hinta = ((Number) request.get("hinta")).floatValue();

    Optional<Tapahtuma> tapahtuma = tapahtumaRepository.findById(tapahtumaId);
    Optional<Lipputyyppi> lipputyyppi = lipputyyppiRepository.findById(lipputyyppiId);

    if (tapahtuma.isPresent() && lipputyyppi.isPresent()) {
        Tapahtuman_lipputyyppi uusi = new Tapahtuman_lipputyyppi(tapahtuma.get(), lipputyyppi.get(), hinta);
        return tapahtumanLipputyyppiRepository.save(uusi);
    } else {
        throw new RuntimeException("Tapahtumaa tai lipputyyppiä ei löytynyt");
    }
}
}
