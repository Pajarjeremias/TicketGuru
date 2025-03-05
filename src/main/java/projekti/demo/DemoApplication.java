package projekti.demo;

import java.time.LocalDate;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import projekti.demo.model.Lipputyyppi;
import projekti.demo.model.LipputyyppiRepository;
import projekti.demo.model.Maksutapa;
import projekti.demo.model.MaksutapaRepository;
import projekti.demo.model.Tapahtuma;
import projekti.demo.model.TapahtumaRepository;
import projekti.demo.model.Tapahtuman_lipputyyppi;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;

@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoRunner(
		LipputyyppiRepository lipputyyppiRepository,
		TapahtumaRepository tapahtumaRepository,
		Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository,
		MaksutapaRepository maksutapaRepository
	) {
		return(args) -> {

			logger.info("Lisätään lipputyyppejä...");

			Lipputyyppi aikuinen = new Lipputyyppi("Aikuinen");
			Lipputyyppi lapsi = new Lipputyyppi("Lapsi");
			Lipputyyppi elakelainen = new Lipputyyppi("Eläkeläinen");

			lipputyyppiRepository.save(aikuinen);
			lipputyyppiRepository.save(lapsi);
			lipputyyppiRepository.save(elakelainen);


			logger.info("Lisätään tapahtumia...");

			Tapahtuma tapahtuma1 = new Tapahtuma("Konsertti 1", LocalDate.of(2025, Month.MARCH, 18).atStartOfDay(), "Paras konsertti ikinä.", 50);
			Tapahtuma tapahtuma2 = new Tapahtuma("Urheilutapahtuma 3", LocalDate.of(2025, Month.APRIL, 1).atStartOfDay(), "Paras urheilutapahtuma ikinä.", 250);

			tapahtumaRepository.save(tapahtuma1);
			tapahtumaRepository.save(tapahtuma2);


			logger.info("Lisätään tapahtumille lipputyyppejä...");

			Tapahtuman_lipputyyppi tapahtuma1_aikuinen = new Tapahtuman_lipputyyppi(tapahtuma1, aikuinen, (float) 30.00);
			Tapahtuman_lipputyyppi tapahtuma1_lapsi = new Tapahtuman_lipputyyppi(tapahtuma1, lapsi, (float) 15.00);

			Tapahtuman_lipputyyppi tapahtuma2_aikuinen = new Tapahtuman_lipputyyppi(tapahtuma2, aikuinen, (float) 19.90);
			Tapahtuman_lipputyyppi tapahtuma2_lapsi = new Tapahtuman_lipputyyppi(tapahtuma2, lapsi, (float) 5.90);
			Tapahtuman_lipputyyppi tapahtuma2_elakelainen = new Tapahtuman_lipputyyppi(tapahtuma2, elakelainen, (float) 0.00);

			tapahtuman_lipputyyppiRepository.save(tapahtuma1_aikuinen);
			tapahtuman_lipputyyppiRepository.save(tapahtuma1_lapsi);
			tapahtuman_lipputyyppiRepository.save(tapahtuma2_aikuinen);
			tapahtuman_lipputyyppiRepository.save(tapahtuma2_lapsi);
			tapahtuman_lipputyyppiRepository.save(tapahtuma2_elakelainen);


			logger.info("Lisätään maksutapoja...");
			
			maksutapaRepository.save(new Maksutapa("Käteinen"));
			maksutapaRepository.save(new Maksutapa("Kortti"));
			maksutapaRepository.save(new Maksutapa("Muu"));

		};
	}

}
