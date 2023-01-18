package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class InfoKlienciControler {
    @FXML
    private TextField idAdres;

    @FXML
    private TextField idImie;

    @FXML
    private TextField idKlienciText;

    @FXML
    private TextField idNazwisko;

    @FXML
    private TextField idPesel;

    @FXML
    private TextField idPrawoJazdy;

    @FXML
    private TextField idTelefon;

    private static List<Klienci> kliencilist;

    public void wypisz(){
        idKlienciText.setText(String.valueOf(kliencilist.get(0).getIdKlienci()));
        idAdres.setText(String.valueOf(kliencilist.get(0).getAdres()));
        idImie.setText(String.valueOf(kliencilist.get(0).getImie()));
        idNazwisko.setText(String.valueOf(kliencilist.get(0).getNazwisko()));
        idTelefon.setText(String.valueOf(kliencilist.get(0).getTelefon()));
        idPesel.setText(String.valueOf(kliencilist.get(0).getPesel()));
        idPrawoJazdy.setText(String.valueOf(kliencilist.get(0).getPrawoJazdy()));


    }

    public static <T> List<T> loadAllData(Class<T> type, Session session, String id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        Query query = session.createQuery("from Klienci as s where s.pesel  = :id");
        query.setParameter("id",id);
        List<T> data = query.getResultList();
        return data;
    }
    public void fetchData(String id) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        kliencilist = loadAllData(Klienci.class, session,id);
        System.out.println(Arrays.toString(kliencilist.toArray()));
        System.out.println(kliencilist.isEmpty());
        transaction.commit();
        session.close();
    }

}
