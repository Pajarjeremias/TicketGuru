package projekti.demo.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class PutTapahtumaModel {

  @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä")
  private String nimi;

  @Future(message = "Päivämäärän ja kellonajan on oltava tulevaisuudessa.")
  private LocalDateTime paivamaara;

  @Size(min = 2, max = 2500, message = "Pituuden täytyy olla 2-2500 merkkiä")
  private String kuvaus;

  private Long tapahtumapaikka_id;

  private Long jarjestaja_id;
  
  public Long getJarjestaja_id() {
    return jarjestaja_id;
  }

  public void setJarjestaja_id(Long jarjestaja_id) {
    this.jarjestaja_id = jarjestaja_id;
  }

  @Min(value = 1, message = "Lippumäärän täytyy olla positiivinen kokonaisluku")
  private int lippumaara;

  public PutTapahtumaModel() {
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  public LocalDateTime getPaivamaara() {
    return paivamaara;
  }

  public void setPaivamaara(LocalDateTime paivamaara) {
    this.paivamaara = paivamaara;
  }

  public String getKuvaus() {
    return kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  public Long getTapahtumapaikka_id() {
    return tapahtumapaikka_id;
  }

  public void setTapahtumapaikka_id(Long tapahtumapaikka_id) {
    this.tapahtumapaikka_id = tapahtumapaikka_id;
  }

  public int getLippumaara() {
    return lippumaara;
  }

  public void setLippumaara(int lippumaara) {
    this.lippumaara = lippumaara;
  }

  @Override
  public String toString() {
    return "PutTapahtumaModel [nimi=" + nimi + ", paivamaara=" + paivamaara + ", kuvaus=" + kuvaus
        + ", tapahtumapaikka_id=" + tapahtumapaikka_id + ", lippumaara=" + lippumaara + "]";
  }

}
