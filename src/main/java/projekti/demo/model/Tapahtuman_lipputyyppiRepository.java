package projekti.demo.model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface Tapahtuman_lipputyyppiRepository extends CrudRepository<Tapahtuman_lipputyyppi, Long> {
  List<Tapahtuman_lipputyyppi> findByTapahtuma(Tapahtuma tapahtuma);
}
