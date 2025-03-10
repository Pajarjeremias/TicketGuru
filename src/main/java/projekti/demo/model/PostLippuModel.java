package projekti.demo.model;

public class PostLippuModel {

  private Long tapahtuman_lipputyypit_id, myynti_id;
  private Float hinta;

  public Long getTapahtuman_lipputyypit_id() {
    return tapahtuman_lipputyypit_id;
  }
  public void setTapahtuman_lipputyypit_id(Long tapahtuman_lipputyypit_id) {
    this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
  }
  public Long getMyynti_id() {
    return myynti_id;
  }
  public void setMyynti_id(Long myynti_id) {
    this.myynti_id = myynti_id;
  }
  public Float getHinta() {
    return hinta;
  }
  public void setHinta(Float hinta) {
    this.hinta = hinta;
  }
  public PostLippuModel(Long myynti_id, Long tapahtuman_lipputyypit_id, Float hinta) {
    this.myynti_id = myynti_id;
    this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
    this.hinta = hinta;
  }

  public PostLippuModel(Long myynti_id, Long tapahtuman_lipputyypit_id) {
    this.myynti_id = myynti_id;
    this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
  }
  public PostLippuModel() {
  }

}