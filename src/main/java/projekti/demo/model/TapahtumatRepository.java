package projekti.demo.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TapahtumatRepository extends CrudRepository<Tapahtumat, Integer>{

    List<Tapahtumat> findByNimi(String nimi);

}
