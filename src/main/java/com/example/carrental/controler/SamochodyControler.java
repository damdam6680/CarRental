package com.example.carrental.controler;


import com.example.carrental.model.Samochody;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class SamochodyControler implements Initializable {
    @FXML
    private Label Clabel;

    @FXML
    private Label Lmarka;

    @FXML
    private Label Lmodel;

    @FXML
    private Label NLabel;
    @FXML
    private TableView<Samochody> tabela;

    @FXML
    private TableColumn<Samochody ,Integer> id;
    @FXML
    private TableColumn<Samochody ,String> marka;
    @FXML
    private TableColumn<Samochody ,String> model;
    @FXML
    private TableColumn<Samochody ,String> nrRejestracji;
    @FXML
    private TableColumn<Samochody ,String> cena;

    @FXML
    private TextField CenaText;

    @FXML
    private TextField MarkaText;

    @FXML
    private TextField ModelText;

    @FXML
    private TextField RejestracjiaText;

    @FXML
    private TextField idText;
    @FXML
    private TextField SaearchBar;

    private  Samochody samochody;

    private List<Samochody> samochodylist;




    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
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

    ObservableList<Samochody> samochodyObservableList = FXCollections.observableArrayList();
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Samochody, Integer>("idSamochodu"));
        marka.setCellValueFactory(new PropertyValueFactory<Samochody, String>("Marka"));
        model.setCellValueFactory(new PropertyValueFactory<Samochody, String>("Model"));
        nrRejestracji.setCellValueFactory(new PropertyValueFactory<Samochody, String>("NrRejestracji"));
        cena.setCellValueFactory(new PropertyValueFactory<Samochody, String>("CenaZaDzien"));
        fetchData();
        for (Samochody temp : samochodylist) {
            samochodyObservableList.add(temp);
        }
        tabela.setItems(samochodyObservableList);

    }

    public void wybrane() {
        Samochody samochody1 = tabela.getSelectionModel().getSelectedItem();
        idText.setText(String.valueOf(samochody1.getIdSamochodu()));
        ModelText.setText(String.valueOf(samochody1.getModel()));
        MarkaText.setText(String.valueOf(samochody1.getMarka()));
        RejestracjiaText.setText(String.valueOf(samochody1.getNrRejestracji()));
        CenaText.setText(String.valueOf(samochody1.getCenaZaDzien()));
    }
    public void editData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Samochody samochody2 = new Samochody();

        samochody2.setIdSamochodu(Integer.parseInt(idText.getText()));
        samochody2.setModel(ModelText.getText());
        samochody2.setMarka(MarkaText.getText());
        samochody2.setNrRejestracji(RejestracjiaText.getText());
        samochody2.setCenaZaDzien(CenaText.getText());


        session.update(samochody2);
        transaction.commit();
        session.close();
    }
    public void wyczysc() {
        idText.setText("");
        ModelText.setText("");
        MarkaText.setText("");
        RejestracjiaText.setText("");
        CenaText.setText("");
    }
    public void addData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Samochody samochody2 = new Samochody();

        //samochody2.setIdSamochodu(Integer.parseInt(idText.getText()));
        samochody2.setModel(ModelText.getText());
        samochody2.setMarka(MarkaText.getText());
        samochody2.setNrRejestracji(RejestracjiaText.getText());
        samochody2.setCenaZaDzien(CenaText.getText());
        walidacja();

        if(isNrRejestracjiOK(RejestracjiaText.getText()) && isCenaOk(CenaText.getText())) {
            session.persist(samochody2);
            transaction.commit();
            session.close();
        }
    }

    public void dealeatData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Samochody samochody2 = new Samochody();

        samochody2.setIdSamochodu(Integer.parseInt(idText.getText()));
        samochody2.setModel(ModelText.getText());
        samochody2.setMarka(MarkaText.getText());
        samochody2.setNrRejestracji(RejestracjiaText.getText());
        samochody2.setCenaZaDzien(CenaText.getText());


        session.delete(samochody2);
        transaction.commit();
        session.close();
    }
    public void loadAllData() {
        fetchData();
        samochodyObservableList.removeAll(samochodyObservableList);
        for (Samochody temp : samochodylist) {
            samochodyObservableList.add(temp);
        }
    }
    public void wyszukiwanie() {
        FilteredList<Samochody> filteredList = new FilteredList<>(samochodyObservableList, e -> true);

        SaearchBar.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateKlientData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                if (predicateKlientData.getModel().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getMarka().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getNrRejestracji().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateKlientData.getCenaZaDzien().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateKlientData.getIdSamochodu()).toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<Samochody> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tabela.comparatorProperty());
            tabela.setItems(sortedList);
        });
    }

    private boolean isNrRejestracjiOK(String s){
        return s.matches("[AZ]{2}([AZ]|[09])[09]{4}") ;
    }
    private boolean isCenaOk(String s){
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    public void walidacja(){
        if(!isNrRejestracjiOK(RejestracjiaText.getText()) || RejestracjiaText.getText().equals("")){
            NLabel.setVisible(true);
            NLabel.setText("podałeś zła rejestracjie");
        }
        else if(isNrRejestracjiOK(RejestracjiaText.getText())){
            NLabel.setVisible(false);
        }
        if(!isCenaOk(CenaText.getText()) || CenaText.getText().equals("")){
            Clabel.setVisible(true);
            Clabel.setText("nie podałeś cyfry");
        }else if(isCenaOk(CenaText.getText())){
            Clabel.setVisible(false);
        }
    }


    @FXML
    private void pdfs()
            throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/car", "root", "");
            Statement stmt = con.createStatement();
            ResultSet query_set = stmt.executeQuery("SELECT * From Samochody");
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("samochody.pdf"));
            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(5);
            PdfPCell table_cell;

            while (query_set.next()) {
                String index = query_set.getString("idSamochodu");
                table_cell = new PdfPCell(new Phrase(index));
                my_report_table.addCell(table_cell);
                String imie = query_set.getString("Marka");
                table_cell = new PdfPCell(new Phrase(imie));
                my_report_table.addCell(table_cell);
                String nazwisko = query_set.getString("Model");
                table_cell = new PdfPCell(new Phrase(nazwisko));
                my_report_table.addCell(table_cell);
                String plec = query_set.getString("NrRejestracji");
                table_cell = new PdfPCell(new Phrase(plec));
                my_report_table.addCell(table_cell);
                String data = query_set.getString("CenaZaDzien");
                table_cell = new PdfPCell(new Phrase(data));
                my_report_table.addCell(table_cell);
            }
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();
            query_set.close();
            stmt.close();
            con.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (DocumentException e) {

            e.printStackTrace();
        }
    }
}
