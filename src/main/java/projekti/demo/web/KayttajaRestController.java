package projekti.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import projekti.demo.model.Kayttaja;
import projekti.demo.model.KayttajaRepository;
import projekti.demo.model.Kayttajatyyppi;
import projekti.demo.model.KayttajatyyppiRepository;
import projekti.demo.model.Lippu;
import projekti.demo.model.LippuRepository;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.PostLippuModel;
import projekti.demo.model.Postitoimipaikka;
import projekti.demo.model.PostitoimipaikkaRepository;
import projekti.demo.model.Tapahtuman_lipputyyppi;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;
import projekti.demo.model.Tila;
import projekti.demo.model.TilaRepository;
import projekti.demo.model.PutLippuModel;
import projekti.demo.model.KayttajaModel;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@EnableMethodSecurity(securedEnabled = true)
public class KayttajaRestController {

    @Autowired
    KayttajaRepository kayttajaRepository;

    @Autowired
    PostitoimipaikkaRepository postitoimipaikkaRepository;

    @Autowired
    KayttajatyyppiRepository kayttajatyyppiRepository;

    // luo uusi käyttäjä
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @PostMapping(value = { "/api/kayttajat", "/api/kayttajat/" })
    public ResponseEntity<?> createKayttaja(@RequestBody Kayttaja kayttaja) {
        try {
            System.out.println("käyttäjätunnus on " + kayttaja.getKayttajatunnus());
            System.out.println("syötetty postinumero on " + kayttaja.getPostinumero());
             // mikäli käyttäjätyyppiä ei ole ilmoitettu, asetetaan käyttäjätyypiksi Asiakas
            if (kayttaja.getKayttajatyyppi() == null) {
                System.out.println("kayttajatyyppi on " + kayttaja.getKayttajatyyppi());
                kayttaja.setKayttajatyyppi(kayttajatyyppiRepository.findByKayttajatyyppi("Asiakas"));
            }
   
            // tarkastetaan, mikäli käyttäjätunnus on jo varattu
            if (kayttajaRepository.findByKayttajatunnusIgnoreCase(kayttaja.getKayttajatunnus()) != null) {
                throw new Exception("Username is allready taken.");
            }

            // haetaan tietokannassa syötetyt käyttäjätyyppi ja postitoimipaikka
            Kayttajatyyppi kayttajatyyppi = kayttajatyyppiRepository
                    .findById(kayttaja.getKayttajatyyppi().getKayttajatyyppi_id()).orElse(null);
            // tarkastetaan, että annettu käyttäjätyyppi on olemassa, jos ei heitetään
            // exception
            if (kayttajatyyppi == null) {
                throw new Exception("Invalid value for kayttajatyyppi. Please check ID");
            }

            // tämä ei oikeastaan ole tarpeellinen
            Postitoimipaikka postitoimipaikka = null;
            if (kayttaja.getPostinumero() != null) {
                if (kayttaja.getPostinumero().getPostinumero() != null) {
                    System.out.println("postinumero annettu");
                    String postinumeroid = kayttaja.getPostinumero().getPostinumero();
                    Optional<Postitoimipaikka> postitoimipaikkaopt = postitoimipaikkaRepository.findById(postinumeroid);
                    if (postitoimipaikkaopt.isPresent()) {
                        postitoimipaikka = postitoimipaikkaopt.get();
                        kayttaja.setPostinumero(postitoimipaikka);
                    }
                } else {
                    System.out.println("postinumeroa ei annettua, se asetetaan muotoon null ja oli jo ");
                }
            }

            // muutetaan salasana hash muotoon, ennen tallennusta
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passhasString = passwordEncoder.encode(kayttaja.getSalasana_hash());
            kayttaja.setSalasana_hash(passhasString);


            kayttajaRepository.save(kayttaja);

            return new ResponseEntity<Kayttaja>(kayttaja, HttpStatus.CREATED);
        } catch (Exception e) {

            // Palautetaan virheviesti
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // hae yksittäinen käyttäjä
    @PreAuthorize("hasAnyAuthority('Yllapitaja')")
    @GetMapping("/api/kayttajat/{id}")
    public Kayttaja getMethodName(@PathVariable Long id) {
        return kayttajaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No kayttaja on give ID " + id));

    }

    // hae kaikki käyttäjät
    @PreAuthorize("hasAnyAuthority('Yllapitaja')")
    @GetMapping(value = { "/api/kayttajat", "/api/kayttajat/" })
    public ResponseEntity<List<Kayttaja>> getAllKayttajat() {
        System.out.println("Päästiin oikeaan paikkaa - HAE KAIKKI KÄYTTÄJÄT");
        try {
            Iterable<Kayttaja> kayttajatiterable = kayttajaRepository.findAll();
            List<Kayttaja> kayttajat = new ArrayList<>();
            kayttajatiterable.forEach(kayttajat::add);
            if (kayttajat.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(kayttajat);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "database error, while getting data ",
                    e);
        }
    }

    // päivitä käyttäjän tietoja
    @PreAuthorize("hasAnyAuthority('Yllapitaja', 'Tapahtumavastaava', 'Lipunmyyja')")
    @PutMapping(value = { "/api/kayttajat/{id}"})
    public ResponseEntity<?> putKayttaja(@RequestBody KayttajaModel kayttajaModel, @PathVariable Long id) {
        
        // tarkistetaan löytyykö annettulla id:llä käyttäjää, jos ei heitetään 
        Kayttaja kayttajaedit = kayttajaRepository.findById(id).orElse(null);
        if (kayttajaedit == null){
            throw new IllegalArgumentException("There is no user (kayttaja) in given id ");
        } else {
        }

        // jos pyynnössä on etunimi, niin päivitetään se kayttajaedit:n
        if (kayttajaModel.getEtunimi() != null){
            kayttajaedit.setEtunimi(kayttajaModel.getEtunimi());
        }

        // jos pyynnössä on sukunimi , niin päivitetään se 
        if (kayttajaModel.getSukunimi() != null){
            kayttajaedit.setSukunimi(kayttajaModel.getSukunimi());
        }

        // jos pyynnöss on salasana, nii npäivitetään se
        if (kayttajaModel.getSalasana_hash() != null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passhasString = passwordEncoder.encode(kayttajaModel.getSalasana_hash());
            kayttajaedit.setSalasana_hash(passhasString);
        }

        // jos pyynnössä on katuosoite, niin päivitetään se
        if (kayttajaModel.getKatuosoite() != null){
            kayttajaedit.setKatuosoite(kayttajaModel.getKatuosoite());
        }

        // jos pyynnössä on postinumero, niin päivitetään se 
        if (kayttajaModel.getPostinumero() != null){
            kayttajaedit.setPostinumero(kayttajaModel.getPostinumero());
        } 

        // jos pyynnössä on puhellinnumero niin päivitetään se
        if (kayttajaModel.getPuh_nro() != null){
            kayttajaedit.setPuh_nro(kayttajaModel.getPuh_nro());
        }

        // jos pyynnössää on email, niin päivitetään se
        if (kayttajaModel.getEmail() != null){
            kayttajaedit.setEmail(kayttajaModel.getEmail());
        }
      
        kayttajaRepository.save(kayttajaedit);
        return new ResponseEntity<>(kayttajaedit, HttpStatus.OK);



    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {

        return ResponseEntity.badRequest().body(Map.of("virhe",
                "Tieto väärässä muodossa, tarkasta syötteiden arvot. IDn tulee olla kokonaislukuja. Mahdollisen hinnan tulee olla joko kokonaisluku tai liukuluku. Päivämäärän ja ajan tulee olla muodossa yyyy-MM-dd'T'HH:mm:ss.   "
                        + ex.getMessage()));

    }
}
