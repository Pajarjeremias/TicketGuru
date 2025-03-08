package projekti.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="Liput")
public class Lippu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lippu_id;

    @NotNull(message = "Tapahtuman lipputyyppi on pakollinen")
    @ManyToOne
    @JoinColumn(name = "tapahtuma_lipputyyppi_id")
    private Tapahtuman_lipputyyppi tapahtuman_lipputyyppi;

    @NotNull(message = "Pakollinen")
    private Float hinta;

    @NotNull(message = "Tila on pakollinen")
    @ManyToOne
    @JoinColumn(name = "tila_id")
    private Tila tila;

    @ManyToOne
    @JoinColumn(name = "kayttaja_id")
    private Kayttaja tarkastanut;

    private LocalDateTime tarkastus_pvm;

    @NotNull(message = "Myynti on pakollinen")
    @ManyToOne
    @JoinColumn(name = "myynti_id")
    private Myynti myynti;

    public Lippu() { }

    public Lippu(
            @NotNull(message = "Tapahtuman lipputyyppi on pakollinen") Tapahtuman_lipputyyppi tapahtuman_lipputyyppi,
            @NotNull(message = "Pakollinen") Float hinta, @NotNull(message = "Tila on pakollinen") Tila tila,
            @NotNull(message = "Myynti on pakollinen") Myynti myynti) {
        this.tapahtuman_lipputyyppi = tapahtuman_lipputyyppi;
        this.hinta = hinta;
        this.tila = tila;
        this.myynti = myynti;
    }

    public Long getLippu_id() {
        return lippu_id;
    }

    public void setLippu_id(Long lippu_id) {
        this.lippu_id = lippu_id;
    }

    public Tapahtuman_lipputyyppi getTapahtuman_lipputyyppi() {
        return tapahtuman_lipputyyppi;
    }

    public void setTapahtuman_lipputyyppi(Tapahtuman_lipputyyppi tapahtuman_lipputyyppi) {
        this.tapahtuman_lipputyyppi = tapahtuman_lipputyyppi;
    }

    public Float getHinta() {
        return hinta;
    }

    public void setHinta(Float hinta) {
        this.hinta = hinta;
    }

    public Tila getTila() {
        return tila;
    }

    public void setTila(Tila tila) {
        this.tila = tila;
    }

    public Kayttaja getTarkastanut() {
        return tarkastanut;
    }

    public void setTarkastanut(Kayttaja tarkastanut) {
        this.tarkastanut = tarkastanut;
    }

    public LocalDateTime getTarkastus_pvm() {
        return tarkastus_pvm;
    }

    public void setTarkastus_pvm(LocalDateTime tarkastus_pvm) {
        this.tarkastus_pvm = tarkastus_pvm;
    }

    public Myynti getMyynti() {
        return myynti;
    }

    public void setMyynti(Myynti myynti) {
        this.myynti = myynti;
    }

    



}

