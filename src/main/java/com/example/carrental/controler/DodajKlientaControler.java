package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.carrental.controler.Walidacjia.*;


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
        Walidacja();
        if(isBezZnakowSpecialnych(idImie.getText())
                && isBezZnakowSpecialnych(idNazwisko.getText())
                && isBezZnakowSpecialnych(idAdres.getText())
                && isCyfra(idTelefon.getText())
                && isCyfra(idPrawoJazdy.getText())
                && isPesel(idPesel.getText())
                && idText.getText() != ""
                && idAdres.getText() != ""
                && idImie.getText() != ""
                && idNazwisko.getText() != ""
                && idPesel.getText() != ""
                && idTelefon.getText() != ""
                && idPrawoJazdy.getText() != ""
        ) {
            session.persist(klienci);
            transaction.commit();
            session.close();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
        }
    }

    public void Walidacja(){

        if (!isBezZnakowSpecialnych(idPesel.getText()) || idPesel.getText() == "") {
            l7.setVisible(true);
            l7.setText("nie moze zawierac znakow specialnych");
        } else {
            l7.setVisible(false);
        }
        if (!isBezZnakowSpecialnych(idImie.getText()) || idImie.getText() == "") {
            l2.setVisible(true);
            l2.setText("nie moze zawierac znakow specialnych");
        } else {
            l2.setVisible(false);
        }
        if (!isBezZnakowSpecialnych(idNazwisko.getText()) || idNazwisko.getText() == "") {
            l3.setVisible(true);
            l3.setText("nie moze zawierac znaków specialnych");
        } else {
            l3.setVisible(false);
        }
        if (!isCyfra(idPrawoJazdy.getText()) || idPrawoJazdy.getText() == "") {
            l4.setVisible(true);
            l4.setText("podałeś zły nr");
        } else {
            l4.setVisible(false);
        }
        if (isCyfra(idAdres.getText()) || idAdres.getText() == "") {
            l5.setVisible(true);
            l5.setText("podałes zły adres");
        } else {
            l5.setVisible(false);
        }
        if (!isCyfra(idTelefon.getText()) || idTelefon.getText() == "") {
            l6.setVisible(true);
            l6.setText("podałeś zły numer telefonu");
        } else {
            l6.setVisible(false);
        }
    }

}
