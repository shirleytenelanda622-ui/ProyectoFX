package org.example.proyectofx.controller;

import org.example.proyectofx.dao.MascotaDAO;
import org.example.proyectofx.dao.PropietarioDAO;
import org.example.proyectofx.model.Mascota;
import org.example.proyectofx.model.Propietario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

/**
 * Controlador del módulo CRUD principal: Mascotas.
 * Guardar, listar (con TableView), editar y eliminar, con conexión real a la BD.
 */
public class MascotasController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtRaza;
    @FXML private TextField txtEdad;
    @FXML private ComboBox<String> cbSexo;
    @FXML private TextField txtPeso;
    @FXML private ComboBox<Propietario> cbPropietario;

    @FXML private TableView<Mascota> tablaMascotas;
    @FXML private TableColumn<Mascota, Integer> colId;
    @FXML private TableColumn<Mascota, String> colNombre;
    @FXML private TableColumn<Mascota, String> colEspecie;
    @FXML private TableColumn<Mascota, String> colRaza;
    @FXML private TableColumn<Mascota, Integer> colEdad;
    @FXML private TableColumn<Mascota, String> colSexo;
    @FXML private TableColumn<Mascota, Double> colPeso;
    @FXML private TableColumn<Mascota, String> colPropietario;

    private final MascotaDAO mascotaDAO = new MascotaDAO();
    private final PropietarioDAO propietarioDAO = new PropietarioDAO();

    private Mascota mascotaSeleccionada;

    @FXML
    public void initialize() {
        cbSexo.setItems(FXCollections.observableArrayList("Macho", "Hembra"));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colRaza.setCellValueFactory(new PropertyValueFactory<>("raza"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colPropietario.setCellValueFactory(new PropertyValueFactory<>("nombrePropietario"));

        tablaMascotas.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccion) -> {
            if (seleccion != null) {
                cargarFormulario(seleccion);
            }
        });

        cargarPropietarios();
        cargarTabla();
    }

    private void cargarPropietarios() {
        ObservableList<Propietario> propietarios = FXCollections.observableArrayList(propietarioDAO.listar());
        cbPropietario.setItems(propietarios);
    }

    private void cargarTabla() {
        ObservableList<Mascota> mascotas = FXCollections.observableArrayList(mascotaDAO.listar());
        tablaMascotas.setItems(mascotas);
    }

    private void cargarFormulario(Mascota m) {
        mascotaSeleccionada = m;
        txtNombre.setText(m.getNombre());
        txtEspecie.setText(m.getEspecie());
        txtRaza.setText(m.getRaza());
        txtEdad.setText(String.valueOf(m.getEdad()));
        cbSexo.setValue(m.getSexo());
        txtPeso.setText(String.valueOf(m.getPeso()));

        for (Propietario p : cbPropietario.getItems()) {
            if (p.getId() == m.getIdPropietario()) {
                cbPropietario.setValue(p);
                break;
            }
        }
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        if (!validarFormulario()) return;

        Mascota m = new Mascota();
        m.setNombre(txtNombre.getText().trim());
        m.setEspecie(txtEspecie.getText().trim());
        m.setRaza(txtRaza.getText().trim());
        m.setEdad(Integer.parseInt(txtEdad.getText().trim()));
        m.setSexo(cbSexo.getValue());
        m.setPeso(Double.parseDouble(txtPeso.getText().trim()));
        m.setIdPropietario(cbPropietario.getValue().getId());

        if (mascotaDAO.existeNombreParaPropietario(m.getNombre(), m.getIdPropietario())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Registro duplicado",
                    "Ya existe una mascota con ese nombre para el propietario seleccionado.");
            return;
        }

        boolean exito = mascotaDAO.guardar(m);
        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Mascota registrada correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la mascota.");
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        if (mascotaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione una mascota de la tabla para editar.");
            return;
        }
        if (!validarFormulario()) return;

        mascotaSeleccionada.setNombre(txtNombre.getText().trim());
        mascotaSeleccionada.setEspecie(txtEspecie.getText().trim());
        mascotaSeleccionada.setRaza(txtRaza.getText().trim());
        mascotaSeleccionada.setEdad(Integer.parseInt(txtEdad.getText().trim()));
        mascotaSeleccionada.setSexo(cbSexo.getValue());
        mascotaSeleccionada.setPeso(Double.parseDouble(txtPeso.getText().trim()));
        mascotaSeleccionada.setIdPropietario(cbPropietario.getValue().getId());

        boolean exito = mascotaDAO.actualizar(mascotaSeleccionada);
        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Mascota actualizada correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la mascota.");
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        if (mascotaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione una mascota de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea eliminar la mascota \"" + mascotaSeleccionada.getNombre() + "\"?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            boolean exito = mascotaDAO.eliminar(mascotaSeleccionada.getId());
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Mascota eliminada correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la mascota.");
            }
        }
    }

    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        mascotaSeleccionada = null;
        txtNombre.clear();
        txtEspecie.clear();
        txtRaza.clear();
        txtEdad.clear();
        cbSexo.setValue(null);
        txtPeso.clear();
        cbPropietario.setValue(null);
        tablaMascotas.getSelectionModel().clearSelection();
    }

    /** Validaciones obligatorias: campos vacíos, tipo numérico y valores positivos. */
    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()
                || txtEspecie.getText() == null || txtEspecie.getText().trim().isEmpty()
                || txtEdad.getText() == null || txtEdad.getText().trim().isEmpty()
                || cbSexo.getValue() == null
                || txtPeso.getText() == null || txtPeso.getText().trim().isEmpty()
                || cbPropietario.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
                    "Todos los campos son obligatorios, incluyendo el propietario.");
            return false;
        }

        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Valor inválido", "La edad debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Tipo de dato inválido", "La edad debe ser un valor numérico.");
            return false;
        }

        try {
            double peso = Double.parseDouble(txtPeso.getText().trim());
            if (peso <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Valor inválido", "El peso debe ser un número positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Tipo de dato inválido", "El peso debe ser un valor numérico.");
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
