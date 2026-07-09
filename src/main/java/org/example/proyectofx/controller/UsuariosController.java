package org.example.proyectofx.controller;

import org.example.proyectofx.dao.UsuarioDAO;
import org.example.proyectofx.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;


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
    private Usuario usuarioSeleccionado;

    @FXML
    public void initialize() {
        cbRol.setItems(FXCollections.observableArrayList("ADMIN", "CAJERO", "REPORTES"));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccion) -> {
            if (seleccion != null) cargarFormulario(seleccion);
        });

        cargarTabla();
    }

    private void cargarTabla() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList(usuarioDAO.listar());
        tablaUsuarios.setItems(lista);
    }

    private void cargarFormulario(Usuario u) {
        usuarioSeleccionado = u;
        txtNombre.setText(u.getNombre());
        txtCorreo.setText(u.getCorreo());
        txtContrasena.clear();
        txtContrasena.setPromptText("Dejar en blanco para no cambiarla");
        cbRol.setValue(u.getRol());
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (!validarFormulario(true)) return;

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
    private void onActualizar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un usuario de la tabla para editar.");
            return;
        }
        if (!validarFormulario(false)) return;

        usuarioSeleccionado.setNombre(txtNombre.getText().trim());
        usuarioSeleccionado.setCorreo(txtCorreo.getText().trim());
        usuarioSeleccionado.setRol(cbRol.getValue());


        if (usuarioDAO.actualizar(usuarioSeleccionado)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario actualizado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el usuario.");
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un usuario de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea eliminar al usuario \"" + usuarioSeleccionado.getNombre() + "\"?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (usuarioDAO.eliminar(usuarioSeleccionado.getId())) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario.");
            }
        }
    }

    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        usuarioSeleccionado = null;
        txtNombre.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        txtContrasena.setPromptText("Mínimo 6 caracteres");
        cbRol.setValue(null);
        tablaUsuarios.getSelectionModel().clearSelection();
    }


    private boolean validarFormulario(boolean esCreacion) {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()
                || txtCorreo.getText() == null || txtCorreo.getText().trim().isEmpty()
                || cbRol.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Nombre, correo y rol son obligatorios.");
            return false;
        }

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Datos incorrectos", "El nombre solo debe contener letras");
            return false;
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Correo incorrecto", "El correo debe estar completo");
            return false;
        }
        if (esCreacion && contrasena.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "La contraseña es obligatoria para crear un usuario.");
            return false;
        }
        if (!contrasena.isEmpty() && contrasena.length() < 6) {
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
