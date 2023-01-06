package com.example.carrental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "klienci")
public class Klienci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idKlienci")
    private int idKlienci ;

    @Column(name = "Imie")
    private String Imie;

    @Column(name = "Nazwisko")
    private String Nazwisko;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "PrawoJazdy")
    private String PrawoJazdy;

    @Column(name = "Telefon")
    private String Telefon;

    @Column(name = "Adres")
    private String Adres;

    public Klienci(){}

    public Klienci(int idKlienci, String imie, String nazwisko, String pesel, String prawoJazdy, String telefon, String adres) {
        this.idKlienci = idKlienci;
        Imie = imie;
        Nazwisko = nazwisko;
        this.pesel = pesel;
        PrawoJazdy = prawoJazdy;
        Telefon = telefon;
        Adres = adres;
    }

    public int getIdKlienci() {
        return idKlienci;
    }

    public void setIdKlienci(int idKlienci) {
        this.idKlienci = idKlienci;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPrawoJazdy() {
        return PrawoJazdy;
    }

    public void setPrawoJazdy(String prawoJazdy) {
        PrawoJazdy = prawoJazdy;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getAdres() {
        return Adres;
    }

    public void setAdres(String adres) {
        Adres = adres;
    }

    @Override
    public String toString() {
        return "Klienci{" +
                "idKlienci=" + idKlienci +
                ", Imie='" + Imie + '\'' +
                ", Nazwisko='" + Nazwisko + '\'' +
                ", pesel='" + pesel + '\'' +
                ", PrawoJazdy='" + PrawoJazdy + '\'' +
                ", Telefon='" + Telefon + '\'' +
                ", Adres='" + Adres + '\'' +
                '}';
    }
}
