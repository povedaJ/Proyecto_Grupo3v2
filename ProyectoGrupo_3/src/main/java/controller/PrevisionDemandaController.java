package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import domain.Product;
import ucr.proyecto.HelloApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrevisionDemandaController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> currentStockColumn;
    @FXML
    private TableColumn<Product, Integer> minimumStockColumn;
    @FXML
    private TableColumn<Product, Integer> supplierIdColumn;
    @FXML
    private Label titleLabel;
    @FXML
    private Button backButton;

    private ObservableList<Product> products;
    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar la lista de productos
        products = FXCollections.observableArrayList();

        // Configurar las columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        currentStockColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        minimumStockColumn.setCellValueFactory(new PropertyValueFactory<>("minimumStock"));
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        // Cargar los productos en la tabla
        loadProducts();

        // Establecer el controlador para el botón de regreso
        backButton.setOnAction(event -> btnRegresar());
    }

    private void loadProducts() {
        // Aquí debes implementar la lógica para cargar los productos desde la base de datos o cualquier otra fuente de datos
        // y agregarlos a la lista de productos (variable 'products')

        // Ejemplo de carga de productos de prueba
        products.add(new Product("Producto 1", 10.99, 100, 50, 10));
        products.add(new Product("Producto 3", 10.99, 100, 50, 10));
        products.add(new Product("Producto 2", 10.99, 100, 50, 10));

        // Mostrar los productos en la tabla
        tableView.setItems(products);
    }

    @FXML
    private void btnRegresar() {
        loadPage("menuAdmin.fxml");
    }
    @FXML
    private void btnActualizar() {
        // Obtener el producto seleccionado de la tabla
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Mostrar el cuadro de diálogo para actualizar el stock actual
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Stock");
            dialog.setHeaderText(selectedProduct.getDescription());
            dialog.setContentText("Ingrese la cantidad de stock actual:");

            // Obtener el resultado del cuadro de diálogo
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(stock -> {
                // Validar y actualizar el stock actual del producto
                try {
                    int stockValue = Integer.parseInt(stock);
                    selectedProduct.setCurrentStock(stockValue);

                } catch (NumberFormatException e) {
                    // Manejar el caso en el que se ingrese un valor no válido
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Valor inválido");
                    alert.setContentText("Ingrese un valor numérico válido para el stock actual.");
                    alert.showAndWait();
                }
            });
        } else {
            // Si no se selecciona ningún producto, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Producto no seleccionado");
            alert.setContentText("Por favor, seleccione un producto de la tabla.");
            alert.showAndWait();
        }
    }
}