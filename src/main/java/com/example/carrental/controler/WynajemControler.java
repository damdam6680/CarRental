package com.example.carrental.controler;

import com.example.carrental.model.Klienci;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.example.carrental.controler.Walidacjia.*;


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

    @FXML
    private Label CenaError;

    @FXML
    private Label NrRachunkuLabel;
    @FXML
    private Label doLabel;
    @FXML
    private Label idKlentaLabel;

    @FXML
    private Label idSamochoduLabel;
    @FXML
    private Label odLabel;

    @FXML
    private TextArea tx;
    @FXML
    private TextArea tx1;

    //Tablica
    @FXML
    private TableColumn<Wynajem, String> NrRachunkutab;
    @FXML
    private TableColumn<Wynajem, Double> cenatab;
    @FXML
    private TableColumn<Wynajem, LocalDate> dotab;
    @FXML
    private TableColumn<Wynajem, Integer> idKliencitab;
    @FXML
    private TableColumn<Wynajem, Integer> idSamochodutab;
    @FXML
    private TableColumn<Wynajem, Integer> idWynajemtab;
    @FXML
    private TableColumn<Wynajem, String> komentarztab;
    @FXML
    private TableColumn<Wynajem, LocalDate> odtab;
    @FXML
    private TableView<Wynajem> tablica;

    //TexFild Tablicy
    @FXML
    private TextField CenaText;

    @FXML
    private TextField KomentarzText;

    @FXML
    private TextField NrRachunkuText;

    @FXML
    private TextField doText;

    @FXML
    private TextField odText;

    @FXML
    private TextField idKlenciText;

    @FXML
    private TextField idSamochoduid;

    @FXML
    private TextField idWynajemText;

    @FXML
    private TextField szukaj;



    private List<Samochody> samochodylist;
    private List<Klienci> KlienciList;

    private List<Wynajem> wynajemList;

    static String cena1;
    boolean walidaciabool = false;


    ObservableList<Wynajem> samochodyObservableList = FXCollections.observableArrayList();

    ObservableList<Wynajem> DatayObseravbleList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idWynajemtab.setCellValueFactory(new PropertyValueFactory<Wynajem, Integer>("idWynajem"));
        idKliencitab.setCellValueFactory(new PropertyValueFactory<Wynajem, Integer>("klienciList"));
        idSamochodutab.setCellValueFactory(new PropertyValueFactory<Wynajem, Integer>("samochodyList"));
        NrRachunkutab.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("NrRachunku"));
        cenatab.setCellValueFactory(new PropertyValueFactory<Wynajem, Double>("Cena"));
        odtab.setCellValueFactory(new PropertyValueFactory<Wynajem, LocalDate>("Do"));
        dotab.setCellValueFactory(new PropertyValueFactory<Wynajem, LocalDate>("Od"));
        komentarztab.setCellValueFactory(new PropertyValueFactory<Wynajem, String>("Komentarz"));
        fetchAllData();
        samochodyObservableList.addAll(wynajemList);

        tablica.setItems(samochodyObservableList);

        idSamochoduText.getItems().setAll(samochodylist);
        idKleinta123.getItems().setAll(KlienciList);

    }

    public void fetchAllData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);
        config.addAnnotatedClass(Klienci.class);
        config.addAnnotatedClass(Wynajem.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        KlienciList = loadAllDataKlienta(Klienci.class, session);
        samochodylist = loadAllDataCar(Samochody.class, session);
        wynajemList = loadAllWynajemKlienta(Wynajem.class, session);
        System.out.println(Arrays.toString(samochodylist.toArray()));
        System.out.println(Arrays.toString(wynajemList.toArray()));
        transaction.commit();
        session.close();
    }

    private static <T> List<T> loadAllDataCar(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return (List<T>) session.createQuery("select idSamochodu from Samochody").getResultList();
    }

    private static <T> List<T> loadAllDataKlienta(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery("select idKlienci  from Klienci").getResultList();
        return data;
    }

    private static <T> List<T> loadAllWynajemKlienta(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void InfoSamochody(ActionEvent actionEvent) {
        if(idSamochoduText.getValue() != null) {
            try {
                URL fxmlLocation = getClass().getResource("/com/example/carrental/infoCar.fxml");
                System.out.println(fxmlLocation);
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                InfoCarControler infoCarControler = fxmlLoader.getController();
                System.out.println(idSamochoduText.getValue());
                infoCarControler.fetchData(String.valueOf(idSamochoduText.getValue()));
                infoCarControler.wypisz();
                stage.setTitle("Info");
                stage.setScene(new Scene(root));
                stage.show();
                idSamochoduLabel.setVisible(false);
            } catch (Exception e) {
                System.out.println(e);
            }
        }else {
            idSamochoduLabel.setVisible(true);
            idSamochoduLabel.setText("nie moze byc puste");
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
            Query query = session.createQuery("Select CenaZaDzien from Samochody WHERE idSamochodu = :id");
            query.setParameter("id", idSamochoduText.getValue());
            cena1 = query.getSingleResult().toString();
            Oblicz();
            transaction.commit();
            session.close();
            WyswietlDate();

    }

    public void Wygeneruj(ActionEvent actionEvent) {
        String generatedString = RandomStringUtils.randomAlphabetic(16).toUpperCase(Locale.ROOT);

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

        walidacja();
        SprawdzicCzyNieKoliduje();
        if (isCyfra(cena.getText()) && !cena.getText().isEmpty() && idKleinta123.getValue() != null && idSamochoduText.getValue() != null && dodata.getValue() != null && od.getValue() != null && !isOdWieksza(dodata.getValue(), od.getValue()) && !isPrzeszlosc(dodata.getValue(), od.getValue()) && !Objects.equals(NrRachunku.getText(), "") && walidaciabool == true) {
            wynajem.setNrRachunku(NrRachunku.getText());
            wynajem.setCena(Double.parseDouble(cena.getText()));
            wynajem.setDo(dodata.getValue());
            wynajem.setOd(od.getValue());
            wynajem.setKlienciList(Integer.valueOf(String.valueOf(idKleinta123.getValue())));
            wynajem.setSamochodyList(Integer.valueOf(String.valueOf(idSamochoduText.getValue())));
            wynajem.setKomentarz(komentarz.getText());
            session.persist(wynajem);
            transaction.commit();

        }
        session.close();
    }


    public void wiecejOpen(ActionEvent actionEvent) {
        if(idKleinta123.getValue() != null) {
            try {
                URL fxmlLocation = getClass().getResource("/com/example/carrental/infoKlienci.fxml");
                System.out.println(fxmlLocation);
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();

                InfoKlienciControler infoKlienciControler = fxmlLoader.getController();

                System.out.println(idKleinta123.getValue());

                infoKlienciControler.fetchData(String.valueOf(idKleinta123.getValue()));
                infoKlienciControler.wypisz();
                stage.setTitle("Info");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.out.println(e);
            }
            idKlentaLabel.setVisible(false);
        }else {
            idKlentaLabel.setVisible(true);
            idKlentaLabel.setText("nie moze byc puste");
        }

    }

    public void walidacja() {
        if (!isCyfra(cena.getText()) || cena.getText().equals("")) {
            CenaError.setVisible(true);
            CenaError.setText("nie podałeś cyfry");

        } else if (isCyfra(cena.getText())) {
            CenaError.setVisible(false);

        }
        if (NrRachunku.getText().equals("")) {
            NrRachunkuLabel.setVisible(true);
            NrRachunkuLabel.setText("nie może być puste");
        } else {
            NrRachunkuLabel.setVisible(false);

        }
        if (idKleinta123.getValue() == null) {
            idKlentaLabel.setVisible(true);
            idKlentaLabel.setText("nie może być puste");
        } else {
            idKlentaLabel.setVisible(false);

        }
        if (idSamochoduText.getValue() == null) {
            idSamochoduLabel.setVisible(true);
            idSamochoduLabel.setText("nie może być puste");
        } else {
            idSamochoduLabel.setVisible(false);

        }
        if (dodata.getValue() == null) {
            doLabel.setVisible(true);
            doLabel.setText("nie może być puste");
        } else {
            doLabel.setVisible(false);

        }
        if (od.getValue() == null) {
            odLabel.setVisible(true);
            odLabel.setText("nie może być puste");
        } else {
            odLabel.setVisible(false);

        }
        if (isOdWieksza(dodata.getValue(), od.getValue())) {
            doLabel.setVisible(true);
            doLabel.setText("nie mozna ustawic do daty do wieszesz niez od ");
            walidaciabool = false;
        } else {
            doLabel.setVisible(false);
        }
        if (isPrzeszlosc(dodata.getValue(), od.getValue())) {
            odLabel.setVisible(true);
            odLabel.setText("nie mozna rezerowac w przeszlosc ");
        } else {
            odLabel.setVisible(false);
        }


    }


    public void Oblicz() {
        int liczbadni = 0;
        if (od.getValue() != null && dodata.getValue() != null) {
            liczbadni = (int) ChronoUnit.DAYS.between(od.getValue(), dodata.getValue());
        }
        cena.setText(String.valueOf(liczbadni * Integer.parseInt(cena1)));
    }

    public void WyswietlDate() {

        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        List allpatients;
        Query q1 = session.createQuery("Select Od, Do from Wynajem WHERE samochodyList =:id");
        q1.setParameter("id", idSamochoduText.getValue());
        String datay = "";
        String dotay = "";
        allpatients=  ((org.hibernate.query.Query<?>) q1).list();
        for (Iterator it = allpatients.iterator(); it.hasNext(); ) {
            Object[] myResult = (Object[]) it.next();
            LocalDate od = (LocalDate) myResult[0];
            LocalDate Do = (LocalDate) myResult[1];
            datay += od + "\n";
            dotay += Do + "\n";
        }

        tx.setText(datay);
        tx1.setText(dotay);
        transaction.commit();
        session.close();


    }
    public void SprawdzicCzyNieKoliduje(){
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q1 = session.createQuery("Select Od, Do from Wynajem WHERE samochodyList =:id");
        q1.setParameter("id", idSamochoduText.getValue());
        List allpatients1;
        LocalDate d = od.getValue();
        LocalDate d1 = dodata.getValue();
        allpatients1=  ((org.hibernate.query.Query<?>) q1).list();
        for (Iterator it = allpatients1.iterator(); it.hasNext(); ) {
            Object[] myResult = (Object[]) it.next();
            LocalDate od = (LocalDate) myResult[0];
            LocalDate Do = (LocalDate) myResult[1];
            if (od.compareTo(d) * d.compareTo(od)  >= 0 || od.compareTo(d1) * d.compareTo(od) >= 0){
                doLabel.setText("jedna z tych data koliduje z istniejacym zamowieniem");
                doLabel.setVisible(true);
                System.out.println("jaks data jest pomiedzy");
                walidaciabool = false;
                break;
            }else {
                doLabel.setVisible(false);
                walidaciabool = true;

            }
        }

    }

    //====================
    public void editData(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wynajem wynajem1 = new Wynajem();

        wynajem1.setIdWynajem(Integer.parseInt(idWynajemText.getText()));
        wynajem1.setKlienciList(Integer.parseInt(idKlenciText.getText()));
        wynajem1.setSamochodyList(Integer.valueOf(idSamochoduid.getText()));
        wynajem1.setNrRachunku(NrRachunkuText.getText());
        wynajem1.setCena(Double.parseDouble(CenaText.getText()));
        wynajem1.setOd(LocalDate.parse(odText.getText()));
        wynajem1.setDo(LocalDate.parse(doText.getText()));
        wynajem1.setKomentarz(String.valueOf(KomentarzText.getText()));
       // walidacja();
        if (isCyfra(String.valueOf(idSamochoduText.getValue())) && isCyfra(idKlenciText.getText()) && isCyfra(idSamochoduid.getText()) && isCyfra(CenaText.getText()) && isData(odText.getText()))
            session.update(wynajem1);
            transaction.commit();
            session.close();

    }

    public void wybrane() {
        Wynajem wynajem = tablica.getSelectionModel().getSelectedItem();
        idWynajemText.setText(String.valueOf(wynajem.getIdWynajem()));
        idKlenciText.setText(String.valueOf(wynajem.getKlienciList()));
        idSamochoduid.setText(String.valueOf(wynajem.getSamochodyList()));
        NrRachunkuText.setText(String.valueOf(wynajem.getNrRachunku()));
        CenaText.setText(String.valueOf(wynajem.getCena()));
        odText.setText(String.valueOf(wynajem.getOd()));
        doText.setText(String.valueOf(wynajem.getDo()));
        KomentarzText.setText(String.valueOf(wynajem.getKomentarz()));
    }

    public void usun(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wynajem wynajem1 = new Wynajem();

        wynajem1.setIdWynajem(Integer.parseInt(idWynajemText.getText()));
        wynajem1.setKlienciList(Integer.parseInt(idKlenciText.getText()));
        wynajem1.setSamochodyList(Integer.valueOf(idSamochoduid.getText()));
        wynajem1.setNrRachunku(NrRachunkuText.getText());
        wynajem1.setCena(Double.parseDouble(CenaText.getText()));
        wynajem1.setOd(LocalDate.parse(odText.getText()));
        wynajem1.setDo(LocalDate.parse(doText.getText()));
        wynajem1.setKomentarz(String.valueOf(KomentarzText.getText()));


        session.delete(wynajem1);
        transaction.commit();
        session.close();
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
                } else if ( String.valueOf(predicateKlientData.getOd()).toString().contains(searchKey)) {
                    return true;
                }
                  else if (String.valueOf(predicateKlientData.getDo()).toString().contains(searchKey)) {
                    return true;
                }else if (predicateKlientData.getNrRachunku().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateKlientData.getKomentarz().toLowerCase().contains(searchKey)) {
                    return true;
                } else return String.valueOf(predicateKlientData.getIdWynajem()).toString().contains(searchKey);
            });
            SortedList<Wynajem> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tablica.comparatorProperty());
            tablica.setItems(sortedList);
        });
    }
}