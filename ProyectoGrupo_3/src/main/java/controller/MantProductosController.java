package controller;

import domain.Customer;
import domain.List.ListException;
import domain.List.SinglyLinkedList;
import domain.Product;
import domain.Supplier;
import domain.Tree.AVL;
import domain.Tree.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class MantProductosController {

    @FXML
    private BorderPane bp;

    @FXML
    private TableColumn<Product, Integer> currentStockColumn;

    @FXML
    private TableColumn<Product, String> descrptionColumn;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TableColumn<Product, Integer> minimunStockColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> supplierColumn;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private Label txtErrorMessages;

    @FXML
    private Label txtMessages;
    private Alert alert;
    private TextInputDialog dialog;
    private static AVL supplierAVL = new AVL();//proveedores
    private static AVL productsAVL = new AVL();// Productos

    @FXML
    public void initialize() throws TreeException {
        this.supplierAVL = util.Utility.getSupplierAVL();
        this.productsAVL = util.Utility.getProductsAVL();
        try {
            Utility.addReadSuppliersFromFile("Supplier");
            Utility.addreadProductsFromFile("Product");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);

        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.descrptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.currentStockColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
        this.minimunStockColumn.setCellValueFactory(new PropertyValueFactory<>("minimumStock"));
        this.supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        updateTableView();

    }

    public void updateTableView() {
        tableView.getItems().clear();
        try {
            this.alert = util.FXUtility.alert("", "");
            alert.setAlertType(Alert.AlertType.ERROR);
            if (productsAVL != null && !productsAVL.isEmpty()) {
                int n = productsAVL.size();
                for (int i = 1; i <= n; i++) {
                    //System.out.println("que hay"+productsAVL.get(i-1));
                    this.tableView.getItems().add((Product)productsAVL.get(i-1));
                }
            }
        } catch (TreeException ex) {
            throw new RuntimeException(ex);
        }

    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        loadPage("addProduct.fxml");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {

        // Lógica para el botón Eliminar
        // Obtener el proveedor seleccionado en la tabla
        Product proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            // Confirmar la eliminación del proveedor
            boolean confirmacion = mostrarConfirmacion("Eliminar proveedor",
                    "¿Estás seguro de que deseas eliminar este proveedor?");

            if (confirmacion) {
                // Eliminar el proveedor de la lista observable del TableView
                tableView.getItems().remove(proveedorSeleccionado);
                try {
                    productsAVL.remove(proveedorSeleccionado);
                    Utility.file(productsAVL,"Supplier");
                } catch (IOException | TreeException e) {
                    throw new RuntimeException(e);
                }
                mostrarInformacion("Proveedor eliminado",
                        "El proveedor ha sido eliminado correctamente.");
            }
        } else {
            mostrarAlerta("Proveedor no seleccionado",
                    "Por favor, selecciona un proveedor de la tabla.");
        }
    }

    @FXML
    void editOnAction(ActionEvent event) throws TreeException {

        Product proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            productsAVL.remove(proveedorSeleccionado);
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar Productp");
            dialog.setHeaderText(null);
            dialog.setContentText("Ingresa los nuevos datos del producto:");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            TextField txtid= new TextField(String.valueOf(proveedorSeleccionado.getId()));
            txtid.setPromptText("id");
            TextField txtDescription = new TextField(proveedorSeleccionado.getDescription());
            txtDescription.setPromptText("description");
            TextField txtPrice = new TextField(String.valueOf(proveedorSeleccionado.getPrice()));
            txtPrice.setPromptText("price");
            TextField txtcurrentStock = new TextField(String.valueOf(proveedorSeleccionado.getCurrentStock()));
            txtcurrentStock.setPromptText("currentStock");
            TextField txtminimumStock = new TextField(String.valueOf(proveedorSeleccionado.getMinimumStock()));
            txtminimumStock.setPromptText("minimumStock");
            TextField txtsupplierId = new TextField(String.valueOf(proveedorSeleccionado.getSupplierId()));
            txtsupplierId.setPromptText("supplierId");

            gridPane.addRow(0, new Label("id:"), txtid);
            gridPane.addRow(1, new Label("description:"), txtDescription);
            gridPane.addRow(2, new Label("price:"), txtPrice);
            gridPane.addRow(3, new Label("currentStock:"), txtcurrentStock);
            gridPane.addRow(4, new Label("minimumStock:"), txtminimumStock);
            gridPane.addRow(5, new Label("supplierId:"), txtsupplierId);


            dialog.getDialogPane().setContent(gridPane);

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {

                int id = Integer.parseInt(txtid.getText());
                String description = txtDescription.getText();
                Double price = Double.parseDouble(txtPrice.getText());
                int currentStock  = Integer.parseInt(txtcurrentStock.getText());
                int minimumStock  = Integer.parseInt(txtminimumStock.getText());
                int supplierId  = Integer.parseInt(txtsupplierId.getText());


                proveedorSeleccionado.setId(id);
                proveedorSeleccionado.setDescription(description);
                proveedorSeleccionado.setPrice(price);
                proveedorSeleccionado.setCurrentStock(currentStock);
                proveedorSeleccionado.setMinimumStock(minimumStock);
                proveedorSeleccionado.setSupplierId(supplierId);


                productsAVL.remove(proveedorSeleccionado);
                productsAVL.add(proveedorSeleccionado);
                updateTable(productsAVL);

                try {
                    util.Utility.setSupplierAVL(productsAVL);
                    Utility.file(productsAVL,"Product");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mostrarInformacion("Proveedor actualizado", "El proveedor ha sido actualizado correctamente.");

            }

        } else {
            mostrarAlerta("Proveedor no seleccionado", "Por favor, selecciona un proveedor de la tabla.");
        }


    }
    private void updateTable(AVL supplierList) {
        tableView.getItems().clear();
        try {
            this.alert = util.FXUtility.alert("Lista de Productos", "Desplegar Productos");
            alert.setAlertType(Alert.AlertType.ERROR);

            if (supplierList != null && !supplierList.isEmpty()) {
                int n = supplierList.size();
                for (int i = 1; i < n; i++) {
                    this.tableView.getItems().add((Product) productsAVL.get(i));
                }
            } else {
                alert.setContentText("La lista de productos está vacía");
                alert.showAndWait();
            }
        } catch (TreeException e) {
            alert.setContentText("Ha ocurrido un error al actualizar la tabla");
            alert.showAndWait();
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

}