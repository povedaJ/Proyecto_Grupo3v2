package controller;

import domain.Product;
import domain.Supplier;
import domain.Tree.AVL;
import domain.Tree.TreeException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;

public class addProductController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField currentStockTextField;

    @FXML
    private TextField descripcionTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField minStockTextField;

    @FXML
    private TextField precioTextField;

    @FXML
    private ChoiceBox<Integer> suplierCB;
    private static AVL supplierAVL = new AVL();//proveedores
    private static AVL productsAVL = new AVL();// Productos
    Alert alert;


    public void initialize() {
        this.supplierAVL = util.Utility.getSupplierAVL();
        this.productsAVL = util.Utility.getProductsAVL();
        this.alert = util.FXUtility.alert("Producto ", "Añadir producto...");
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        try {

            Utility.addReadSuppliersFromFile("Supplier");
            Utility.addreadProductsFromFile("Product");

            if (supplierAVL != null && !supplierAVL.isEmpty()) {
                int n = supplierAVL.size();
                for (int i = 0; i < n; i++) {
                    Supplier sCB = (Supplier) supplierAVL.get(i);
                    suplierCB.getItems().add(sCB.getId());
                    //this.tableView.getItems().add((Supplier) supplierAVL.get(i));
                }
            } else {
                alert.setContentText("Supplier list is empty");
                alert.showAndWait();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
        idTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    idTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    void addOnAction(ActionEvent event) {
        try {
            if (isValid()) {
                int id = Integer.parseInt(this.idTextField.getText());
                int currentStock = Integer.parseInt(this.currentStockTextField.getText());
                int minimunStock = Integer.parseInt(this.minStockTextField.getText());
                double price = Double.parseDouble(this.precioTextField.getText());
                int selecSupplier = suplierCB.getValue();
                Product newProduct = new Product(id,
                        descripcionTextField.getText(), price, currentStock,
                        minimunStock, selecSupplier);

                if (productsAVL.isEmpty() || !productsAVL.contains(newProduct.getId())) {
                    productsAVL.add(newProduct);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("El producto fue añadido correctamente");
                    util.Utility.file(productsAVL, "Product");
                    btnClean();
                    // loadPage("mantProductos.fxml");
                    //System.out.println("PRODI"+productsAVL.toString());
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("El producto  ya existe");
                    btnClean(); //llama al boton clean
                }
            } else {//alerta de complete los campos
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Por favor complete toda las información del formulario");
            }
            alert.showAndWait();

        } catch (TreeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void numericOnly(KeyEvent event) {

    }
    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !descripcionTextField.getText().isEmpty() && !currentStockTextField.getText().isEmpty()
                && !minStockTextField.getText().isEmpty() && !precioTextField.getText().isEmpty() & suplierCB.getValue() != null;
    }
    void btnClean() {
        this.idTextField.clear();
        this.descripcionTextField.clear();
        this.currentStockTextField.clear();
        this.minStockTextField.clear();
        this.precioTextField.clear();
        this.suplierCB.setValue(null);
    }
    @FXML
    void returnOnAction(ActionEvent event) {
        loadPage("mantProductos.fxml");
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
