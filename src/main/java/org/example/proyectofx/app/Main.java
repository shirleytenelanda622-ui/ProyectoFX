package org.example.proyectofx.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicación JavaFX.
 * Carga la ventana de Login al iniciar.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/veterinaria/view/login.fxml"));

        Scene scene = new Scene(root, 480, 420);
        scene.getStylesheets().add(getClass().getResource("/com/veterinaria/css/styles.css").toExternalForm());

        stage.setTitle("Sistema Veterinaria - Login");
        stage.setScene(scene);
        stage.setMinWidth(420);
        stage.setMinHeight(380);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
