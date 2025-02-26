package projekti.demo.model;

import java.util.List;

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
@Table(name = "Tilat")
public class Tila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tila_id")
    private Long tila_id;

    @NotEmpty(message = "Tilan nimi on pakollinen")
    @Size(max = 30, message = "Maksimipituus 30 merkkiä")
    @Column(name = "tila", nullable = false, unique = true)
    private String tila;

    // halutaanko tänne myös lista lipuista? Eli voi hakea mitä lippuja on esim.
    // Tarkastettu tilassa?
    // Lisätty 26.2.2025, lisätty samalla getterit ja setterit

    @OneToMany(mappedBy = "tila")
    private List<Lippu> liput;

    // Constructorit
    public Tila() {
        super();
    }

    public Tila(String tila) {
        super();
        this.tila = tila;
    }

    public Tila(String tila, List<Lippu> liput) {
        this.tila = tila;
        this.liput = liput;
    }

    // Getterit ja setterit
    public Long getTila_id() {
        return tila_id;
    }

    public void setTila_id(Long tila_id) {
        this.tila_id = tila_id;
    }

    public List<Lippu> getLiput() {
        return liput;
    }

    public void setLiput(List<Lippu> liput) {
        this.liput = liput;
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }

    @Override
    public String toString() {
        return "Tila [tila_id=" + tila_id + ", tila=" + tila + ", liput=" + liput + "]";
    }

}
