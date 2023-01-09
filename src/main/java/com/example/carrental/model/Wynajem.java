package com.example.carrental.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "wynajem")
@Embeddable
public class Wynajem {
//TODO NAPRAWIC Klienci LIst i Samochody list i czas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWynajem")
    private int idWynajem;

    @OneToMany()
    @JoinColumn(name = "idKlienta")
    private List<Klienci> klienciList;

    @OneToMany()
    @JoinColumn(name = "idSamochodu")
    private List<Samochody> samochodyList;


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

    public List<Klienci> getKlienciList() {
        return klienciList;
    }

    public void setKlienciList(List<Klienci> klienciList) {
        this.klienciList = klienciList;
    }

    public List<Samochody> getSamochodyList() {
        return samochodyList;
    }

    public void setSamochodyList(List<Samochody> samochodyList) {
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
