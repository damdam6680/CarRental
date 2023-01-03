open
module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;

   // opens com.example.carrental to javafx.fxml;
    exports com.example.carrental;
    exports com.example.carrental.model;
    //exports com.example.carrental.Controler;
    //  opens com.example.carrental.model to javafx.fxml;


}