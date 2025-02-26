package projekti.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Kayttaja {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long kayttaja_id;

  @NotEmpty(message = "Kayttajatunnus on pakollinen")
  @Size(max = 100, message = "Maksimipituus 100 merkkiä")
  private String kayttajatunnus;

  @NotEmpty(message = "Salasana_hash on pakollinen")
  @Size(max = 60, message = "Maksimipituus 60 merkkiä")
  private String salasana_hash;

  @Size(max = 100, message = "Maksimipituus 100 merkkiä")
  private String etunimi;

  @Size(max = 100, message = "Maksimipituus 100 merkkiä")
  private String sukunimi;

  @Size(max = 20, message = "Maksimipituus 20 merkkiä")
  private String puh_nro;

  @Size(max = 100, message = "Maksimipituus 100 merkkiä")
  private String email;

  @Size(max = 100, message = "Maksimipituus 100 merkkiä")
  private String katuosoite;


  @ManyToOne
  @JoinColumn(name = "postinumero_id")
  private Postitoimipaikka postinumero;


  @NotEmpty(message = "Käyttäjätyyppi on pakollinen")
  @ManyToOne
  @JoinColumn(name = "kayttajatyyppi_id")
  private Kayttajatyyppi kayttajatyyppi;

  // Constructorit
  public Kayttaja() { }

  public Kayttaja(String kayttajatunnus, String salasana_hash, Kayttajatyyppi kayttajatyyppi) {
    super();
    this.kayttajatunnus = kayttajatunnus;
    this.salasana_hash = salasana_hash;
    this.kayttajatyyppi = kayttajatyyppi;
  }

  public Kayttaja(String kayttajatunnus, String salasana_hash, String etunimi, String sukunimi, String puh_nro, String email, String katuosoite, Postitoimipaikka postinumero, Kayttajatyyppi kayttajatyyppi) {
    this.kayttajatunnus = kayttajatunnus;
    this.salasana_hash = salasana_hash;
    this.etunimi = etunimi;
    this.sukunimi = sukunimi;
    this.puh_nro = puh_nro;
    this.email = email;
    this.katuosoite = katuosoite;
    this.postinumero = postinumero;
    this.kayttajatyyppi = kayttajatyyppi;
  }

  // getterit ja setterit
  public String getKayttajatunnus() {
    return kayttajatunnus;
  }

  public void setKayttajatunnus(String kayttajatunnus) {
    this.kayttajatunnus = kayttajatunnus;
  }

  public String getSalasana_hash() {
    return salasana_hash;
  }

  public void setSalasana_hash(String salasana_hash) {
    this.salasana_hash = salasana_hash;
  }

  public String getEtunimi() {
    return etunimi;
  }

  public void setEtunimi(String etunimi) {
    this.etunimi = etunimi;
  }

  public String getSukunimi() {
    return sukunimi;
  }

  public void setSukunimi(String sukunimi) {
    this.sukunimi = sukunimi;
  }

  public String getPuh_nro() {
    return puh_nro;
  }

  public void setPuh_nro(String puh_nro) {
    this.puh_nro = puh_nro;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getKatuosoite() {
    return katuosoite;
  }

  public void setKatuosoite(String katuosoite) {
    this.katuosoite = katuosoite;
  }

  public Postitoimipaikka getPostinumero() {
    return postinumero;
  }

  public void setPostinumero(Postitoimipaikka postinumero) {
    this.postinumero = postinumero;
  }

  public Kayttajatyyppi getKayttajatyyppi() {
    return kayttajatyyppi;
  }

  public void setKayttajatyyppi(Kayttajatyyppi kayttajatyyppi) {
    this.kayttajatyyppi = kayttajatyyppi;
  }

  public Long getKayttaja_id() {
    return kayttaja_id;
  }

  public void setKayttaja_id(Long kayttaja_id) {
    this.kayttaja_id = kayttaja_id;
  }

  // to string
  @Override
  public String toString() {
    return "Kayttaja [kayttaja_id=" + kayttaja_id + ", kayttajatunnus=" + kayttajatunnus + ", salasana_hash="
        + salasana_hash + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", puh_nro=" + puh_nro + ", email="
        + email + ", katuosoite=" + katuosoite + ", postinumero=" + postinumero + ", kayttajatyyppi=" + kayttajatyyppi
        + "]";
  }

}
