package projekti.demo.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface KayttajaRepository extends CrudRepository<Kayttaja, Long> {
  Kayttaja findByKayttajatunnusIgnoreCase(String username);

  List<Kayttaja> findByKayttajatunnus(String kayttajatunnus);

 // Kayttaja findByKayttajatunnus(String kayttajatunnus);

}
