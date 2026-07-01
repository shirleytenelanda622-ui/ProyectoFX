package org.example.proyectofx.controller;

import org.example.proyectofx.dao.UsuarioDAO;
import org.example.proyectofx.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controlador del módulo "Gestión de usuarios". Visible solo para ADMIN
 * (el DashboardController se encarga de ocultar el botón para los demás roles).
 */
public class UsuariosController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cbRol;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colRol;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        cbRol.setItems(FXCollections.observableArrayList("ADMIN", "CAJERO", "REPORTES"));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        cargarTabla();
    }

    private void cargarTabla() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList(usuarioDAO.listar());
        tablaUsuarios.setItems(lista);
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (!validarFormulario()) return;

        Usuario u = new Usuario();
        u.setNombre(txtNombre.getText().trim());
        u.setCorreo(txtCorreo.getText().trim());
        u.setContrasena(txtContrasena.getText().trim());
        u.setRol(cbRol.getValue());

        if (usuarioDAO.guardar(u)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario creado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el usuario (¿correo repetido?).");
        }
    }

    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        cbRol.setValue(null);
    }

    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()
                || txtCorreo.getText() == null || txtCorreo.getText().trim().isEmpty()
                || txtContrasena.getText() == null || txtContrasena.getText().trim().isEmpty()
                || cbRol.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Todos los campos son obligatorios.");
            return false;
        }
        if (txtContrasena.getText().trim().length() < 6) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña débil", "La contraseña debe tener al menos 6 caracteres.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
