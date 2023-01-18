package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;
import com.example.carrental.model.Wynajem;
import jakarta.persistence.Query;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.text.Text;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomeControler implements Initializable {
    @FXML
    private Text liczba;

    @FXML
    private Text liczba1;

    @FXML
    private Text liczba11;

    @FXML
    private BarChart<?, ?> wykres;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WpiszLiczbeTransakcji();
        WpiszLiczbeSamochodow();
        WpiszLiczbeKlientow();
    }

    public void WpiszLiczbeTransakcji(){
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Query q1 = session.createQuery("Select count(idWynajem) from Wynajem");

       Long liczba = (Long) q1.getSingleResult();

       liczba11.setText(String.valueOf(liczba));
        transaction.commit();
        session.close();
    }
    public void WpiszLiczbeSamochodow(){
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Query q1 = session.createQuery("Select Count(idSamochodu) from Samochody");

        Long liczba = (Long) q1.getSingleResult();

        liczba1.setText(String.valueOf(liczba));


        transaction.commit();
        session.close();
    }
    public void WpiszLiczbeKlientow(){
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Query q1 = session.createQuery("Select Count(idKlienci) from Klienci");

        Long liczbaa = (Long) q1.getSingleResult();

        liczba.setText(String.valueOf(liczbaa));


        transaction.commit();
        session.close();
    }
}
