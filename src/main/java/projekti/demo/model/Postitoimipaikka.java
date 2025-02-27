package projekti.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Postitoimipaikat")
public class Postitoimipaikka {

    @Id
    @NotEmpty(message = "Postinumero-kenttä ei saa olla tyhjä.")
    @Column(name = "postinumero")
    private String postinumero;

    @NotEmpty(message = "Postitoimipaikka-kenttä ei saa olla tyhjä.")
    @Size(max = 30, message = "Maksimipituus on 30 merkkiä.")
    @Column(name = "postitoimipaikka")
    private String postitoimipaikka; 

    @Size(max = 30, message = "Maksimipituus on 30 merkkiä.")
    @Column(name = "maa")
    private String maa;

    

    public Postitoimipaikka() {
        super();
    }

    public Postitoimipaikka(@NotEmpty(message = "Postinumero-kenttä ei saa olla tyhjä.") @NotEmpty(message = "Postinumero-kenttä ei saa olla tyhjä.") String postinumero,
            @NotEmpty(message = "Postitoimipaikka-kenttä ei saa olla tyhjä.") @Size(max = 30, message = "Maksimipituus on 30 merkkiä.") String postitoimipaikka,
            @Size(max = 30, message = "Maksimipituus on 30 merkkiä.") String maa) {
        this.postinumero = postinumero;
        this.postitoimipaikka = postitoimipaikka;
        this.maa = maa;
    }

    public @NotEmpty(message = "Postinumero-kenttä ei saa olla tyhjä.") String getPostinumero() {
        return postinumero;
    }

    public void setPostinumero(@NotEmpty(message = "Postinumero-kenttä ei saa olla tyhjä.") String postinumero) {
        this.postinumero = postinumero;
    }

    public String getPostitoimipaikka() {
        return postitoimipaikka;
    }

    public void setPostitoimipaikka(String postitoimipaikka) {
        this.postitoimipaikka = postitoimipaikka;
    }

    public String getMaa() {
        return maa;
    }

    public void setMaa(String maa) {
        this.maa = maa;
    }

    @Override
    public String toString() {
        return "Postitoimipaikka [postinumero=" + postinumero + ", postitoimipaikka=" + postitoimipaikka + ", maa="
                + maa + "]";
    }
    
}
