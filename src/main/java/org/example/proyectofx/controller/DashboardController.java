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

/**
 * Controlador del Dashboard ÚNICO y REUTILIZABLE.
 *
 * No existen tres dashboards distintos: existe un solo dashboard.fxml
 * cuyo contenido (menú lateral y contenido central) se activa/desactiva
 * dinámicamente según el rol del Usuario recibido mediante setUsuario().
 *
 * Esto es reutilización de componentes aplicando POO (composición +
 * uso del objeto Usuario).
 */
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

    /**
     * Recibe el objeto Usuario autenticado desde el LoginController
     * y adapta la interfaz según su rol.
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        lblNombreUsuario.setText(usuario.getNombre());
        lblRolUsuario.setText(usuario.getRol());
        adaptarMenuSegunRol();
    }

    /**
     * Un Administrador ve todo; un Cajero ve solo operaciones (mascotas /
     * propietarios); Reportes solo ve estadísticas. Esto se logra mostrando
     * u ocultando botones del mismo dashboard.fxml, sin duplicar pantallas.
     */
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

        // Al entrar, cargar una vista inicial según el rol
        if (esAdmin || esCajero) {
            cargarVista("/com/veterinaria/view/mascotas.fxml");
        } else {
            mostrarMensajeBienvenidaReportes();
        }
    }

    @FXML
    private void onMascotas(ActionEvent event) {
        cargarVista("/com/veterinaria/view/mascotas.fxml");
    }

    @FXML
    private void onPropietarios(ActionEvent event) {
        cargarVista("/com/veterinaria/view/propietarios.fxml");
    }

    @FXML
    private void onUsuarios(ActionEvent event) {
        cargarVista("/com/veterinaria/view/usuarios.fxml");
    }

    @FXML
    private void onReportes(ActionEvent event) {
        mostrarMensajeBienvenidaReportes();
    }

    private void mostrarMensajeBienvenidaReportes() {
        Label placeholder = new Label("Módulo de Reportes en construcción (próxima entrega).");
        placeholder.getStyleClass().add("placeholder-label");
        contenedorPrincipal.setCenter(placeholder);
    }

    /**
     * Carga el contenido central del dashboard reutilizando el mismo
     * BorderPane: cambia solo lo que va en el "centro", sin abrir
     * ventanas ni pantallas nuevas.
     */
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
            Parent root = FXMLLoader.load(getClass().getResource("/com/veterinaria/view/login.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 480, 420);
            scene.getStylesheets().add(getClass().getResource("/com/veterinaria/css/styles.css").toExternalForm());
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
