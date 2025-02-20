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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Tapahtumat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tapahtuma_id;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtumat")
    private List<Tapahtumapaikat> tapahtumapaikat;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Jarjestaja_id")
    private Jarjestajat jarjestajat;

    // constructorit
    public Tapahtumat() {
    }

    public Tapahtumat(String nimi, LocalDateTime paivamaara, String kuvaus) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.kuvaus = kuvaus;
    }

    public Tapahtumat(String nimi, LocalDateTime paivamaara, String kuvaus,
            List<Tapahtumapaikat> tapahtumapaikat, Jarjestajat jarjestajat) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
        this.kuvaus = kuvaus;
        this.tapahtumapaikat = tapahtumapaikat;
        this.jarjestajat = jarjestajat;
    }

    // Getterit ja setterit
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

    public List<Tapahtumapaikat> getTapahtumapaikat() {
        return tapahtumapaikat;
    }

    public void setTapahtumapaikat(List<Tapahtumapaikat> tapahtumapaikat) {
        this.tapahtumapaikat = tapahtumapaikat;
    }

    public Jarjestajat getJarjestajat() {
        return jarjestajat;
    }

    public void setJarjestajat(Jarjestajat jarjestajat) {
        this.jarjestajat = jarjestajat;
    }

    public int getTapahtuma_id() {
        return tapahtuma_id;
    }

    @Override
    public String toString() {
        if (this.tapahtumapaikat != null) {
            return "Tapahtumat [Tapahtuma_id=" + tapahtuma_id + ", Nimi=" + nimi + ", Paivamaara=" + paivamaara
                    + ", Kuvaus=" + kuvaus + ", tapahtumapaikat=" + tapahtumapaikat + "]";
        } else {
            return "Tapahtumat [Tapahtuma_id=" + tapahtuma_id + ", Nimi=" + nimi + ", Paivamaara=" + paivamaara
                    + ", Kuvaus=" + kuvaus + "]";
        }

    }

}
