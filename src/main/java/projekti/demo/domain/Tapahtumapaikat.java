package projekti.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;

@Entity
public class Tapahtumapaikat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tapahtumapaikka_id;

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
    private Postitoimipaikat postinumero;

    @Column(name = "kapasitettti")
    private int maksimi_osallistujat;

    @JsonIgnoreProperties("tapahtumapaikat")
    @ManyToOne
    @JoinColumn(name = "tapahtumat_id")
    private Tapahtumat tapahtumat;



    public Tapahtumapaikat() {
    }

    public Tapahtumapaikat(String nimi) {
        this.nimi = nimi;
    }

    public Tapahtumapaikat(String nimi, String katuosoite) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
    }

    public Tapahtumapaikat(String nimi, String katuosoite, Postitoimipaikat postinumero) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
    }

        public Tapahtumapaikat(String nimi,String katuosoite, Postitoimipaikat postinumero,
            int maksimi_osallistujat, Tapahtumat tapahtumat) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
        this.maksimi_osallistujat = maksimi_osallistujat;
        this.tapahtumat = tapahtumat;
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

    public Postitoimipaikat getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postitoimipaikat postinumero) {
        this.postinumero = postinumero;
    }

    public int getMaksimi_osallistujat() {
        return maksimi_osallistujat;
    }

    public void setMaksimi_osallistujat(int maksimi_osallistujat) {
        this.maksimi_osallistujat = maksimi_osallistujat;
    }

    public Tapahtumat getTapahtumat() {
        return tapahtumat;
    }

    public void setTapahtumat(Tapahtumat tapahtumat) {
        this.tapahtumat = tapahtumat;
    }

    public int getTapahtumapaikka_id() {
        return tapahtumapaikka_id;
    }

    @Override
    public String toString() {
        return "Tapahtumapaikat [tapahtumapaikka_id=" + tapahtumapaikka_id + ", nimi=" + nimi + ", katuosoite="
                + katuosoite + ", maksimi_osallistujat=" + maksimi_osallistujat + "]";
    }

}
