package projekti.demo.model;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface TilaRepository extends CrudRepository<Tila, Long> {

    Optional<Tila> findByMyyty(boolean myyty);
    Optional<Tila> findByMyymatta(boolean myymatta);
    Optional<Tila> findByTarkastettu(boolean tarkastettu);
    Optional<Tila> findByPeruttu(boolean peruttu);
}
