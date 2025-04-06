package projekti.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "maksutavat")
public class Maksutapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maksutapa_id")
    private Long maksutapa_id;

    @NotEmpty
    @Size(max = 20)
    private String maksutapa;

    // Constructorit
    public Maksutapa() { }

    public Maksutapa(@NotEmpty @Size(max = 20) String maksutapa) {
      this.maksutapa = maksutapa;
    }

    // Getterit ja setterit
    public Long getMaksutapa_id() {
      return maksutapa_id;
    }

    public void setMaksutapa_id(Long maksutapa_id) {
      this.maksutapa_id = maksutapa_id;
    }

    public String getMaksutapa() {
      return maksutapa;
    }

    public void setMaksutapa(String maksutapa) {
      this.maksutapa = maksutapa;
    }

    // To string
    @Override
    public String toString() {
      return "Maksutapa [maksutapa_id=" + maksutapa_id + ", maksutapa=" + maksutapa + "]";
    }

}
