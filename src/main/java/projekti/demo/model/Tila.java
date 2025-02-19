package projekti.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="tilat")
public class Tila {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tila_id", nullable = false, updatable = false)
    private Long id;


    @NotEmpty(message = "Tilan nimi on pakollinen")
    @Size(max = 30, message = "Maksimipituus 30 merkkiä")
    @Column(name = "tila", nullable = false, unique = true)
    
    private String tila;

    public Tila() {
        super();
    }

    public Tila(String tila) {
        super();
        this.tila = tila;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }

    @Override
    public String toString() {
        return "Tila [id=" + id + ", tila=" + tila + "]";
    }

    
}







    



