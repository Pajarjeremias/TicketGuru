package projekti.demo.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface LippuRepository extends CrudRepository<Lippu, Long> {
    Optional<Lippu> findByKoodi(@Param("koodi") String koodi);

}
