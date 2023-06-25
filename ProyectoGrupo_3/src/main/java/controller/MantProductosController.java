package controller;

import domain.Customer;
import domain.List.ListException;
import domain.List.SinglyLinkedList;
import domain.Product;
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
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;

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
            this.alert = util.FXUtility.alert("Student List", "Display Students");
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

    }

    @FXML
    void editOnAction(ActionEvent event) {

    }

}
