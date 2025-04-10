package projekti.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Tapahtuman_lipputyypit")
public class Tapahtuman_lipputyyppi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tapahtuma_lipputyyppi_id;

    @ManyToOne
    @JoinColumn(name = "tapahtuma_id", nullable = false)
    @NotNull
    @JsonIgnore
    private Tapahtuma tapahtuma;

    @ManyToOne
    @JoinColumn(name = "lipputyyppi_id", nullable = false)
    @NotNull
    private Lipputyyppi lipputyyppi;

    @OneToMany(mappedBy = "tapahtuman_lipputyyppi")
    @JsonIgnore
    private List<Lippu> liput;

    @Column(name = "hinta")
    @NotNull
    private Float hinta;

    public Tapahtuman_lipputyyppi() {
    }

    public Tapahtuman_lipputyyppi(@NotNull Tapahtuma tapahtuma, @NotNull Lipputyyppi lipputyyppi,
            @NotNull Float hinta) {
        this.tapahtuma = tapahtuma;
        this.lipputyyppi = lipputyyppi;
        this.hinta = hinta;
    }

    public Tapahtuman_lipputyyppi(Tapahtuma tapahtuma, Lipputyyppi lipputyyppi, List<Lippu> liput,
            @NotNull Float hinta) {
        this.tapahtuma = tapahtuma;
        this.lipputyyppi = lipputyyppi;
        this.liput = liput;
        this.hinta = hinta;
    }

    public Long getTapahtuma_lipputyyppi_id() {
        return tapahtuma_lipputyyppi_id;
    }

    public void setTapahtuma_lipputyyppi_id(Long tapahtuma_lipputyyppi_id) {
        this.tapahtuma_lipputyyppi_id = tapahtuma_lipputyyppi_id;
    }

    public Tapahtuma getTapahtuma() {
        return tapahtuma;
    }

    public void setTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtuma = tapahtuma;
    }

    public Lipputyyppi getLipputyyppi() {
        return lipputyyppi;
    }

    public void setLipputyyppi(Lipputyyppi lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }

    public List<Lippu> getLiput() {
        return liput;
    }

    public void setLiput(List<Lippu> liput) {
        this.liput = liput;
    }

    public Float getHinta() {
        return hinta;
    }

    public void setHinta(Float hinta) {
        this.hinta = hinta;
    }
    @Override
    public String toString() {
        return "Tapahtuman_lipputyyppi [tapahtuma_lipputyyppi_id=" + tapahtuma_lipputyyppi_id 
                + ", tapahtuma=" + (tapahtuma != null ? tapahtuma.getNimi() : "null")
                + ", lipputyyppi=" + (lipputyyppi != null ? lipputyyppi.getLipputyyppi_id() : "null") 
                + ", hinta=" + hinta + "]";
    }
}
