package controller;

import domain.Product;
import domain.Supplier;
import domain.Tree.AVL;
import domain.Tree.TreeException;
import domain.queue.HearderLinkedQueue;
import domain.queue.QueueException;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControlCostosController implements Initializable {

    private domain.queue.HearderLinkedQueue hLQ1 = util.Utility.getCostsControlQ();

    private HearderLinkedQueue hLQ2 = util.Utility.getCostsControlQ();
    @javafx.fxml.FXML
    private BorderPane bp;
    @FXML
    private TableView<List<String>> tableCosts;
    @FXML
    private TableColumn<List<String>, String> columnId;
    @FXML
    private TableColumn<List<String>, String> columnDescription;
    @FXML
    private TableColumn<List<String>, String> columnPrice;
    @FXML
    private TableColumn<List<String>, String> columnCurrentStock;
    @FXML
    private TableColumn<List<String>, String> colunmMinimunStock;
    @FXML
    private TableColumn<List<String>, String> columnSupplierId;
    @FXML
    private TableColumn<List<String>, String> columnTotalCosts;

    private double costsTotal;

    private AVL supplierName = new AVL();

    private AVL product = new AVL();
    @FXML
    private TextArea textAreaCosts;

    private AVL productList = util.Utility.getProductsAVL();

    private AVL supplierList = util.Utility.getSupplierAVL();


    @Override
    public void initialize(URL url, ResourceBundle rb) {   try {
        Utility.addReadSuppliersFromFile("Supplier");
    } catch (FileNotFoundException e) {
        // Manejar adecuadamente la excepción o mostrar un mensaje de error
        e.printStackTrace();
    }

//        Image image = new Image(util.Utility.getRouteImagen());
//        logoImagen.setImage(image);

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>(""));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        columnCurrentStock.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunmMinimunStock.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnSupplierId.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnTotalCosts.setCellValueFactory(new PropertyValueFactory<>("address"));

    }


    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRegresar(ActionEvent actionEvent) {
        loadPage("menuAdmin.fxml");
    }

    @Deprecated
    public void btnGenerarInforme(ActionEvent event) {

    }

    @Deprecated
    public void btnCostosTotales(ActionEvent actionEvent) {

        try {

            for (int i = 0; i < product.size(); i++) {
                Product p = (Product) product.get(i);
                hLQ1.enQueue(p.getPrice() * p.getCurrentStock());
            }

            costsTotal = 0.0;
            while (!hLQ1.isEmpty()) {
                Object element = hLQ1.front();
                double value = (double) element;
                costsTotal += value;
                hLQ2.enQueue(hLQ1.deQueue());
            }
            textAreaCosts.setText(String.valueOf(costsTotal));

            while (!hLQ2.isEmpty()) {
                hLQ1.enQueue(hLQ2.deQueue());
            }

        } catch (TreeException e) {
            throw new RuntimeException(e);
        } catch (QueueException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAñadirProducto(ActionEvent actionEvent) {


    }
}
