package com.example.carrental.controler;
import com.example.carrental.model.Samochody;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InfoCarControler implements Initializable {

    @FXML
    private TextField idCenaZaDzien;

    @FXML
    private TextField idMarka;

    @FXML
    private TextField idModel;

    @FXML
    private TextField idNrRejestracji;

    @FXML
    private TextField idSamochodu;


    private static List<Samochody> samochodylist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void wypisz(){
        idSamochodu.setText(String.valueOf(samochodylist.get(0).getIdSamochodu()));
        idNrRejestracji.setText(String.valueOf(samochodylist.get(0).getNrRejestracji()));
        idModel.setText(String.valueOf(samochodylist.get(0).getModel()));
        idMarka.setText(String.valueOf(samochodylist.get(0).getMarka()));
        idCenaZaDzien.setText(String.valueOf(samochodylist.get(0).getCenaZaDzien()));

    }

    public static <T> List<T> loadAllData(Class<T> type, Session session,String id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        Query query = session.createQuery("from Samochody as s where s.NrRejestracji   = :id");
        query.setParameter("id",id);
        List<T> data = query.getResultList();
        return data;
    }
    public void fetchData(String id) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        samochodylist = loadAllData(Samochody.class, session,id);
        System.out.println(Arrays.toString(samochodylist.toArray()));
        System.out.println(samochodylist.isEmpty());
        transaction.commit();
        session.close();
    }


    public void Edytuj(ActionEvent actionEvent) {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Samochody.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Samochody samochody2 = new Samochody();

        samochody2.setIdSamochodu(Integer.parseInt(idSamochodu.getText()));
        samochody2.setModel(idModel.getText());
        samochody2.setMarka(idMarka.getText());
        samochody2.setNrRejestracji(idNrRejestracji.getText());
        samochody2.setCenaZaDzien(idCenaZaDzien.getText());


        session.update(samochody2);
        transaction.commit();
        session.close();
    }
}
