package projekti.demo.model;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Jarjestajat")
public class Jarjestaja {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jarjestaja_id")
    private Long jarjestaja_id;

    @NotEmpty(message = "Organisaation nimi on pakollinen.")
    @Size(max = 30, message = "Maksimipituus on 30 merkkiä.")
    @Column(name = "nimi")
    private String nimi;

    @OneToOne
    @JoinColumn(name="kayttaja_id")
    @NotEmpty(message = "Käyttäjä on pakollinen")

    private Kayttaja yhteyshenkilo_id;

    @NotEmpty(message = "Organisaation katuosoite on pakollinen.")
    @Size(max = 30, message = "Maksimipituus on 30 merkkiä.")
    @Column(name = "katuosoite")
    private String katuosoite;

    @ManyToOne
    @JoinColumn(name="postinumero")
    @NotEmpty(message = "Postinumero on pakollinen.")
    private Postitoimipaikka postinumero;

    public Jarjestaja(){
        super();
    }

    public Jarjestaja(
            @NotEmpty(message = "Organisaation nimi on pakollinen.") @Size(max = 30, message = "Maksimipituus on 30 merkkiä.") String nimi,
            @NotEmpty(message = "Käyttäjä on pakollinen") Kayttaja yhteyshenkilo_id,
            @NotEmpty(message = "Organisaation katuosoite on pakollinen.") @Size(max = 30, message = "Maksimipituus on 30 merkkiä.") String katuosoite,
            @NotEmpty(message = "Postinumero on pakollinen.") Postitoimipaikka postinumero) {
        this.nimi = nimi;
        this.yhteyshenkilo_id = yhteyshenkilo_id;
        this.katuosoite = katuosoite;
        this.postinumero = postinumero;
    }

    public Long getJarjestaja_id() {
        return jarjestaja_id;
    }

    public void setJarjestaja_id(Long jarjestaja_id) {
        this.jarjestaja_id = jarjestaja_id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Kayttaja getYhteyshenkilo_id() {
        return yhteyshenkilo_id;
    }

    public void setYhteyshenkilo_id(Kayttaja yhteyshenkilo_id) {
        this.yhteyshenkilo_id = yhteyshenkilo_id;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public Postitoimipaikka getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(Postitoimipaikka postinumero) {
        this.postinumero = postinumero;
    }

    



}
