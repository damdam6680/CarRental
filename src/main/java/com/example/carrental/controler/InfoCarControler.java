package com.example.carrental.controler;

import com.example.carrental.model.Samochody;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InfoCarControler implements Initializable {
    @FXML
    private TextField idCena;

    @FXML
    private TextField idMarka;

    @FXML
    private TextField idModel;

    @FXML
    private TextField idNrRejestracji;

    @FXML
    private static TextField idText;

    private static List<Samochody> samochodylist;



    ObservableList<Samochody> samochodyObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static <T> List<T> loadAllData(Class<T> type, Session session,String id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        Query query = session.createQuery("from Samochody as s where s.idSamochodu   = :id");
        query.setParameter("id",id);
        List<T> data = query.getResultList();
        return data;
    }
    public void fetchData(String id) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        samochodylist = loadAllData(Samochody.class, session,id);
        System.out.println(Arrays.toString(samochodylist.toArray()));
        System.out.println(samochodylist.isEmpty());
        transaction.commit();
        session.close();
    }



}
