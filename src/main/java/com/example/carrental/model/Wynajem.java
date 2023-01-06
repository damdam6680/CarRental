package com.example.carrental.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "wynajem")
@Embeddable
public class Wynajem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWynajem")
    private int idWynajem;

    @ManyToOne()
    @JoinColumn(name = "idKlienta")
    Klienci klienci;




    @Column(name = "NrRachunku")
    private String NrRachunku;

    @Column(name = "Cena")
    private double Cena;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "Od")
    private java.util.Date Od;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "Do")
    private java.util.Date Do;

    @Column(name = "Komentarz")
    private String Komentarz;





    public Wynajem(){}
}
