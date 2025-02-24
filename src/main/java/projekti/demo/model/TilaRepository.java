package projekti.demo.model;

import org.springframework.data.repository.CrudRepository;

public interface TilaRepository extends CrudRepository<Tila, Long>{
    Tila findByTila(String tila);

}



