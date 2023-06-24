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
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import ucr.proyecto.HelloApplication;

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Supplier s = new Supplier(1,"Nayeli","85211401","sebas@gamil.com","100m Sur");
        Supplier s1 = new Supplier(2,"Sebas","85211401","sebas@gamil.com","100m Sur");
        Supplier s2 = new Supplier(3,"Kendy","85211401","sebas@gamil.com","100m Sur");
        Supplier s3 = new Supplier(4,"Estela","85211401","sebas@gamil.com","100m Sur");

        supplierName.add(s);
        supplierName.add(s1);
        supplierName.add(s2);
        supplierName.add(s3);


        product = new AVL();
        Product p1 = new Product(18,"Martillo", 20.000, 4, 1, 1);
        Product p2 = new Product(19,"Tornillo", 20.000, 4, 1, 1);

        product.add(p1);
        product.add(p2);
        this.columnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(0));
            }
        });

        this.columnDescription.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(1));
            }
        });

        this.columnPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(2));
            }
        });

        this.columnCurrentStock.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(3));
            }
        });

        this.colunmMinimunStock.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(4));
            }
        });

        this.columnSupplierId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(5));
            }
        });

        this.columnTotalCosts.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<List<String>, String> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue().get(6));
            }
        });

        if (!product.isEmpty()) {
            try {
                tableCosts.setItems(getData());
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ObservableList<List<String>> getData() throws TreeException {


        ObservableList<List<String>> data = FXCollections.observableArrayList();

        for (int i = 0; i < this.product.size(); i++) {

            List<String> arrayList = new ArrayList<>();
            Product pr = (Product) product.get(i);
            arrayList.add(String.valueOf(pr.getId()));
            arrayList.add(pr.getDescription());
            arrayList.add("₡" + pr.getPrice());
            arrayList.add(String.valueOf(pr.getCurrentStock()));
            arrayList.add(String.valueOf(pr.getMinimumStock()));

            for (int j = 0; j < supplierName.size(); j++) {
                Supplier su = (Supplier) supplierName.get(j);
                if (String.valueOf(su.getId()).equals(pr.getSupplierId()))
                    arrayList.add(su.getName());
            }

            try {
                hLQ1.enQueue(pr.getPrice() * pr.getCurrentStock());
                arrayList.add("₡" + hLQ1.deQueue());
            } catch (QueueException e) {
                throw new RuntimeException(e);

            }
            data.add(arrayList);
        }
        return data;
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
