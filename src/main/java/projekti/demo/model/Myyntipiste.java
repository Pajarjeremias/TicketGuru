package projekti.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "myyntipisteet")
public class Myyntipiste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myyntipiste_id")
    private Integer myyntipiste_id;

    @Column(name = "nimi", nullable = false, length = 100)
    private String nimi;

    @Column(name = "katuosoite", nullable = false, length = 100)
    private String katuosoite;

    @ManyToOne
    @JoinColumn(name = "postinumero", nullable = false)
    private Postitoimipaikka postitoimipaikka;

    @OneToMany(mappedBy = "myyntipiste")
    private List<Myynti> myynnit;

    // Constructorit
    public Myyntipiste() { }

    public Myyntipiste(String nimi, String katuosoite, Postitoimipaikka postitoimipaikka) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postitoimipaikka = postitoimipaikka;
    }

    public Myyntipiste(String nimi, String katuosoite, Postitoimipaikka postitoimipaikka, List<Myynti> myynnit) {
        this.nimi = nimi;
        this.katuosoite = katuosoite;
        this.postitoimipaikka = postitoimipaikka;
        this.myynnit = myynnit;
    }

    // Getterit ja setterit
    public Integer getMyyntipisteId() {
        return myyntipiste_id;
    }

    public void setMyyntipisteId(Integer myyntipisteId) {
        this.myyntipiste_id = myyntipisteId;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public Postitoimipaikka getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public void setPostitoimipaikka(Postitoimipaikka postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

    public List<Myynti> getMyynnit() {
        return myynnit;
    }

    public void setMyynnit(List<Myynti> myynnit) {
        this.myynnit = myynnit;
    }

    // To string
    @Override
    public String toString() {
        return "Myyntipiste [myyntipiste_id=" + myyntipiste_id + ", nimi=" + nimi + ", katuosoite=" + katuosoite
                + ", postitoimipaikka=" + postitoimipaikka + ", myynnit=" + myynnit + "]";
    }
}