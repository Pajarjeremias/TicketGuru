package projekti.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import projekti.demo.model.Lipputyyppi;
import projekti.demo.model.LipputyyppiRepository;
import projekti.demo.model.TapahtumaRepository;
import projekti.demo.model.Tapahtuman_lipputyyppiRepository;

@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoRunner(
		TapahtumaRepository tapahtumaRepository,
		Tapahtuman_lipputyyppiRepository tapahtuman_lipputyyppiRepository,
		LipputyyppiRepository lipputyyppiRepository
	) {
		return(args) -> {

			Lipputyyppi aikuinen = new Lipputyyppi("Aikuinen")


		};
	}

}
