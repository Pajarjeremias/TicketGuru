package projekti.demo.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TapahtumaRepository extends CrudRepository<Tapahtuma, Long>{

    List<Tapahtuma> findByNimi(String nimi);

}
