package projekti.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class PutLippuModel {

    private Long tapahtuman_lipputyypit_id;
    private Float hinta;
    private Long tila_id;
    private Float myynti_id;

    public PutLippuModel(Long tapahtuman_lipputyypit_id, Long tila_id) {
        this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
        this.tila_id = tila_id;
    }
    public PutLippuModel(Long tapahtuman_lipputyypit_id, Float hinta) {
        this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
        this.hinta = hinta;
    }
    public PutLippuModel(Long tapahtuman_lipputyypit_id, Float hinta, Long tila_id) {
        this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
        this.hinta = hinta;
        this.tila_id = tila_id;
    }

    public PutLippuModel(Long tapahtuman_lipputyypit_id, Float hinta, Long tila_id, Float myynti_id) {
        this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
        this.hinta = hinta;
        this.tila_id = tila_id;
        this.myynti_id = myynti_id;
    }
    
    public Long getTapahtuman_lipputyypit_id() {
        return tapahtuman_lipputyypit_id;
    }
    public void setTapahtuman_lipputyypit_id(Long tapahtuman_lipputyypit_id) {
        this.tapahtuman_lipputyypit_id = tapahtuman_lipputyypit_id;
    }
    public Float getHinta() {
        return hinta;
    }
    public void setHinta(Float hinta) {
        this.hinta = hinta;
    }
    public Long getTila_id() {
        return tila_id;
    }
    public void setTila_id(Long tila_id) {
        this.tila_id = tila_id;
    }
    public Float getMyynti_id() {
        return myynti_id;
    }
    public void setMyynti_id(Float myynti_id) {
        this.myynti_id = myynti_id;
    }

    


}
