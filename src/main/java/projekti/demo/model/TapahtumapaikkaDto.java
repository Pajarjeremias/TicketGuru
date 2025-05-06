package projekti.demo.model;

public class TapahtumapaikkaDto {
    public String nimi;
    public String katuosoite;
    public String postinumero;
    public String postitoimipaikka;
    public String maa;
    public int maksimi_osallistujat;
    public Long tapahtuma_id;
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
    public String getPostinumero() {
        return postinumero;
    }
    public void setPostinumero(String postinumero) {
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
    public int getMaksimi_osallistujat() {
        return maksimi_osallistujat;
    }
    public void setMaksimi_osallistujat(int maksimi_osallistujat) {
        this.maksimi_osallistujat = maksimi_osallistujat;
    }
    public Long getTapahtuma_id() {
        return tapahtuma_id;
    }
    public void setTapahtuma_id(Long tapahtuma_id) {
        this.tapahtuma_id = tapahtuma_id;
    }
}

