package org.example.proyectofx.controller;

import org.example.proyectofx.dao.PropietarioDAO;
import org.example.proyectofx.model.Propietario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

/**
 * Controlador del módulo CRUD de Propietarios (tabla relacionada con Mascotas).
 */
public class PropietariosController {

    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCorreo;

    @FXML private TableView<Propietario> tablaPropietarios;
    @FXML private TableColumn<Propietario, Integer> colId;
    @FXML private TableColumn<Propietario, String> colCedula;
    @FXML private TableColumn<Propietario, String> colNombre;
    @FXML private TableColumn<Propietario, String> colTelefono;
    @FXML private TableColumn<Propietario, String> colDireccion;
    @FXML private TableColumn<Propietario, String> colCorreo;

    private final PropietarioDAO propietarioDAO = new PropietarioDAO();
    private Propietario propietarioSeleccionado;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        tablaPropietarios.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccion) -> {
            if (seleccion != null) cargarFormulario(seleccion);
        });

        cargarTabla();
    }

    private void cargarTabla() {
        ObservableList<Propietario> lista = FXCollections.observableArrayList(propietarioDAO.listar());
        tablaPropietarios.setItems(lista);
    }

    private void cargarFormulario(Propietario p) {
        propietarioSeleccionado = p;
        txtCedula.setText(p.getCedula());
        txtNombre.setText(p.getNombre());
        txtTelefono.setText(p.getTelefono());
        txtDireccion.setText(p.getDireccion());
        txtCorreo.setText(p.getCorreo());
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (!validarFormulario()) return;

        if (propietarioDAO.existeCedula(txtCedula.getText().trim())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Registro duplicado", "Ya existe un propietario con esa cédula.");
            return;
        }

        Propietario p = new Propietario();
        p.setCedula(txtCedula.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setTelefono(txtTelefono.getText().trim());
        p.setDireccion(txtDireccion.getText().trim());
        p.setCorreo(txtCorreo.getText().trim());

        if (propietarioDAO.guardar(p)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Propietario registrado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el propietario.");
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        if (propietarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un propietario para editar.");
            return;
        }
        if (!validarFormulario()) return;

        propietarioSeleccionado.setCedula(txtCedula.getText().trim());
        propietarioSeleccionado.setNombre(txtNombre.getText().trim());
        propietarioSeleccionado.setTelefono(txtTelefono.getText().trim());
        propietarioSeleccionado.setDireccion(txtDireccion.getText().trim());
        propietarioSeleccionado.setCorreo(txtCorreo.getText().trim());

        if (propietarioDAO.actualizar(propietarioSeleccionado)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Propietario actualizado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el propietario.");
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        if (propietarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un propietario para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea eliminar al propietario \"" + propietarioSeleccionado.getNombre() + "\"?\n" +
                "Nota: no se puede eliminar si tiene mascotas registradas.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            if (propietarioDAO.eliminar(propietarioSeleccionado.getId())) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Propietario eliminado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error",
                        "No se pudo eliminar. Verifique que no tenga mascotas asociadas.");
            }
        }
    }

    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        propietarioSeleccionado = null;
        txtCedula.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtCorreo.clear();
        tablaPropietarios.getSelectionModel().clearSelection();
    }

    private boolean validarFormulario() {
        if (txtCedula.getText() == null || txtCedula.getText().trim().isEmpty()
                || txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Cédula y nombre son obligatorios.");
            return false;
        }
        if (!txtCedula.getText().trim().matches("\\d{10}")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato inválido", "La cédula debe tener 10 dígitos numéricos.");
            return false;
        }
        String correo = txtCorreo.getText().trim();
        if(!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$")){
            mostrarAlerta(Alert.AlertType.ERROR, "Correo incompleto", "Ingrese un correo valido");
            return false;
        }
        String telefono = txtTelefono.getText().trim();
        if(telefono.length() != 10){
            mostrarAlerta(Alert.AlertType.ERROR, "Telefono incompleto", "El telefono debe contner 10 parametros");
            return false;
        }
        String cedula = txtCedula.getText().trim();
        if(!telefono.matches("\\d+") || !cedula.matches("\\d+")){
            mostrarAlerta(Alert.AlertType.ERROR, "Valores incorrectos", "Debe ingresar solo números");
            return false;
        }
        String nombre = txtNombre.getText().trim();
        if(!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")){
            mostrarAlerta(Alert.AlertType.ERROR, "Datos incorrectos", "El nombre solo debe contener letras");
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
