package com.example.carrental.controler;

import com.example.carrental.model.Samochody;
import com.example.carrental.model.Wynajem;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CzyOddano implements Initializable {

    @FXML
    private TableColumn<Wynajem, String> IdSamochodu;



    @FXML
    private TableColumn<Wynajem, String> IdKlienta;

    @FXML
    private TableColumn<Wynajem, String> IdKlienta1;

    @FXML
    private TableColumn<Wynajem, String> czyZw;

    @FXML
    private TableColumn<Wynajem, String> doTable;

    @FXML
    private TableColumn<Wynajem, String> doZaplaty;

    @FXML
    private TableColumn<Wynajem, String> odTable;

    @FXML
    private TableView<Wynajem> tab;

    @FXML
    private TextField szukaj;


    private List<Wynajem> Wyj;

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        //SELECT wynajem.Do FROM `wynajem` WHERE Do < CURRENT_DATE
        List<T> data = session.createQuery("FROM Wynajem WHERE Do < CURRENT_DATE and Oddano = 0").getResultList();
        return data;
    }
    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Wyj = loadAllData(Wynajem.class, session);
        System.out.println(Arrays.toString(Wyj.toArray()));
        transaction.commit();
        session.close();
    }
    ObservableList<Wynajem> samochodyObservableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdSamochodu.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("samochodyList"));
        IdKlienta.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("klienciList"));
        czyZw.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("Oddano"));
        doTable.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("Do"));
        doZaplaty.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("Cena"));
        odTable.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("Od"));
        IdKlienta1.setCellValueFactory(new PropertyValueFactory<Wynajem,String>("NrRachunku"));
        fetchData();
        samochodyObservableList.addAll(Wyj);
        tab.setItems(samochodyObservableList);
    }


    public void editData(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wynajem wynajem = tab.getSelectionModel().getSelectedItem();


        wynajem.setOddano(true);
        session.update(wynajem);
        transaction.commit();
        session.close();
        System.out.println(wynajem.getIdWynajem());

    }

    public void wybrane(MouseEvent mouseEvent) {
    }

    public void wyszukiwanie(KeyEvent keyEvent) {
        FilteredList<Wynajem> filteredList = new FilteredList<>(samochodyObservableList, e -> true);

        szukaj.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateKlientData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                if (String.valueOf(predicateKlientData.getSamochodyList()).toString().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateKlientData.getKlienciList()).toString().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateKlientData.getCena()).toString().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateKlientData.getOd()).toString().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateKlientData.getDo()).toString().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getNrRachunku().toLowerCase().contains(searchKey)) {
                    return true;
                }  else return String.valueOf(predicateKlientData.getIdWynajem()).toString().contains(searchKey);
            });
            SortedList<Wynajem> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tab.comparatorProperty());
            tab.setItems(sortedList);
        });
    }

    public void odswierz(ActionEvent actionEvent) {
        fetchData();
        samochodyObservableList.removeAll(samochodyObservableList);
        samochodyObservableList.addAll(Wyj);
    }
}
