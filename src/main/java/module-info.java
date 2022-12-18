module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.carrental to javafx.fxml;
    exports com.example.carrental;
    exports com.example.carrental.model;
    opens com.example.carrental.model to javafx.fxml;
    exports com.example.carrental.util;
    opens com.example.carrental.util to javafx.fxml;
}