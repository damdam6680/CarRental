open
module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires com.calendarfx.view;
   // opens com.example.carrental to javafx.fxml;
    exports com.example.carrental;
    exports com.example.carrental.model;
    exports com.example.carrental.controler;
    //exports com.example.carrental.Controler;
    //  opens com.example.carrental.model to javafx.fxml;
    requires com.jfoenix;
    requires transitive javafx.graphics ;

}