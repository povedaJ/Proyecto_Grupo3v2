package controller;

import domain.Product;
import domain.Supplier;
import domain.Tree.AVL;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class newProductController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField currentStockTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;
    Alert alert;
    private static AVL supplierAVL = new AVL();//proveedores
    private static AVL productsAVL = new AVL();// Productos
    @FXML
    private TextField minimunStockTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private ChoiceBox<Supplier> supplierChoiceBox;


    public void initialize() {

        //carga la lista de clientes
        this.supplierAVL = util.Utility.getSupplierAVL();
        this.productsAVL = util.Utility.getProductsAVL();

        this.alert = util.FXUtility.alert("Producto ", "Añadir producto...");
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        try {
            if (!productsAVL.isEmpty()) {
                Utility.addreadProductsFromFile("Products");
            }if (!supplierAVL.isEmpty()){
                Utility.addReadSuppliersFromFile("Supplier");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void retunrOnAction(ActionEvent event) {

    }

    @FXML
    void addOnAction(ActionEvent event) {

    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !descriptionTextField.getText().isEmpty() && !currentStockTextField.getText().isEmpty()
                && !minimunStockTextField.getText().isEmpty() && !priceTextField.getText().isEmpty() & supplierChoiceBox.getValue() != null;
    }

    void btnClean() {
        this.idTextField.clear();
        this.descriptionTextField.clear();
        this.currentStockTextField.clear();
        this.minimunStockTextField.clear();
        this.priceTextField.clear();
        this.supplierChoiceBox.setValue(null);
    }

    @FXML
    void numericOnly(KeyEvent event) {

    }

}
