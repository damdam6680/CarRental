package com.example.carrental.controler;

import com.example.carrental.Main;
import com.example.carrental.model.Klienci;
import com.example.carrental.model.Samochody;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KlienciController implements Initializable {
    @FXML
    private TableView<Klienci> tab;
    @FXML
    private TableColumn<Klienci, String> AdresTab;

    @FXML
    private TableColumn<Klienci, String> ImieTab;

    @FXML
    private TableColumn<Klienci, String> NazwiskoTab;

    @FXML
    private TableColumn<Klienci, String> PrawoJazdyTab;

    @FXML
    private TableColumn<Klienci, String> TelefonTab;

    @FXML
    private TableColumn<Klienci, Integer> idTab;

    @FXML
    private TableColumn<Klienci, String> peselTab;

    @FXML
    private TextField text;

    public List<Klienci> KlienciList;

    ObservableList<Klienci> KlienciObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTab.setCellValueFactory(new PropertyValueFactory<Klienci, Integer>("idKlienci"));
        ImieTab.setCellValueFactory(new PropertyValueFactory<Klienci, String>("Imie"));
        NazwiskoTab.setCellValueFactory(new PropertyValueFactory<Klienci, String>("Nazwisko"));
        PrawoJazdyTab.setCellValueFactory(new PropertyValueFactory<Klienci, String>("PrawoJazdy"));
        peselTab.setCellValueFactory(new PropertyValueFactory<Klienci, String>("pesel"));
        TelefonTab.setCellValueFactory(new PropertyValueFactory<Klienci, String>("Telefon"));
        AdresTab.setCellValueFactory(new PropertyValueFactory<Klienci, String>("Adres"));
        fetchData();
        for (Klienci temp : KlienciList) {
            KlienciObservableList.add(temp);
        }
        tab.setItems(KlienciObservableList);

    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        KlienciList = loadAllData(Klienci.class, session);
        System.out.println(Arrays.toString(KlienciList.toArray()));
        transaction.commit();
        session.close();
    }

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void wyszukiwanie() {
        FilteredList<Klienci> filteredList = new FilteredList<>(KlienciObservableList, e -> true);

        text.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateKlientData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                if (predicateKlientData.getImie().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getNazwisko().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getAdres().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getPesel().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateKlientData.getTelefon()).toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<Klienci> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tab.comparatorProperty());
            tab.setItems(sortedList);
        });
    }

    public void dodaj(ActionEvent actionEvent) throws IOException {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/carrental/DodajKlienta.fxml");
            System.out.println(fxmlLocation);
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Dodaj Klienta");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void loadAllData() {
        fetchData();
        KlienciObservableList.removeAll(KlienciObservableList);
        for (Klienci temp : KlienciList) {
            KlienciObservableList.add(temp);
        }
        System.out.println("zaczytane");
    }

    public void usun(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Klienci klienci1 = tab.getSelectionModel().getSelectedItem();

        session.delete(klienci1);
        transaction.commit();
        session.close();
        loadAllData();
    }
}
