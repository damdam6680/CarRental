package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

public class DodajKlientaControler implements Initializable {

    @FXML
    private TextField idAdres;

    @FXML
    private TextField idImie;

    @FXML
    private TextField idNazwisko;

    @FXML
    private TextField idPesel;

    @FXML
    private TextField idPrawoJazdy;

    @FXML
    private TextField idTelefon;

    @FXML
    private TextField idText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void AddData(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Klienci klienci = new Klienci();

        klienci.setImie(idImie.getText());
        klienci.setNazwisko(idNazwisko.getText());
        klienci.setAdres(idAdres.getText());
        klienci.setTelefon(idTelefon.getText());
        klienci.setPrawoJazdy(idPrawoJazdy.getText());
        klienci.setPesel(idPesel.getText());


        session.persist(klienci);
        transaction.commit();
        session.close();
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

}
