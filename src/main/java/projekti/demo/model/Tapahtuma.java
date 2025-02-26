package projekti.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Tapahtumat")
public class Tapahtuma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tapahtuma_id;

    @Column(name = "Nimi")
    @NotEmpty(message = "Tapahtumalla täytyy olla nimi")
    @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä")
    private String nimi;

    @Column(name = "PvmJaKellonaika")
    @NotEmpty(message = "Tapahtumalla täytyy olla päiväys ja kellonaika")
    private LocalDateTime paivamaara;

    @Column(name = "Kuvaus")
    @Size(min = 2, max = 2500, message = "Pituuden täytyy olla 2-2500 merkkiä")
    private String kuvaus;

    @JsonIgnoreProperties("tapahtumat")
    @OneToMany(mappedBy = "tapahtumapaikka_id", cascade = CascadeType.ALL)
    private List<Tapahtumapaikka> tapahtumapaikka;

    @OneToMany(mappedBy = "tapahtuma", cascade = CascadeType.ALL)
    private List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "jarjestaja_id")
    private Jarjestaja jarjestaja;

    @NotEmpty(message = "Lippumäärä on pakollinen")
    @Min(1)
    private int lippumaara;

    public Tapahtuma() { }

    public Tapahtuma(
            @NotEmpty(message = "Tapahtumalla täytyy olla nimi") @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä") String nimi,
            @NotEmpty(message = "Tapahtumalla täytyy olla päiväys ja kellonaika") LocalDateTime paivamaara,
            @Size(min = 2, max = 2500, message = "Pituuden täytyy olla 2-2500 merkkiä") String kuvaus,
            @NotEmpty(message = "Lippumäärä on pakollinen") @Min(1) int lippumaara) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.kuvaus = kuvaus;
        this.lippumaara = lippumaara;
    }

    public Tapahtuma(
            @NotEmpty(message = "Tapahtumalla täytyy olla nimi") @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä") String nimi,
            @NotEmpty(message = "Tapahtumalla täytyy olla päiväys ja kellonaika") LocalDateTime paivamaara,
            @Size(min = 2, max = 2500, message = "Pituuden täytyy olla 2-2500 merkkiä") String kuvaus,
            List<Tapahtumapaikka> tapahtumapaikka, List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit,
            Jarjestaja jarjestaja, @NotEmpty(message = "Lippumäärä on pakollinen") @Min(1) int lippumaara) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.kuvaus = kuvaus;
        this.tapahtumapaikka = tapahtumapaikka;
        this.tapahtuman_lipputyypit = tapahtuman_lipputyypit;
        this.jarjestaja = jarjestaja;
        this.lippumaara = lippumaara;
    }

    public Long getTapahtuma_id() {
        return tapahtuma_id;
    }

    public void setTapahtuma_id(Long tapahtuma_id) {
        this.tapahtuma_id = tapahtuma_id;
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

    public List<Tapahtumapaikka> getTapahtumapaikka() {
        return tapahtumapaikka;
    }

    public void setTapahtumapaikka(List<Tapahtumapaikka> tapahtumapaikka) {
        this.tapahtumapaikka = tapahtumapaikka;
    }

    public List<Tapahtuman_lipputyyppi> getTapahtuman_lipputyypit() {
        return tapahtuman_lipputyypit;
    }

    public void setTapahtuman_lipputyypit(List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit) {
        this.tapahtuman_lipputyypit = tapahtuman_lipputyypit;
    }

    public Jarjestaja getJarjestaja() {
        return jarjestaja;
    }

    public void setJarjestaja(Jarjestaja jarjestaja) {
        this.jarjestaja = jarjestaja;
    }

    public int getLippumaara() {
        return lippumaara;
    }

    public void setLippumaara(int lippumaara) {
        this.lippumaara = lippumaara;
    }

    @Override
    public String toString() {
        return "Tapahtuma [tapahtuma_id=" + tapahtuma_id + ", nimi=" + nimi + ", paivamaara=" + paivamaara + ", kuvaus="
                + kuvaus + ", tapahtumapaikka=" + tapahtumapaikka + ", tapahtuman_lipputyypit=" + tapahtuman_lipputyypit
                + ", jarjestaja=" + jarjestaja + ", lippumaara=" + lippumaara + "]";
    }

}
