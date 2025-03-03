package projekti.demo.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LippuRepository extends CrudRepository<Lippu, Long> {
    List<Lippu> findByTila(Tila tila);
}
