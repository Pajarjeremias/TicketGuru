package projekti.demo.model;

import java.util.List;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TapahtumaRepository extends JpaRepository<Tapahtuma, Long>{

    List<Tapahtuma> findByNimi(String nimi);

}
