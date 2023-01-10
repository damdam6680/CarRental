package com.example.carrental.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "wynajem")
@Embeddable
public class Wynajem {
//TODO NAPRAWIC Klienci LIst i Samochody list i czas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWynajem")
    private int idWynajem;

    //@OneToMany()
    @Column(name = "idKlienci")
    private int klienciList;

    //@OneToMany()
    @Column(name = "idSamochodu")
    private int samochodyList;


    @Column(name = "NrRachunku")
    private String NrRachunku;

    @Column(name = "Cena")
    private double Cena;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "Od")
    private LocalDate Od;
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "Do")
    private LocalDate Do;

    @Column(name = "Komentarz")
    private String Komentarz;

    public int getIdWynajem() {
        return idWynajem;
    }

    public void setIdWynajem(int idWynajem) {
        this.idWynajem = idWynajem;
    }

    public int getKlienciList() {
        return klienciList;
    }

    public void setKlienciList(int klienciList) {
        this.klienciList = klienciList;
    }

    public int getSamochodyList() {
        return samochodyList;
    }

    public void setSamochodyList(int samochodyList) {
        this.samochodyList = samochodyList;
    }

    public String getNrRachunku() {
        return NrRachunku;
    }

    public void setNrRachunku(String nrRachunku) {
        NrRachunku = nrRachunku;
    }

    public double getCena() {
        return Cena;
    }

    public void setCena(double cena) {
        Cena = cena;
    }

    public LocalDate getOd() {
        return Od;
    }

    public void setOd(LocalDate od) {
        Od = od;
    }

    public LocalDate getDo() {
        return Do;
    }

    public void setDo(LocalDate aDo) {
        Do = aDo;
    }

    public String getKomentarz() {
        return Komentarz;
    }

    public void setKomentarz(String komentarz) {
        Komentarz = komentarz;
    }

    @Override
    public String toString() {
        return "Wynajem{" +
                "idWynajem=" + idWynajem +
                ", klienciList=" + klienciList +
                ", samochodyList=" + samochodyList +
                ", NrRachunku='" + NrRachunku + '\'' +
                ", Cena=" + Cena +
                ", Od=" + Od +
                ", Do=" + Do +
                ", Komentarz='" + Komentarz + '\'' +
                '}';
    }

    public Wynajem(){}
}
