package projekti.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="lipputyypit")

public class Lipputyyppi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lipputyyppi_id", nullable = false, updatable = false)
    private Long lipputyyppi_id;


    @Column(name = "lipputyyppi", nullable = false, unique = true)
    private String lipputyyppi;

    public Lipputyyppi() {
        super();
    }


    public Lipputyyppi(String lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }


    public Long getLipputyyppi_id() {
        return lipputyyppi_id;
    }


    public void setLipputyyppi_id(Long lipputyyppi_id) {
        this.lipputyyppi_id = lipputyyppi_id;
    }


    public String getLipputyyppi() {
        return lipputyyppi;
    }


    public void setLipputyyppi(String lipputyyppi) {
        this.lipputyyppi = lipputyyppi;
    }


    @Override
    public String toString() {
        return "Lipputyyppi [lipputyyppi_id=" + lipputyyppi_id + ", lipputyyppi=" + lipputyyppi + "]";
    }

    

    
}




