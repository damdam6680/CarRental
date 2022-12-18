package com.example.carrental;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

public class HelloController {

    @FXML
    private BorderPane root;


    @FXML
    public void printHelloWorld(ActionEvent actionEvent) throws IOException {

        Pane v = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Samochody.fxml")));
        root.setCenter(v);
    }

    public void OpenHome(ActionEvent actionEvent) throws IOException {

        Pane v = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        root.setCenter(v);

    }

    public void OpenRents(ActionEvent actionEvent) throws IOException {
        Pane v = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("wynajem.fxml")));
        root.setCenter(v);
    }
}