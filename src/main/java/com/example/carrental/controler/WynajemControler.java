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

/**
 * Ta Klasa słuzy do opsługi Zakładki Wynajem
 *
 */
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

    static List idSamochodu;
    static List idKlienta;
    ObservableList<Wynajem> samochodyObservableList = FXCollections.observableArrayList();

    ObservableList<Wynajem> DatayObseravbleList = FXCollections.observableArrayList();
    //TODO dadac walidacjie do dodac i usun
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

    /**
     * Ta Klasa służy do tworzenia sesji i określenia configuracji hibernata
     *
     */
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

    /**
     * Ta Klasa słuzy do wysyłania zapytania do Tabeli Samochody
     * @param type
     * @param session
     * @return
     * @param <T>
     */

    private static <T> List<T> loadAllDataCar(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return (List<T>) session.createQuery("select NrRejestracji from Samochody").getResultList();
    }

    /**
     * Ta Klasa słuzy do wysyłania zapytania do Tabeli Klienci
     * @param type
     * @param session
     * @return
     * @param <T>
     */
    private static <T> List<T> loadAllDataKlienta(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery("select pesel  from Klienci").getResultList();
        return data;
    }

    /**
     * Ta Klasa słuzy do wysyłania zapytania do Tabeli Wynajem
     * @param type
     * @param session
     * @return
     * @param <T>
     */
    private static <T> List<T> loadAllWynajemKlienta(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    /**
     * Ta klasa słuzy do otwarcia nowego okna z informacjami o samochodzie
     * Przekazuje parametr z ComoBoxa do Klasy InfoCarControler
     * @param actionEvent
     */
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

    /**
     * Przy zmiane prametru w ComoBoxie zostaja wysłąne zapytanie o dane wartosci samochodu
     * Funkcja wywołuje jeszcze 2 inne funkcjie Oblicz() i WyswietlDate
     * @param actionEvent
     */
    public void WyswietlCene(ActionEvent actionEvent) {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Samochody.class);
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            SessionFactory factory = config.buildSessionFactory(builder.build());
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("Select CenaZaDzien from Samochody WHERE NrRejestracji = :id");
            query.setParameter("id", idSamochoduText.getValue());
            cena1 = query.getSingleResult().toString();

            Query query1 = session.createQuery("Select idSamochodu  From Samochody WHERE NrRejestracji = :id");
            query1.setParameter("id", idSamochoduText.getValue());
            System.out.println(query1.getResultList());

            idSamochodu = query1.getResultList();


            Oblicz();
        WyswietlDate(query1.getResultList());

        transaction.commit();
            session.close();

    }

    /**
     * Funkcja generuje losowy ciąg znaków jako nr rachunku
     * @param actionEvent
     */
    public void Wygeneruj(ActionEvent actionEvent) {
        String generatedString = RandomStringUtils.randomAlphabetic(16).toUpperCase(Locale.ROOT);

        NrRachunku.setText(generatedString);
    }

    /**
     * Funkcja dodaje dane do bazy danych i sprawdza czy dane sa poprawne
     * @param actionEvent
     */
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

            wynajem.setNrRachunku(NrRachunku.getText());
            wynajem.setCena(Double.parseDouble(cena.getText()));
            wynajem.setDo(dodata.getValue());
            wynajem.setOd(od.getValue());
            wynajem.setKlienciList(Integer.valueOf(String.valueOf(idKlienta.get(0))));
            wynajem.setSamochodyList(Integer.valueOf(String.valueOf(idSamochodu.get(0))));
            wynajem.setKomentarz(komentarz.getText());
            session.persist(wynajem);
            transaction.commit();


        session.close();
    }

    /**
     * Ta klasa słuzy do otwarcia nowego okna z informacjami o Kliencie
     * Przekazuje parametr z ComoBoxa do Klasy InfoKlienciKontroler
     * @param actionEvent
     */
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

    /**
     * Ta Funkcja Słuzy do wpisanaia informacji o błednych danych
     */
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

    /**
     * Funckaj oblicza Cene wypożyczenia
     */
    public void Oblicz() {
        int liczbadni = 0;
        if (od.getValue() != null && dodata.getValue() != null) {
            liczbadni = (int) ChronoUnit.DAYS.between(od.getValue(), dodata.getValue());
        }
        cena.setText(String.valueOf( liczbadni * Double.parseDouble(cena1)));
    }

    /**
     * Ta Funkcja wyswietla Date od i do wpożyczenia danego samochodu
     * @param id
     */
    public void WyswietlDate(List id) {

        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        List allpatients;

        Query q1 = session.createQuery("Select Od, Do from Wynajem WHERE samochodyList =:id");
        q1.setParameter("id", id.get(0));
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

    /**
     * Ta Funkcja Sprawdza czy Daty nie Koliduja z innymi danymi7
     */
    public void SprawdzicCzyNieKoliduje(){
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wynajem.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q1 = session.createQuery("Select Od, Do from Wynajem WHERE samochodyList =:id");
        q1.setParameter("id", idSamochodu.get(0));
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

    /**
     * Funckaj służy do aktualizacji danych w bazie danych i sprawdza czy dane sa poprawne
     * @param actionEvent
     */
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

            session.update(wynajem1);
            transaction.commit();
            session.close();

    }

    /**
     * Funkcja wpisuje po kliknieciu rekordu z tabeli dane do textfildów
     */
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

    /**
     * Funcka służy do usuwania rekordu z bazy danych
     * Funkcja pobiera dane z TextFlidow
     * I wysyła zapytanie o usuniece do bazy danych
     * @param actionEvent
     */
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

    /**
     * Funkcja słuzy do przeszukiwania danych w tabelce
     * Pobiera Klucz z TextFilda i sprawdza czy wpisany klucz nie zawiera jakiegość pola z tabeli
     * @param keyEvent
     */
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

    public void PobierzIdKlienta(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Klienci.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("Select idKlienci from Klienci WHERE pesel = :id");
        query.setParameter("id", idKleinta123.getValue());
        idKlienta = query.getResultList();
        System.out.println(query.getResultList());
        transaction.commit();
        session.close();
    }
}