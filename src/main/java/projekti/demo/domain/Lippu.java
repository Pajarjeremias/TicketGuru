package projekti.demo.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
@Table(name="liput")
public class Lippu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lippu_id", nullable = false, updatable = false)
    private Long lippu_id;

    @Column(name = "hinta")
    private Long hinta;

    @Column(name = "tarkistus_pvm")
    private Date tarkistus_pvm;

    @ManyToOne
    @JoinColumn(name="tapahtuma_id")
    private Tapahtuma tapahtuma;

    @ManyToOne
    @JoinColumn(name="tila_id")
    private Tila tila;

    @ManyToOne
    @JoinColumn(name="lipputyyppi_id")
    private Lipputyyppi lipputyyppi;

    @ManyToOne
    @JoinColumn(name="kayttaja_id")
    private Kayttaja tarkastaja;

    public Lippu() {
        super();
    }

    public Lippu(Long hinta, Date tarkistus_pvm, Tapahtuma tapahtuma, Tila tila, Lipputyyppi lipputyyppi,
            Kayttaja tarkastaja) {
        this.hinta = hinta;
        this.tarkistus_pvm = tarkistus_pvm;
        this.tapahtuma = tapahtuma;
        this.tila = tila;
        this.lipputyyppi = lipputyyppi;
        this.tarkastaja = tarkastaja;
    }

    public Long getHinta() {
        return hinta;
    }

    public void setHinta(Long hinta) {
        this.hinta = hinta;
    }

    public Date getTarkistus_pvm() {
        return tarkistus_pvm;
    }

    public void setTarkistus_pvm(Date tarkistus_pvm) {
        this.tarkistus_pvm = tarkistus_pvm;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }

    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    public Tila getTila() {
        return tila;
    }

    public void setTila(Tila tila) {
        this.tila = tila;
    }

    public Lipputyyppi getLipputyyppi() {
        return lipputyyppi;
    }

    public void setLipputyyppi(Lipputyyppi lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }

    public Kayttaja getTarkastaja() {
        return tarkastaja;
    }

    public void setTarkastaja(Kayttaja tarkastaja) {
        this.tarkastaja = tarkastaja;
    }

    @Override
    public String toString() {
        return "Lippu [lippu_id=" + lippu_id + ", hinta=" + hinta + ", tarkistus_pvm=" + tarkistus_pvm + ", tapahtuma="
                + tapahtuma + ", tila=" + tila + ", lipputyyppi=" + lipputyyppi + ", tarkastaja=" + tarkastaja + "]";
    }

    
}

