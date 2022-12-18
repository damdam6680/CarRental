package com.example.carrental;

import com.example.carrental.model.Samochody;
import com.example.carrental.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SamochodyControler implements Initializable {

    ObservableList<Samochody> list = FXCollections.observableArrayList();
    @FXML
    private TableView<Samochody> table;

    @FXML
    private TableColumn<Samochody ,Integer> id;
    @FXML
    private TableColumn<Samochody ,String> marka;
    @FXML
    private TableColumn<Samochody ,String> model;
    @FXML
    private TableColumn<Samochody ,String> nrRejestracji;
    @FXML
    private TableColumn<Samochody ,Double> cena;



    public void initialize(URL url, ResourceBundle resourceBundle) {
            //getAll();

    }

    private void getAll() {

    }


}
