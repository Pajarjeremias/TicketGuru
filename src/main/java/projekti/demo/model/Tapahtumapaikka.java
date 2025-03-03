package projekti.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Tapahtumapaikat")
public class Tapahtumapaikka {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tapahtumapaikka_id;

    @Column(name = "Nimi")
    @NotEmpty(message = "paikalla täytyy olla nimi")
    @Size(min = 1, max = 200, message = "Täytyy olla 2-200 merkkiä")
    private String nimi;

    @Column(name = "Katuosoite")
    @Size(max = 30, message = "täytyy mahtua 30 merkkiin")
    private String katuosoite;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "postinumero")
    private Postitoimipaikka postinumero;

    @Column(name = "kapasitettti")
    private int maksimi_osallistujat;

    @JsonIgnoreProperties("tapahtumapaikat")
    @ManyToOne
    @JoinColumn(name = "tapahtumat_id")
    private Tapahtuma tapahtuma;

    public Tapahtumapaikka() {
    }

    public Tapahtumapaikka(String nimi) {
        this.nimi = nimi;
    }

    public Tapahtumapaikka(String nimi, String katuosoite) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
    }

    public Tapahtumapaikka(String nimi, String katuosoite, Postitoimipaikka postinumero) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
    }

    public Tapahtumapaikka(String nimi, String katuosoite, Postitoimipaikka postinumero,
            int maksimi_osallistujat, Tapahtuma tapahtuma) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.maksimi_osallistujat = maksimi_osallistujat;
        this.tapahtuma = tapahtuma;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
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

    public int getMaksimi_osallistujat() {
        return maksimi_osallistujat;
    }

    public void setMaksimi_osallistujat(int maksimi_osallistujat) {
        this.maksimi_osallistujat = maksimi_osallistujat;
    }

    public Tapahtuma getTapahtumat() {
        return tapahtuma;
    }

    public void setTapahtumat(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    public long getTapahtumapaikka_id() {
        return tapahtumapaikka_id;
    }

    @Override
    public String toString() {
        return "Tapahtumapaikat [tapahtumapaikka_id=" + tapahtumapaikka_id + ", nimi=" + nimi + ", katuosoite="
                + katuosoite + ", maksimi_osallistujat=" + maksimi_osallistujat + "]";
    }

}
