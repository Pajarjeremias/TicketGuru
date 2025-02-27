package projekti.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Kayttajatyypit")
public class Kayttajatyyppi {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long kayttajatyyppi_id;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "kayttajatyyppi")
  @JsonIgnore
  private List<Kayttaja> kayttajat;

  @NotEmpty(message = "Käyttäjätyyppi on pakollinen")
  @Size(max = 20, message= "Maksimipituus 20 merkkiä")
  private String kayttajatyyppi;

  @Size(max = 500, message = "Maksimipituus 500 merkkiä")
  private String kuvaus;

  public Kayttajatyyppi() { }

  public Kayttajatyyppi(String kayttajatyyppi, String kuvaus) {
    super();
    this.kayttajatyyppi = kayttajatyyppi;
    this.kuvaus = kuvaus;
  }

  public String getKayttajatyyppi() {
    return kayttajatyyppi;
  }

  public void setKayttajatyyppi(String kayttajatyyppi) {
    this.kayttajatyyppi = kayttajatyyppi;
  }

  public String getKuvaus() {
    return kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  public List<Kayttaja> getKayttajat() {
    return kayttajat;
  }

  public void setKayttajat(List<Kayttaja> kayttajat) {
    this.kayttajat = kayttajat;
  }

  public Long getKayttajatyyppi_id() {
    return kayttajatyyppi_id;
  }

  public void setKayttajatyyppi_id(Long kayttajatyyppi_id) {
    this.kayttajatyyppi_id = kayttajatyyppi_id;
  }

  @Override
  public String toString() {
    return "Kayttajatyyppi [kayttajatyyppi_id=" + kayttajatyyppi_id + ", kayttajat=" + kayttajat + ", kayttajatyyppi="
        + kayttajatyyppi + ", kuvaus=" + kuvaus + "]";
  }

}
