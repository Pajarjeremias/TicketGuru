package projekti.demo;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import projekti.demo.model.Kayttaja;
import projekti.demo.model.KayttajaRepository;
import projekti.demo.model.Kayttajatyyppi;
import projekti.demo.model.KayttajatyyppiRepository;
import projekti.demo.model.Lippu;
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

@SpringBootApplication
public class DemoApplication {

    private final KayttajatyyppiRepository kayttajatyyppiRepository;

    private final KayttajaRepository kayttajaRepository;

    private final LippuRepository lippuRepository;

    private final TilaRepository tilaRepository;

    private final MyyntiRepository myyntiRepository;

    private final MyyntipisteRepository myyntipisteRepository;

    private final PostitoimipaikkaRepository postitoimipaikkaRepository;

    private final Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository;

    private final TapahtumaRepository tapahtumaRepository;

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    DemoApplication(TapahtumaRepository tapahtumaRepository, Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository, PostitoimipaikkaRepository postitoimipaikkaRepository, MyyntipisteRepository myyntipisteRepository, MyyntiRepository myyntiRepository, TilaRepository tilaRepository, LippuRepository lippuRepository, KayttajaRepository kayttajaRepository, KayttajatyyppiRepository kayttajatyyppiRepository) {
        this.tapahtumaRepository = tapahtumaRepository;
        this.tapahtuman_lipputyyppiRepository = tapahtuman_lipputyyppiRepository;
        this.postitoimipaikkaRepository = postitoimipaikkaRepository;
        this.myyntipisteRepository = myyntipisteRepository;
        this.myyntiRepository = myyntiRepository;
        this.tilaRepository = tilaRepository;
        this.lippuRepository = lippuRepository;
        this.kayttajaRepository = kayttajaRepository;
        this.kayttajatyyppiRepository = kayttajatyyppiRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoRunner(
		LipputyyppiRepository lipputyyppiRepository,
		TapahtumaRepository tapahtumaRepository,
		Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository,
		MaksutapaRepository maksutapaRepository,
		MyyntipisteRepository myyntipisteRepository,
		PostitoimipaikkaRepository postitoimipaikkaRepository,
		MyyntiRepository myyntiRepository,
		TilaRepository tilaRepository,
		LippuRepository lippuRepository,
		KayttajaRepository kayttajaRepository,
		KayttajatyyppiRepository kayttajatyyppiRepository

	) {
		return(args) -> {

			

			logger.info("Lisätään lipputyyppejä...");

			Lipputyyppi aikuinen = new Lipputyyppi("Aikuinen");
			Lipputyyppi lapsi = new Lipputyyppi("Lapsi");
			Lipputyyppi elakelainen = new Lipputyyppi("Eläkeläinen");

			if (lippuRepository.count() == 0) {
				lipputyyppiRepository.save(aikuinen);
				lipputyyppiRepository.save(lapsi);
				lipputyyppiRepository.save(elakelainen);
			}



			logger.info("Lisätään tapahtumia...");

			Tapahtuma tapahtuma1 = new Tapahtuma("Konsertti 1", LocalDate.of(2025, Month.JULY, 18).atStartOfDay(), "Paras konsertti ikinä.", 50);
			Tapahtuma tapahtuma2 = new Tapahtuma("Urheilutapahtuma 3", LocalDate.of(2025, Month.JUNE, 1).atStartOfDay(), "Paras urheilutapahtuma ikinä.", 250);

			if (tapahtumaRepository.count() == 0) {
				tapahtumaRepository.save(tapahtuma1);
				tapahtumaRepository.save(tapahtuma2);
			}


			logger.info("Lisätään tapahtumille lipputyyppejä...");

			Tapahtuman_lipputyyppi tapahtuma1_aikuinen = new Tapahtuman_lipputyyppi(tapahtuma1, aikuinen, (float) 30.00);
			Tapahtuman_lipputyyppi tapahtuma1_lapsi = new Tapahtuman_lipputyyppi(tapahtuma1, lapsi, (float) 15.00);

			Tapahtuman_lipputyyppi tapahtuma2_aikuinen = new Tapahtuman_lipputyyppi(tapahtuma2, aikuinen, (float) 19.90);
			Tapahtuman_lipputyyppi tapahtuma2_lapsi = new Tapahtuman_lipputyyppi(tapahtuma2, lapsi, (float) 5.90);
			Tapahtuman_lipputyyppi tapahtuma2_elakelainen = new Tapahtuman_lipputyyppi(tapahtuma2, elakelainen, (float) 0.00);

			if (tapahtuman_lipputyyppiRepository.count() == 0) {
				tapahtuman_lipputyyppiRepository.save(tapahtuma1_aikuinen);
				tapahtuman_lipputyyppiRepository.save(tapahtuma1_lapsi);
				tapahtuman_lipputyyppiRepository.save(tapahtuma2_aikuinen);
				tapahtuman_lipputyyppiRepository.save(tapahtuma2_lapsi);
				tapahtuman_lipputyyppiRepository.save(tapahtuma2_elakelainen);
			}



			logger.info("Lisätään maksutapoja...");

			Maksutapa maksutapa1 = new Maksutapa("Käteinen");
			if (maksutapaRepository.count() == 0) {
				maksutapaRepository.save(maksutapa1);
				maksutapaRepository.save(new Maksutapa("Kortti"));
				maksutapaRepository.save(new Maksutapa("Muu"));
			}


			
			logger.info("Lisätään postitoimipaikkoja...");
			Postitoimipaikka postitmpk1 = new Postitoimipaikka("00520", "Helsinki", "Suomi");
			if (postitoimipaikkaRepository.count() == 0) {
				postitoimipaikkaRepository.save(postitmpk1);
			}



			logger.info("Lisätään myyntipisteitä...");
			Myyntipiste myyntipiste1 = new Myyntipiste("Ensimmäinen piste","Messuaukio 1", postitmpk1);
			if (myyntipisteRepository.count() == 0) {
				myyntipisteRepository.save(myyntipiste1);
			}

			Myynti myynti2 = new Myynti(LocalDate.of(2025, 6, 15), myyntipiste1, maksutapa1);
			logger.info("Lisätään myyntejä...");
			if (myyntiRepository.count() == 0) {
				myyntiRepository.save(new Myynti(LocalDate.of(2025, 6, 15), myyntipiste1, maksutapa1));

				myyntiRepository.save(myynti2);
			}


			logger.info("Lisätään tiloja...");
			Tila myyty = new Tila("Myyty");
			Tila myymatta = new Tila("Myymättä");
			Tila tarkastettu = new Tila("Tarkastettu");
			Tila peruttu = new Tila("Peruttu");

			if (tilaRepository.count() == 0) {
				tilaRepository.save(myyty);
				tilaRepository.save(myymatta);
				tilaRepository.save(tarkastettu);
				tilaRepository.save(peruttu);
			}

			
			
			Lippu lippu1 = new Lippu(tapahtuma1_aikuinen, (float) 3, myyty, myynti2);
			if (lippuRepository.count() == 0) {
				lippuRepository.save(lippu1); 
			}

			for (long i = 1; i <= 4; i++) {
				lippuRepository.findById(i).ifPresent(lippu -> {
					if (lippu.getKoodi() == null) {
						lippu.setKoodi(UUID.randomUUID().toString());
						lippuRepository.save(lippu);
					}
				});
			}



			logger.info("lisätään käyttäjätyypät");

			Kayttajatyyppi asiakas = new Kayttajatyyppi("Asiakas", "Tuiki tavallinen palveluiden kuluttaja");
			Kayttajatyyppi lipunmyyja = new Kayttajatyyppi("Lipunmyyja", "Kovan luokan laillinen trokari");
			Kayttajatyyppi tapahtumavastaava = new Kayttajatyyppi("Tapahtumavastaava", "Mestan tirehtööri niin että, party never ends.");
			Kayttajatyyppi yllapitaja = new Kayttajatyyppi("Yllapitaja", "Tää on se noobi, joka potkii sut pihalle servulta suutuspäissään. aka'Tech_god' ");

			if (kayttajatyyppiRepository.count() == 0) {
				kayttajatyyppiRepository.save(asiakas);
				kayttajatyyppiRepository.save(lipunmyyja);
				kayttajatyyppiRepository.save(tapahtumavastaava);
				kayttajatyyppiRepository.save(yllapitaja);
			}


			logger.info("Lisätään käyttäjiä...");

			Kayttaja kayttaja1 = new Kayttaja("asiakas", "$2a$10$fs/sPJqj.ZdZHSPpen19GONDN7HhVXTB/oXJFflS8kzMyfgLouwoq", "Jeremias", "Pajari", "0449834478", "jeremias.pajari@gmail.com", "vanhatie 5", postitmpk1, asiakas);
			Kayttaja kayttaja2 = new Kayttaja("lipunmyyjä", "$2a$10$HLxDiZI9wgTbvc4ky0/eletLO9odk0vTh8Nw9lDECQdfZ/DcVm6P2", "Matti", "Meiikäläinen", "040459596", "matti.meikalaienn@gmail.com", "jokukatu1", postitmpk1, lipunmyyja);
			Kayttaja kayttaja3 = new Kayttaja("tapahtumavastaava", "$2a$10$.CJDmiwhy06KrVn1X3qIcOba2vl8bZ09odC.j8YqHDJlwNAV569P.", "tapahtuma", "vastaava", "023954365", "srfdhsgafa@gmail.com", "ruttopuisto 1", postitmpk1, tapahtumavastaava);
			Kayttaja kayttaja4 = new Kayttaja("yllapitaja", "$2a$10$cXQXTxyhjEznUURgTLxju.deYV7U6fbJVJ.7iCNc8goIZvmK0TVG.", "ylla", "pitaja", "0405349534", "asgads@gmail.com", "jumbonparkkipaikka", postitmpk1, yllapitaja);

			if (kayttajaRepository.count() == 0) {
				kayttajaRepository.save(kayttaja1);
				kayttajaRepository.save(kayttaja2);
				kayttajaRepository.save(kayttaja3);
				kayttajaRepository.save(kayttaja4);
			}

			
			logger.info("käyttäjät lisätty onnistuneesti :)"); 

		}; 
	}

}
