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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Tapahtumat")
public class Tapahtuma {

    private String url; // Luotu SetUrl RestControlleria varten

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tapahtuma_id;

    @Column(name = "Nimi")
    @NotEmpty(message = "Tapahtumalla täytyy olla nimi")
    @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä")
    private String nimi;

    @Column(name = "PvmJaKellonaika")
    @Future(message = "Päivämäärän ja kellonajan on oltava tulevaisuudessa.")
    @NotNull(message = "Päivämäärä ja kellonaika täytyy olla annettu ja sen on oltava tulevaisuudessa.")
    private LocalDateTime paivamaara;

    @Column(name = "Kuvaus")
    @Size(min = 2, max = 2500, message = "Pituuden täytyy olla 2-2500 merkkiä")
    private String kuvaus;

    @JsonIgnoreProperties("tapahtumat")
    @ManyToOne
    @JoinColumn(name = "tapahtumapaikka_id")
    private Tapahtumapaikka tapahtumapaikka;

    @OneToMany(mappedBy = "tapahtuma", cascade = CascadeType.ALL)
    // pitäisikö olla linkitettu tapahtuman lipputyyppiin? @OneToMany(mappedBy = "tapahtuma_lipputyyppi_id", cascade = CascadeType.ALL)
    private List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit;

    @ManyToOne
    @JoinColumn(name = "jarjestaja_id")
    private Jarjestaja jarjestaja;

    @Min(value = 1, message = "Lippumäärän täytyy olla positiivinen kokonaisluku")
    private int lippumaara;

    public Tapahtuma() { }

    public Tapahtuma(
            @NotEmpty(message = "Tapahtumalla täytyy olla nimi") @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä") String nimi,
            @Future(message = "Tapahtumalla täytyy olla päiväys ja kellonaika") @NotNull LocalDateTime paivamaara,
            @Min(value = 1, message = "Lippumäärän täytyy olla positiivinen kokonaisluku") int lippumaara) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.lippumaara = lippumaara;
    }

    public Tapahtuma(
            @NotEmpty(message = "Tapahtumalla täytyy olla nimi") @Size(min = 2, max = 200, message = "Täytyy olla 2-200 merkkiä") String nimi,
            @Future(message = "Tapahtumalla täytyy olla päiväys ja kellonaika") @NotNull LocalDateTime paivamaara,
            @Size(min = 2, max = 2500, message = "Pituuden täytyy olla 2-2500 merkkiä") String kuvaus,
            @Min(value = 1, message = "Lippumäärän täytyy olla positiivinen kokonaisluku") int lippumaara) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.kuvaus = kuvaus;
        this.lippumaara = lippumaara;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Tapahtumapaikka getTapahtumapaikka() {
        return tapahtumapaikka;
    }

    public void setTapahtumapaikka(Tapahtumapaikka tapahtumapaikka) {
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
        return "Tapahtuma [url=" + url + ", tapahtuma_id=" + tapahtuma_id + ", nimi=" + nimi + ", paivamaara="
                + paivamaara + ", kuvaus=" + kuvaus + ", tapahtumapaikka=" + tapahtumapaikka
                + ", tapahtuman_lipputyypit=" + tapahtuman_lipputyypit + ", jarjestaja=" + jarjestaja + ", lippumaara="
                + lippumaara + "]";
    }

}
