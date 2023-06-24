package controller;

import domain.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import domain.Supplier;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;

import java.io.IOException;

public class MantProveedoresController {
    private BorderPane bp;
    private ObservableList<Product> products;
    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Supplier> tableView;

    @FXML
    private TableColumn<Supplier, Integer> idColumn;

    @FXML
    private TableColumn<Supplier, String> nombreColumn;

    @FXML
    private TableColumn<Supplier, String> direccionColumn;

    @FXML
    private TableColumn<Supplier, String> telefonoColumn;

    @FXML
    public void initialize() {
        // Configurar las celdas de las columnas con las propiedades del supplier
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    @FXML
    public void btnRegresar() {
        loadPage("menuAdmin.fxml");
    }

    @FXML
    public void btnActualizar() {
        // Lógica para el botón Actualizar
    }

    @FXML
    public void btnAgregar() {
        // Lógica para el botón Agregar
    }

    @FXML
    public void btnEliminar() {
        // Lógica para el botón Eliminar
    }
}
