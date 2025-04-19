package projekti.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.validation.*;
import java.util.Set;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import projekti.demo.model.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LippuTests {
    
    @Autowired
    private LippuRepository lippuRepository;

    @Autowired
    private LipputyyppiRepository lipputyyppiRepository;

    @Autowired
    private Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository;

    @Autowired
    private TilaRepository tilaRepository;

    @Autowired
    private MyyntiRepository myyntiRepository;

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    private void getMyynti(){
        Optional<Myynti> myyntioptional = myyntiRepository.findById((long) 1);
        assertThat(myyntioptional).isPresent();
    }

    @Test
    public void createLippu(){
        Optional<Myynti> myyntiOptional = myyntiRepository.findById((long) 1);
        Optional<Lipputyyppi> lipputyyppiOptional = lipputyyppiRepository.findById((long) 1);
        Optional<Tapahtuman_lipputyyppi> tapahtumanlipputyyppOptional = tapahtuman_lipputyyppiRepository.findById((long) 1);
        Optional<Tila> tilaoOptional = tilaRepository.findById((long) 1);
        assertThat(myyntiOptional).isPresent();
        assertThat(lipputyyppiOptional).isPresent();
        assertThat(tapahtumanlipputyyppOptional).isPresent();
        assertThat(tilaoOptional).isPresent();
        Myynti myynti = myyntiOptional.get();
        Lipputyyppi lipputyyppi = lipputyyppiOptional.get();
        Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtumanlipputyyppOptional.get();
        Tila tila = tilaoOptional.get();
        Float hinta = (float) 11.0;
        Lippu uusilippu = new Lippu(tapahtuman_lipputyyppi,hinta, tila,myynti);
        lippuRepository.save(uusilippu);
        assertThat(uusilippu.getLippu_id()).isNotNull();
        assertThat(uusilippu.getKoodi()).isNotNull();
    }

    @Test
    public void testLipunKoodi(){
        Optional<Myynti> myyntiOptional = myyntiRepository.findById((long) 1);
        Optional<Lipputyyppi> lipputyyppiOptional = lipputyyppiRepository.findById((long) 1);
        Optional<Tapahtuman_lipputyyppi> tapahtumanlipputyyppOptional = tapahtuman_lipputyyppiRepository.findById((long) 1);
        Optional<Tila> tilaoOptional = tilaRepository.findById((long) 1);
        assertThat(myyntiOptional).isPresent();
        assertThat(lipputyyppiOptional).isPresent();
        assertThat(tapahtumanlipputyyppOptional).isPresent();
        assertThat(tilaoOptional).isPresent();
        Myynti myynti = myyntiOptional.get();
        Lipputyyppi lipputyyppi = lipputyyppiOptional.get();
        Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtumanlipputyyppOptional.get();
        Tila tila = tilaoOptional.get();
        Float hinta = (float) 11.0;
        Lippu uusilippu = new Lippu(tapahtuman_lipputyyppi,hinta, tila,myynti);
        lippuRepository.save(uusilippu);
        Optional<Lippu> koodilippu = lippuRepository.findByKoodi(uusilippu.getKoodi());
        assertThat(koodilippu).isPresent();
        Lippu codelippu = koodilippu.get();
        assertThat(codelippu).isEqualTo(uusilippu);
    }

    @Test
    public void testLippuHinta(){
        Optional<Myynti> myyntiOptional = myyntiRepository.findById((long) 1);
        Optional<Lipputyyppi> lipputyyppiOptional = lipputyyppiRepository.findById((long) 1);
        Optional<Tapahtuman_lipputyyppi> tapahtumanlipputyyppOptional = tapahtuman_lipputyyppiRepository.findById((long) 1);
        Optional<Tila> tilaoOptional = tilaRepository.findById((long) 1);
        assertThat(myyntiOptional).isPresent();
        assertThat(lipputyyppiOptional).isPresent();
        assertThat(tapahtumanlipputyyppOptional).isPresent();
        assertThat(tilaoOptional).isPresent();
        Myynti myynti = myyntiOptional.get();
        Lipputyyppi lipputyyppi = lipputyyppiOptional.get();
        Tapahtuman_lipputyyppi tapahtuman_lipputyyppi = tapahtumanlipputyyppOptional.get();
        Tila tila = tilaoOptional.get();
        Float hinta = (float) 0.0;
        Lippu uusilippu = new Lippu(tapahtuman_lipputyyppi,hinta, tila,myynti);
        assertThat(uusilippu.getHinta()).isEqualTo((float) 0.0);
        lippuRepository.save(uusilippu);
        assertThat(uusilippu.getLippu_id()).isNotNull();
        assertThat(uusilippu.getKoodi()).isNotNull();

        hinta = (float) 123456789.123;
        uusilippu = new Lippu(tapahtuman_lipputyyppi,hinta, tila,myynti);
        assertThat(uusilippu.getHinta()).isEqualTo((float) 123456789.123);

        hinta = (float) -200;
        uusilippu = new Lippu(tapahtuman_lipputyyppi,hinta, tila,myynti);
        Set<ConstraintViolation<Lippu>> violations = validator.validate(uusilippu);
        assertThat(violations).isNotEmpty().anyMatch(v -> v.getPropertyPath().toString().equals("hinta"));

        hinta = (float) -0.01;
        uusilippu = new Lippu(tapahtuman_lipputyyppi,hinta, tila,myynti);
        violations = validator.validate(uusilippu);
        assertThat(violations).isNotEmpty().anyMatch(v -> v.getPropertyPath().toString().equals("hinta"));
  
    }

}
