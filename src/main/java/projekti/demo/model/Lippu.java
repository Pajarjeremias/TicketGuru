package projekti.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name="Liput")
public class Lippu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lippu_id")
    private Long lippu_id;

    // TODO: Tarkista, kun tapahtuma_liiputyyppi -entity olemassa
    // TODO: Muista tarkistaa myös getterit ja setterit + toString
    /*
    @ManyToOne
    @JoinColumn(name="tapahtuma_lipputyyppi_id")
    @NotEmpty(message = "Tapahtuma_lipputyyppi on pakollinen")
    private Tapahtuman_lipputyypit tapahtuman_lipputyypit;
    */

    @Column(name = "hinta")
    @NotEmpty
    private Float hinta;

    @ManyToOne
    @JoinColumn(name="tila_id")
    @NotEmpty(message = "Tila on pakollinen")
    private Tila tila;

    @ManyToOne
    @JoinColumn(name="kayttaja_id")
    private Kayttaja tarkastaja;

    @Column(name = "tarkistus_pvm")
    private LocalDateTime tarkistus_pvm;

    @ManyToOne
    @JoinColumn(name = "myynti_id", nullable = false)
    @NotEmpty(message = "Myynti on pakollinen")
    private Myynti myynti;

    public Lippu() {
        super();
    }

    // TODO: Luo uudestaan, kun tapahtuma_liiputyyppi -entity olemassa
    /*
    public Lippu(Float hinta, LocalDateTime tarkistus_pvm, Tapahtuma tapahtuma, Tila tila, Lipputyyppi lipputyyppi,
        Kayttaja tarkastaja) {
        this.hinta = hinta;
        this.tarkistus_pvm = tarkistus_pvm;
        this.tapahtuma = tapahtuma;
        this.tila = tila;
        this.lipputyyppi = lipputyyppi;
        this.tarkastaja = tarkastaja;
    }
    */

    public Float getHinta() {
        return hinta;
    }

    public void setHinta(Float hinta) {
        this.hinta = hinta;
    }

    public LocalDateTime getTarkistus_pvm() {
        return tarkistus_pvm;
    }

    public void setTarkistus_pvm(LocalDateTime tarkistus_pvm) {
        this.tarkistus_pvm = tarkistus_pvm;
    }

    public Tila getTila() {
        return tila;
    }

    public void setTila(Tila tila) {
        this.tila = tila;
    }

    public Kayttaja getTarkastaja() {
        return tarkastaja;
    }

    public void setTarkastaja(Kayttaja tarkastaja) {
        this.tarkastaja = tarkastaja;
    }
    
}

