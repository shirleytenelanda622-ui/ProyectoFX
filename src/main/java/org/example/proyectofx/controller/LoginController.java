package org.example.proyectofx.controller;

import org.example.proyectofx.dao.UsuarioDAO;
import org.example.proyectofx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de la pantalla de Login.
 * Valida credenciales contra la base de datos y redirige al Dashboard
 * enviándole el objeto Usuario autenticado (así el dashboard sabe qué
 * mostrar según el rol).
 */
public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;
    @FXML private Button btnIngresar;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        lblMensaje.setText("");
    }

    @FXML
    private void onIngresar(ActionEvent event) {
        String correo = txtCorreo.getText() == null ? "" : txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText() == null ? "" : txtContrasena.getText().trim();

        // Validación: campos vacíos
        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Debe ingresar correo y contraseña.");
            return;
        }

        Usuario usuario = usuarioDAO.autenticar(correo, contrasena);

        if (usuario == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Credenciales inválidas",
                    "El correo o la contraseña son incorrectos.");
            return;
        }

        abrirDashboard(usuario, event);
    }

    private void abrirDashboard(Usuario usuario, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/veterinaria/view/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUsuario(usuario); // se pasa el objeto Usuario completo

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 650);
            scene.getStylesheets().add(getClass().getResource("/com/veterinaria/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Veterinaria - Dashboard (" + usuario.getRol() + ")");
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            stage.centerOnScreen();

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir el dashboard: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
