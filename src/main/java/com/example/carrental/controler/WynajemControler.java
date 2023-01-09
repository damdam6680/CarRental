package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;


import com.example.carrental.model.Wynajem;
import com.example.carrental.util.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class WynajemControler implements Initializable {

    @FXML
    private TextField NrRachunku;

    @FXML
    private TextField cena;

    @FXML
    private DatePicker dodata;

    @FXML
    private ComboBox<Klienci> idKleinta123;

    @FXML
    private ComboBox<Samochody> idSamochoduText;

    @FXML
    private TextArea komentarz;

    @FXML
    private DatePicker od;

    private List<Samochody> samochodylist;
    private List<Klienci> KlienciList;




    ObservableList<Samochody> samochodyObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            fetchDataCar();
            fetchDataKlient();

            idSamochoduText.getItems().setAll(samochodylist);
            idKleinta123.getItems().setAll(KlienciList);

    }

    public void fetchDataCar() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        samochodylist = loadAllDataCar(Samochody.class, session);
        System.out.println(Arrays.toString(samochodylist.toArray()));
        transaction.commit();
        session.close();
    }

    private static <T> List<T> loadAllDataCar(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery("select idSamochodu from Samochody").getResultList();
        return data;
    }
    public void fetchDataKlient() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        KlienciList = loadAllDataKlienta(Klienci.class, session);
        System.out.println(Arrays.toString(KlienciList.toArray()));
        transaction.commit();
        session.close();
    }

    private static <T> List<T> loadAllDataKlienta(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery("select idKlienci  from Klienci").getResultList();
        return data;
    }


    public void InfoSamochody(ActionEvent actionEvent) {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/carrental/infoCar.fxml");
            System.out.println(fxmlLocation);
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            InfoCarControler infoCarControler = fxmlLoader.getController();

            System.out.println(idSamochoduText.getValue());

            infoCarControler.fetchData(String.valueOf(idSamochoduText.getValue()));
            infoCarControler.wypisz();
            stage.setTitle("Info");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void WyswietlCene(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select CenaZaDzien from Samochody WHERE idSamochodu = :id");
        query.setParameter("id",idSamochoduText.getValue());
        String cena1 = query.getSingleResult().toString();
        cena.setText(cena1);
        transaction.commit();
        session.close();

    }

    public void Wygeneruj(ActionEvent actionEvent) {
        String generatedString = RandomStringUtils.randomAlphabetic(11).toUpperCase(Locale.ROOT);

        NrRachunku.setText(generatedString);
    }

    public void Dodaj(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wynajem wynajem = new Wynajem();

       // wynajem.setIdWynajem(Integer.parseInt(NrRachunku.getText()));
        wynajem.setCena(Double.parseDouble(cena.getText()));
        wynajem.setDo(dodata.getValue());
        wynajem.setOd(od.getValue());
       // wynajem.setKlienciList(idKleinta123.getValue().toString());
        //wynajem.setSamochodyList(idSamochoduText.getValue().toString());
        wynajem.setKomentarz(komentarz.getText());


        session.persist(wynajem);
        transaction.commit();
        session.close();
    }
}
