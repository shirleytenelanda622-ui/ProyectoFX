package org.example.proyectofx.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/example/proyectofx/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 470);
        stage.setTitle("ProyectoFX");
        stage.setScene(scene);
        stage.show();
    }
}
