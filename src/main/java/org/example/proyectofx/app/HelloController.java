package org.example.proyectofx.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private ComboBox<String> comboRol;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        comboRol.getItems().addAll(
                "Aministrador",
                "Cajero",
                "Reportes"
        );
    }
}
