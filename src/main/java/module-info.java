open
module com.example.carrental {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires com.calendarfx.view;
    exports com.example.carrental;
    exports com.example.carrental.model;
    exports com.example.carrental.controler;
    requires transitive javafx.graphics ;
    requires swingx;
    requires itextpdf;
    requires org.apache.commons.lang3;
}