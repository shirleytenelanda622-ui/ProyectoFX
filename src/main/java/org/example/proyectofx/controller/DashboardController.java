package org.example.proyectofx.controller;

import org.example.proyectofx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML private Label lblNombreUsuario;
    @FXML private Label lblRolUsuario;

    @FXML private Button btnMascotas;
    @FXML private Button btnPropietarios;
    @FXML private Button btnUsuarios;
    @FXML private Button btnReportes;
    @FXML private Button btnCerrarSesion;

    @FXML private BorderPane contenedorPrincipal;

    private Usuario usuarioActual;

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        lblNombreUsuario.setText(usuario.getNombre());
        lblRolUsuario.setText(usuario.getRol());
        adaptarMenuSegunRol();
    }

    private void adaptarMenuSegunRol() {
        boolean esAdmin = usuarioActual.esAdmin();
        boolean esCajero = usuarioActual.esCajero();
        boolean esReportes = usuarioActual.esReportes();

        btnMascotas.setVisible(esAdmin || esCajero);
        btnMascotas.setManaged(esAdmin || esCajero);

        btnPropietarios.setVisible(esAdmin || esCajero);
        btnPropietarios.setManaged(esAdmin || esCajero);

        btnUsuarios.setVisible(esAdmin);
        btnUsuarios.setManaged(esAdmin);

        btnReportes.setVisible(esAdmin || esReportes);
        btnReportes.setManaged(esAdmin || esReportes);

        if (esAdmin || esCajero) {
            cargarVista("/org/example/proyectofx/view/mascotas.fxml");
        } else if (esReportes) {
            cargarVista("/org/example/proyectofx/view/reportes.fxml");
        }
    }

    @FXML
    private void onMascotas(ActionEvent event) {
        cargarVista("/org/example/proyectofx/view/mascotas.fxml");
    }

    @FXML
    private void onPropietarios(ActionEvent event) {
        cargarVista("/org/example/proyectofx/view/propietarios.fxml");
    }

    @FXML
    private void onUsuarios(ActionEvent event) {
        cargarVista("/org/example/proyectofx/view/usuarios.fxml");
    }

    @FXML
    private void onReportes(ActionEvent event) {
        cargarVista("/org/example/proyectofx/view/reportes.fxml");
    }

    private void cargarVista(String rutaFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Node vista = loader.load();
            contenedorPrincipal.setCenter(vista);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista: " + e.getMessage());
        }
    }

    @FXML
    private void onCerrarSesion(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar sesión");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que desea cerrar sesión?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta.getButtonData().isDefaultButton()) {
                volverAlLogin(event);
            }
        });
    }

    private void volverAlLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/proyectofx/view/login.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 480, 420);
            scene.getStylesheets().add(getClass().getResource("/org/example/proyectofx/estilos/estilos.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Veterinaria - Login");
            stage.centerOnScreen();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver al login: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
