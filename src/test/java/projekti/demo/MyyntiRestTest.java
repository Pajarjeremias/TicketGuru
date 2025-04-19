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

import projekti.demo.model.LippuRepository;
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

public class MyyntiRestTest {

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
        Tila myyty = tilaRepository.findByTila("Myyty");
        if (myyty == null) {
            myyty = tilaRepository.save(new Tila("Myyty"));
        }

        Tapahtuma tapahtuma = new Tapahtuma("Testitapahtuma", LocalDate.of(2025, Month.JULY, 18).atStartOfDay(), "Paras testi ikinä.", 250);
        tapahtuma = tapahtumaRepository.save(tapahtuma);

        Lipputyyppi opiskelija = new Lipputyyppi("Opiskelija");
		lipputyyppiRepository.save(opiskelija);
			

        Tapahtuman_lipputyyppi tapahtuma1_aikuinen = new Tapahtuman_lipputyyppi(tapahtuma, opiskelija, (float) 30.00);
        tapahtumanLipputyyppiId = tapahtuman_lipputyyppiRepository.save(tapahtuma1_aikuinen).getTapahtuma_lipputyyppi_id();

        Maksutapa maksutapa1 = new Maksutapa("ePassi");
        maksutapaId = maksutapaRepository.save(maksutapa1).getMaksutapa_id();

        Postitoimipaikka postitmpk1 = new Postitoimipaikka("00100", "Helsinki", "Suomi");
        postitoimipaikkaId = postitoimipaikkaRepository.save(postitmpk1).getPostinumero();

        Myyntipiste myyntipiste1 = new Myyntipiste("Testimyyntipiste", "Messuaukio 1", postitmpk1);
        myyntipisteId = myyntipisteRepository.save(myyntipiste1).getMyyntipisteId();

        Myynti myynti = new Myynti(LocalDate.now(), myyntipiste1, maksutapa1);
        myyntiId = myyntiRepository.save(myynti).getMyynti_id();        
    }

    @Test
    public void testMyyntiPostOk() throws Exception {
        String uusiMyyntiJson = """
        {
            "liput": [],
            "myyntipiste": {
                "myyntipisteId": %d
            },
            "maksutapa": {
                "maksutapa_id": %d
            }
        }
        """.formatted(myyntipisteId, maksutapaId);

        mockMvc.perform(post("/api/myynnit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(uusiMyyntiJson))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testMyyntiPost_invalidMaksutapa() throws Exception {
        String virheellinenMaksutapaJson = """
                {
                    "liput": [],
                    "myyntipiste": {
                        "myyntipisteId": %d
                    },
                    "maksutapa": {
                        "maksutapa_id":999
                    }
                }
                """.formatted(myyntipisteId);

                mockMvc.perform(post("/api/myynnit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(virheellinenMaksutapaJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string({"Not Found":"Invalid value for 'maksutapa', please check.Id must be a valid id-number."}));
    }
    
}
