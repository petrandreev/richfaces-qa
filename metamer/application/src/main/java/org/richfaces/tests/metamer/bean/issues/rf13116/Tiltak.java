package org.richfaces.tests.metamer.bean.issues.rf13116;

public class Tiltak {

    private String tittel;
    private Ansvarlig ansvarlig;
    private String kategori;
    private String lokasjon;

    public Tiltak(String tittel) {
        super();
        this.tittel = tittel;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public Ansvarlig getAnsvarlig() {
        return ansvarlig;
    }

    public void setAnsvarlig(Ansvarlig ansvarlig) {
        this.ansvarlig = ansvarlig;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getLokasjon() {
        return lokasjon;
    }

    public void setLokasjon(String lokasjon) {
        this.lokasjon = lokasjon;
    }
}
