package projekti.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "Tilat")
public class Tila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tila_id")
    private Long tila_id;

    @Column(name = "myyty", nullable = false)
    private boolean myyty = false;

    @Column(name = "myymatta", nullable = false)
    private boolean myymatta = false;

    @Column(name = "tarkastettu", nullable = false)
    private boolean tarkastettu = false;

    @Column(name = "peruttu", nullable = false)
    private boolean peruttu = false;

    // halutaanko tänne myös lista lipuista? Eli voi hakea mitä lippuja on esim.
    // Tarkastettu tilassa?
    // Lisätty 26.2.2025, lisätty samalla getterit ja setterit

    @OneToMany(mappedBy = "tila")
    @JsonIgnore
    private List<Lippu> liput;

    // Constructorit
    public Tila() {
        this.myyty = false;
        this.myymatta = false;
        this.tarkastettu = false;
        this.peruttu = false;
    }

    public Tila(boolean myyty, boolean myymatta, boolean tarkastettu, boolean peruttu, List<Lippu> liput) {
        this.myyty = myyty;
        this.myymatta = myymatta;
        this.tarkastettu = tarkastettu;
        this.peruttu = peruttu;
        this.liput = liput;
    }
    public Tila(String tila) {
        this.myyty = tila.equals("myyty");
        this.myymatta = tila.equals("myymatta");
        this.tarkastettu = tila.equals("tarkastettu");
        this.peruttu = tila.equals("peruttu");
    }

    public Long getTila_id() {
        return tila_id;
    }

    public void setTila_id(Long tila_id) {
        this.tila_id = tila_id;
    }

    public boolean isMyyty() {
        return myyty;
    }

    public void setMyyty(boolean myyty) {
        this.myyty = myyty;
    }

    public boolean isMyymatta() {
        return myymatta;
    }

    public void setMyymatta(boolean myymatta) {
        this.myymatta = myymatta;
    }

    public boolean isTarkastettu() {
        return tarkastettu;
    }

    public void setTarkastettu(boolean tarkastettu) {
        this.tarkastettu = tarkastettu;
    }

    public boolean isPeruttu() {
        return peruttu;
    }

    public void setPeruttu(boolean peruttu) {
        this.peruttu = peruttu;
    }

    public List<Lippu> getLiput() {
        return liput;
    }

    public void setLiput(List<Lippu> liput) {
        this.liput = liput;
    }

    @Override
    public String toString() {
        return "Tila [tila_id=" + tila_id + ", myyty=" + myyty + ", myymatta=" + myymatta + ", tarkastettu="
                + tarkastettu + ", peruttu=" + peruttu + ", liput=" + liput + "]";
    }


}
