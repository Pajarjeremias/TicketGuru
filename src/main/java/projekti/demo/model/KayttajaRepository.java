package projekti.demo.model;

import java.util.Optional;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KayttajaRepository extends JpaRepository<Kayttaja, Long> {

}
