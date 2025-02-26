package projekti.demo.model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TapahtumanLipputyyppiRepository extends CrudRepository<Tapahtuman_lipputyypit, Long> {

}
