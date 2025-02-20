package projekti.demo.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public @interface TapahtumapaikatRepository extends CrudRepository<Tapahtumapaikat, Integer> {

    List<Tapahtumat> findByNimi(String nimi);

}
