package projekti.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import projekti.demo.model.Lipputyyppi;
import projekti.demo.model.LipputyyppiRepository;
import projekti.demo.model.Maksutapa;
import projekti.demo.model.MaksutapaRepository;
import projekti.demo.model.Myynti;
import projekti.demo.model.MyyntiRepository;
import projekti.demo.model.Myyntipiste;
import projekti.demo.model.MyyntipisteRepository;
import projekti.demo.model.Postitoimipaikka;
import projekti.demo.model.PostitoimipaikkaRepository;
import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;
import projekti.demo.model.Tapahtuman_lipputyyppi;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;
import projekti.demo.model.Tila;
import projekti.demo.model.TilaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "yllapitaja", authorities = {"Yllapitaja"})

public class LippuRestTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TilaRepository tilaRepository;

    @Autowired
    private MyyntiRepository myyntiRepository;

    @Autowired
    private TapahtumaRepository tapahtumaRepository;

    @Autowired
    private LipputyyppiRepository lipputyyppiRepository;

    @Autowired
    private Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository;

    @Autowired
    private MaksutapaRepository maksutapaRepository;

    @Autowired
    private PostitoimipaikkaRepository postitoimipaikkaRepository;

    @Autowired
    private MyyntipisteRepository myyntipisteRepository;

    private Long myyntiId;
    private Long tapahtumanLipputyyppiId;
    private Long tilaId;
    private Long maksutapaId;
    private String postitoimipaikkaId;
    private Integer myyntipisteId;

    @BeforeEach
    public void asetaTestiData() {
        Tila tila = new Tila();
        tila.setTila("Myyty");
        tilaId = tilaRepository.save(tila).getTila_id();

        Tapahtuma tapahtuma = new Tapahtuma();
        tapahtuma.setNimi("Testitapahtuma");
        tapahtuma.setPaivamaara(LocalDate.of(2025, Month.JULY, 18).atStartOfDay());
        tapahtuma = tapahtumaRepository.save(tapahtuma);

        Lipputyyppi lipputyyppi = new Lipputyyppi();
        lipputyyppi.setLipputyyppi("Aikuinen");
        lipputyyppi = lipputyyppiRepository.save(lipputyyppi);

        Tapahtuman_lipputyyppi tl = new Tapahtuman_lipputyyppi();
        tl.setTapahtuma(tapahtuma);
        tl.setLipputyyppi(lipputyyppi);
        tl.setHinta((float)30.00);
        tapahtumanLipputyyppiId = tapahtuman_lipputyyppiRepository.save(tl).getTapahtuma_lipputyyppi_id();

        Maksutapa maksutapa1 = new Maksutapa("Käteinen");
        maksutapaId = maksutapaRepository.save(maksutapa1).getMaksutapa_id();

        Postitoimipaikka postitmpk1 = new Postitoimipaikka("00520", "Helsinki", "Suomi");
        postitoimipaikkaId = postitoimipaikkaRepository.save(postitmpk1).getPostinumero();

        Myyntipiste myyntipiste1 = new Myyntipiste("Ensimmäinen piste", "Messuaukio 1", postitmpk1);
        myyntipisteId = myyntipisteRepository.save(myyntipiste1).getMyyntipisteId();

        Myynti myynti = new Myynti(LocalDate.now(), myyntipiste1, maksutapa1);
        myyntiId = myyntiRepository.save(myynti).getMyynti_id();        
    }

    @Test
    public void testLippuPost() throws Exception {
        String uusiLippuJson = """
        {
            "myynti_id": { "id": %d },
            "tapahtuman_lipputyypit_id": {"id": %d }
        }
        """.formatted(myyntiId, tapahtumanLipputyyppiId);

        mockMvc.perform(post("/api/liput")
                .contentType(MediaType.APPLICATION_JSON)
                .content(uusiLippuJson))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.koodi").exists());
    }
    
}
