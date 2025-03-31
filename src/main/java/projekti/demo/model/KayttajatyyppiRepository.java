package projekti.demo.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface KayttajatyyppiRepository extends CrudRepository<Kayttajatyyppi, Long> {

    Kayttajatyyppi findByKayttajatyyppi(String kayttajatyyppi);

}
