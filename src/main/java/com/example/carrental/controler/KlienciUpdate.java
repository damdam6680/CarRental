package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class KlienciUpdate {

    @FXML
    private TextField idAdress;

    @FXML
    private TextField idImie;

    @FXML
    private TextField idNazwisko;

    @FXML
    private TextField idPrawoJazdy;

    @FXML
    private TextField idPresel;

    @FXML
    private TextField idTelefon;

    @FXML
    private TextField idText;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private Label l4;

    @FXML
    private Label l5;

    @FXML
    private Label l6;

    @FXML
    private Label l7;
    List<Klienci> kliencis;
    public void wypisz(){
        idText.setText(String.valueOf(kliencis.get(0).getIdKlienci()));
        idAdress.setText(String.valueOf(kliencis.get(0).getAdres()));
        idImie.setText(String.valueOf(kliencis.get(0).getImie()));
        idNazwisko.setText(String.valueOf(kliencis.get(0).getNazwisko()));
        idPresel.setText(String.valueOf(kliencis.get(0).getPesel()));
        idPrawoJazdy.setText(String.valueOf(kliencis.get(0).getPrawoJazdy()));
        idTelefon.setText(String.valueOf(kliencis.get(0).getTelefon()));


    }

    public static <T> List<T> loadAllData(Class<T> type, Session session, String id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        Query query = session.createQuery("from Klienci as s where s.idKlienci = :id");
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
         kliencis = loadAllData(Klienci.class, session, id);
        System.out.println(Arrays.toString(kliencis.toArray()));
        System.out.println(kliencis.isEmpty());
        transaction.commit();
        session.close();
    }


    public void Updatet(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Klienci klienci = new Klienci();

        klienci.setIdKlienci(Integer.parseInt(idText.getText()));
        klienci.setAdres(idAdress.getText());
        klienci.setImie(idImie.getText());
        klienci.setNazwisko(idNazwisko.getText());
        klienci.setTelefon(idTelefon.getText());
        klienci.setPrawoJazdy(idPrawoJazdy.getText());
        klienci.setPesel(idPresel.getText());


        session.update(klienci);
        transaction.commit();
        session.close();
    }
}
