package com.example.carrental.controler;

import com.example.carrental.model.Samochody;
import com.example.carrental.model.Wynajem;

import com.jfoenix.controls.JFXAutoCompletePopup;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class WynajemControler implements Initializable {

    @FXML
    private TextField NrRachunku;

    @FXML
    private TextField cena;

    @FXML
    private DatePicker dodata;

    @FXML
    private ChoiceBox<?> idKlienta;


    @FXML
    private TextArea komentarz;

    @FXML
    private DatePicker od;

    private List<Wynajem> Wynajemlist;

    private List<Samochody> samochodylist;

    @FXML
    private TextField idSamochodutext;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        samochodylist = loadAllData(Samochody.class, session);
        System.out.println(Arrays.toString(samochodylist.toArray()));
        transaction.commit();
        session.close();
    }

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery("select NrRejestracji from Samochody").getResultList();
        return data;
    }


}
