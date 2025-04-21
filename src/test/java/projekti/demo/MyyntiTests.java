package projekti.demo;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import projekti.demo.model.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyyntiTests {

    @Autowired
    private MaksutapaRepository maksutapaRepository;

    @Autowired
    private MyyntipisteRepository myyntipisteRepository;

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Test
    public void createMyynti() {
        Optional<Maksutapa> maksutapaOptional = maksutapaRepository.findById((long) 1);
        assertThat(maksutapaOptional).isNotEmpty();
        Maksutapa maksutapa = maksutapaOptional.get();
        Optional<Myyntipiste> myyntipisteOptional = myyntipisteRepository.findById( 1);
        assertThat(myyntipisteOptional).isPresent();
        Myyntipiste myyntipiste = myyntipisteOptional.get();
        LocalDate paivays = LocalDate.now();
        Myynti uusimyynti = new Myynti(paivays, myyntipiste, maksutapa);
        myyntiRepository.save(uusimyynti);
        assertThat(uusimyynti.getMyynti_id()).isNotNull(); 

    }

    @Test
    public void testMyynnit(){
        Optional<Maksutapa> maksutapaOptional = maksutapaRepository.findById((long) 1);
        assertThat(maksutapaOptional).isNotEmpty();
        Maksutapa maksutapa = maksutapaOptional.get();
        Optional<Myyntipiste> myyntipisteOptional = myyntipisteRepository.findById( 1);
        assertThat(myyntipisteOptional).isPresent();
        Myyntipiste myyntipiste = myyntipisteOptional.get();
        LocalDate paivays = LocalDate.now();
        Myynti uusimyynti = new Myynti(paivays, myyntipiste, maksutapa);
        myyntiRepository.save(uusimyynti);
        assertThat(uusimyynti.getMyynti_id()).isNotNull();
        Myynti uusimyynti2 = new Myynti(paivays, myyntipiste, maksutapa);
        myyntiRepository.save(uusimyynti2);

        List<Myynti> myynnit = myyntiRepository.findAll();
        assertThat(myynnit).isNotEmpty();
        assertThat(myynnit).size().isGreaterThan(1);

    }

    @Test
    public void myynninPaivamaara() {
        Optional<Myynti> myyntiOptional = myyntiRepository.findById((long) 2); 
        assertThat(myyntiOptional).isPresent();
        Myynti myynti = myyntiOptional.get();
        assertThat(myynti.getMyyntipaiva()).isInstanceOf(LocalDate.class);
        Maksutapa maksutapa = myyntiOptional.get().getMaksutapa();
        assertThat(myynti.getMaksutapa()).isEqualTo(maksutapa);
    }
    
}
