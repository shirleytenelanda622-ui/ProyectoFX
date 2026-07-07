package org.example.proyectofx.controller;

import org.example.proyectofx.dao.UsuarioDAO;
import org.example.proyectofx.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


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
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        if(!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
            mostrarAlerta(Alert.AlertType.ERROR, "Datos incorrectos", "El nombre solo debe contener letras");
            return false;
        }
        if(!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            mostrarAlerta(Alert.AlertType.ERROR, "Correo incorrecto", "El correo debe estar completo");
            return false;
        }
        if(contrasena.length() < 6){
            mostrarAlerta(Alert.AlertType.ERROR, "Contraseña incompleta", "La contraseña debe contener al menos 6 caracteres");
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
