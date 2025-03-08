package projekti.demo.model;

import jakarta.persistence.*;
//
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "myynnit")
public class Myynti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myynti_id")
    private Integer myynti_id;

    @OneToMany(mappedBy = "lippu_id")
    @JsonIgnore
    private List<Lippu> liput;

    @ManyToOne
    @JoinColumn(name = "kayttaja_id")
    private Kayttaja asiakas;

    @Column(name = "myyntipaiva", nullable = false)
    private LocalDate myyntipaiva;

    @ManyToOne
    @JoinColumn(name = "myyntipiste_id", nullable = false)
    private Myyntipiste myyntipiste;

    @ManyToOne
    @JoinColumn(name = "maksutapa_id", nullable = false)
    @NotNull
    private Maksutapa maksutapa;

    // Constructorit
    public Myynti() { }

    public Myynti(List<Lippu> liput, Kayttaja asiakas, LocalDate myyntipaiva, Myyntipiste myyntipiste, Maksutapa maksutapa) {
        this.liput = liput;
        this.asiakas = asiakas;
        this.myyntipaiva = myyntipaiva;
        this.myyntipiste = myyntipiste;
        this.maksutapa = maksutapa;
    }

    public Myynti(LocalDate myyntipaiva, Myyntipiste myyntipiste, Maksutapa maksutapa) {
        this.myyntipaiva = myyntipaiva;
        this.myyntipiste = myyntipiste;
        this.maksutapa = maksutapa;
    }

    // Getterit ja setterit
    public Integer getMyynti_id() {
        return myynti_id;
    }

    public void setMyynti_id(Integer myynti_id) {
        this.myynti_id = myynti_id;
    }

    public List<Lippu> getLiput() {
        return liput;
    }

    public void setLiput(List<Lippu> liput) {
        this.liput = liput;
    }

    public Kayttaja getAsiakas() {
        return asiakas;
    }

    public void setAsiakas(Kayttaja asiakas) {
        this.asiakas = asiakas;
    }

    public LocalDate getMyyntipaiva() {
        return myyntipaiva;
    }

    public void setMyyntipaiva(LocalDate myyntipaiva) {
        this.myyntipaiva = myyntipaiva;
    }

    public Myyntipiste getMyyntipiste() {
        return myyntipiste;
    }

    public void setMyyntipiste(Myyntipiste myyntipiste) {
        this.myyntipiste = myyntipiste;
    }

    public Maksutapa getMaksutapa() {
        return maksutapa;
    }

    public void setMaksutapa(Maksutapa maksutapa) {
        this.maksutapa = maksutapa;
    }

    // To string
    @Override
    public String toString() {
        return "Myynti [myynti_id=" + myynti_id + ", liput=" + liput + ", asiakas=" + asiakas + ", myyntipaiva="
                + myyntipaiva + ", myyntipiste=" + myyntipiste + ", maksutapa=" + maksutapa + "]";
    }

}