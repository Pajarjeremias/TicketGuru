package projekti.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyyntiRepository extends JpaRepository<Myynti, Integer> {
  
}