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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
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
    public boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initModality(Modality.APPLICATION_MODAL);

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        return alert.showAndWait().orElse(buttonTypeNo) == buttonTypeYes;
    }

    public void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
    /*    // Obtener los datos del nuevo proveedor desde la interfaz
        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String direccion = txtDireccion.getText(); */

        // Crear una nueva instancia de Supplier con los datos obtenidos
//        Supplier nuevoProveedor = new Supplier(id, nombre, telefono, correo, direccion);

        // Agregar el proveedor a la lista observable del TableView
//        tableView.getItems().add(nuevoProveedor);

        mostrarInformacion("Proveedor agregado", "El proveedor ha sido agregado correctamente.");
    }
    @FXML
    public void btnEliminar() {
        // Obtener el proveedor seleccionado en la tabla
        Supplier proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            // Confirmar la eliminación del proveedor
            boolean confirmacion = mostrarConfirmacion("Eliminar proveedor",
                    "¿Estás seguro de que deseas eliminar este proveedor?");

            if (confirmacion) {
                // Eliminar el proveedor de la lista observable del TableView
                tableView.getItems().remove(proveedorSeleccionado);

                mostrarInformacion("Proveedor eliminado",
                        "El proveedor ha sido eliminado correctamente.");
            }
        } else {
            mostrarAlerta("Proveedor no seleccionado",
                    "Por favor, selecciona un proveedor de la tabla.");
        }
    }

}
