package projekti.demo.model;

import org.springframework.data.repository.CrudRepository;

public interface PostitoimipaikkaRepository extends CrudRepository<Postitoimipaikka, String> {

    Postitoimipaikka findByPostitoimipaikka (Postitoimipaikka postinumero);

    
} 