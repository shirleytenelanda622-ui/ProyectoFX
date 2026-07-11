package org.example.proyectofx.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/proyectofx/view/login.fxml"));

        Scene scene = new Scene(root, 300, 543);
        scene.getStylesheets().add(getClass().getResource("/org/example/proyectofx/estilos/estilos.css").toExternalForm());

        stage.setTitle("Sistema Veterinaria - Login");
        stage.setScene(scene);
        stage.setMinWidth(420);
        stage.setMinHeight(380);
        stage.show();
    }
}
