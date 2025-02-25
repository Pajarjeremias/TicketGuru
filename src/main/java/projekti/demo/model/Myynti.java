package projekti.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "myynnit")
public class Myynti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myynti_id")
    private Integer myyntiId;

    @ManyToOne
    @JoinColumn(name = "lippu_id", nullable = false)
    private Lippu lippu;

    @ManyToOne
    @JoinColumn(name = "asiakas_id", nullable = false)
    private Kayttaja asiakas;

    @Column(name = "myyntipaiva", nullable = false)
    private LocalDate myyntipaiva;

    @ManyToOne
    @JoinColumn(name = "myyntipiste_id", nullable = false)
    private Myyntipiste myyntipiste;

    @Column(name = "hinta", nullable = false, precision = 10, scale = 2)
    private BigDecimal hinta;

    // Getterit ja setterit
    public Integer getMyyntiId() {
        return myyntiId;
    }

    public void setMyyntiId(Integer myyntiId) {
        this.myyntiId = myyntiId;
    }

    public Lippu getLippu() {
        return lippu;
    }

    public void setLippu(Lippu lippu) {
        this.lippu = lippu;
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

    public BigDecimal getHinta() {
        return hinta;
    }

    public void setHinta(BigDecimal hinta) {
        this.hinta = hinta;
    }
}