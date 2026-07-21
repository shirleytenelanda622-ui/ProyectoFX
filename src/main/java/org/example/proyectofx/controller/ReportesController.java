package org.example.proyectofx.controller;

import javafx.scene.control.TableCell;
import org.example.proyectofx.dao.ConsultaDAO;
import org.example.proyectofx.model.Consulta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofx.model.Mascota;

import java.text.NumberFormat;
import java.util.Locale;

public class ReportesController {

    @FXML private Label lblTotalConsultas;
    @FXML private Label lblTotalMascotas;
    @FXML private Label lblIngresoTotal;

    @FXML private TableView<Consulta> tablaConsultas;
    @FXML private TableColumn<Consulta, Integer> colId;
    @FXML private TableColumn<Consulta, String> colFecha;
    @FXML private TableColumn<Consulta, String> colMascota;
    @FXML private TableColumn<Consulta, String> colPropietario;
    @FXML private TableColumn<Consulta, String> colDiagnostico;
    @FXML private TableColumn<Consulta, String> colTratamiento;
    @FXML private TableColumn<Consulta, Double> colCosto;
    @FXML private TableColumn<Consulta, String> colUsuario;

    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colMascota.setCellValueFactory(new PropertyValueFactory<>("nombreMascota"));
        colPropietario.setCellValueFactory(new PropertyValueFactory<>("nombrePropietario"));
        colDiagnostico.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        colTratamiento.setCellValueFactory(new PropertyValueFactory<>("tratamiento"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));

        cargarReporte();
    }

    private void cargarReporte() {
        ObservableList<Consulta> lista = FXCollections.observableArrayList(consultaDAO.listarReporte());
        tablaConsultas.setItems(lista);

        NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "EC"));

        lblTotalConsultas.setText(String.valueOf(consultaDAO.contarConsultas()));
        lblTotalMascotas.setText(String.valueOf(consultaDAO.contarMascotas()));
        lblIngresoTotal.setText(moneda.format(consultaDAO.sumarIngresos()));
    }

    @FXML
    private void onActualizar() {
        cargarReporte();
    }
}
