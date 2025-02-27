package projekti.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="Lipputyypit")

public class Lipputyyppi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lipputyyppi_id")
    private Long lipputyyppi_id;

    @OneToMany(mappedBy = "lipputyyppi", cascade = CascadeType.ALL)
    private List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit;
    

    @NotEmpty(message = "Lipputyypin nimi on pakollinen")
    @Size(max = 30, message = "Maksimipituus 30 merkkiä")
    @Column(name = "lipputyyppi", nullable = false, unique = true)
    private String lipputyyppi;

    public Lipputyyppi() {
        super();
    }

    public Lipputyyppi(
            @NotEmpty(message = "Lipputyypin nimi on pakollinen") @Size(max = 30, message = "Maksimipituus 30 merkkiä") String lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }

    public Lipputyyppi(List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit,
            @NotEmpty(message = "Lipputyypin nimi on pakollinen") @Size(max = 30, message = "Maksimipituus 30 merkkiä") String lipputyyppi) {
        this.tapahtuman_lipputyypit = tapahtuman_lipputyypit;
        this.lipputyyppi = lipputyyppi;
    }

    public Long getLipputyyppi_id() {
        return lipputyyppi_id;
    }

    public void setLipputyyppi_id(Long lipputyyppi_id) {
        this.lipputyyppi_id = lipputyyppi_id;
    }

    public List<Tapahtuman_lipputyyppi> getTapahtuman_lipputyypit() {
        return tapahtuman_lipputyypit;
    }

    public void setTapahtuman_lipputyypit(List<Tapahtuman_lipputyyppi> tapahtuman_lipputyypit) {
        this.tapahtuman_lipputyypit = tapahtuman_lipputyypit;
    }

    public String getLipputyyppi() {
        return lipputyyppi;
    }

    public void setLipputyyppi(String lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }

    @Override
    public String toString() {
        return "Lipputyyppi [lipputyyppi_id=" + lipputyyppi_id + ", tapahtuman_lipputyypit=" + tapahtuman_lipputyypit
                + ", lipputyyppi=" + lipputyyppi + "]";
    }

}