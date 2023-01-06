package com.example.carrental.model;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "samochody")
public class Samochody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSamochodu", nullable = false)
    private int idSamochodu;

    @Column(name = "Marka")
    private String Marka;

    @Column(name = "Model")
    private String Model;

    @Column(name = "NrRejestracji")
    private String NrRejestracji;

    @Column(name = "CenaZaDzien")
    private String CenaZaDzien;

    public int getIdSamochodu() {
        return idSamochodu;
    }

    public void setIdSamochodu(int idSamochodu) {
        this.idSamochodu = idSamochodu;
    }

    public String getMarka() {
        return Marka;
    }
    public Samochody(){
    }

    public Samochody(int idSamochodu, String marka, String model, String nrRejestracji, String cenaZaDzien) {
        this.idSamochodu = idSamochodu;
        Marka = marka;
        Model = model;
        NrRejestracji = nrRejestracji;
        CenaZaDzien = cenaZaDzien;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getNrRejestracji() {
        return NrRejestracji;
    }

    public void setNrRejestracji(String nrRejestracji) {
        NrRejestracji = nrRejestracji;
    }

    public String getCenaZaDzien() {
        return CenaZaDzien;
    }

    public void setCenaZaDzien(String cenaZaDzien) {
        CenaZaDzien = cenaZaDzien;
    }



    @Override
    public String toString() {
        return "Samochody{" +
                "idSamochodu=" + idSamochodu +
                ", Marka='" + Marka + '\'' +
                ", Model='" + Model + '\'' +
                ", NrRejestracji='" + NrRejestracji + '\'' +
                ", CenaZaDzien='" + CenaZaDzien + '\'' +
                '}';
    }
}
