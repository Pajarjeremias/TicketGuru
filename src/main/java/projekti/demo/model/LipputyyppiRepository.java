package projekti.demo.model;

import org.springframework.data.repository.CrudRepository;

public interface LipputyyppiRepository extends CrudRepository<Lipputyyppi, Long> {

    Lipputyyppi findByLipputyyppi(String string);
    
}
