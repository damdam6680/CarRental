package com.example.carrental.model;

import jakarta.persistence.*;

import java.time.LocalDate;






@Entity
@Table(name = "wynajem")
public class Wynajem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWynajem")
    private int idWynajem;

    //@ManyToOne
    @Column(name = "idKlienci")
    Integer klienciList;

    //@ManyToOne()
    @Column(name = "idSamochodu")
    Integer samochodyList;


    @Column(name = "NrRachunku")
    private String NrRachunku;

    @Column(name = "Cena")
    private double Cena;

    @Column(name = "Oddano")
    private boolean Oddano;

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

    public Integer getKlienciList() {
        return klienciList;
    }

    public void setKlienciList(Integer klienciList) {
        this.klienciList = klienciList;
    }

    public Integer getSamochodyList() {
        return samochodyList;
    }

    public void setSamochodyList(Integer samochodyList) {
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

    public boolean isOddano() {
        return Oddano;
    }

    public void setOddano(boolean oddano) {
        Oddano = oddano;
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
