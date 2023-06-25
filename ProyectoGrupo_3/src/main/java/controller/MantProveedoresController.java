package controller;

import domain.Customer;
import domain.List.ListException;
import domain.Product;
import domain.Tree.AVL;
import domain.Tree.TreeException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import domain.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class MantProveedoresController {

    @FXML
    private BorderPane bp;

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
    private ImageView logoImagen;

    TextInputDialog busc = new TextInputDialog("");
    TextInputDialog nameTID = new TextInputDialog("");
    TextInputDialog idTID = new TextInputDialog("");
    TextInputDialog phoneNumberTID = new TextInputDialog("");
    TextInputDialog emailTID = new TextInputDialog("");
    TextInputDialog addressTID = new TextInputDialog("");
    private boolean compare;
    private String d;

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    @FXML
    private Label txtMessages;

    @FXML
    private Label txtErrorMessages;

    @FXML
    private TableColumn<Supplier, String> emailColumn;

    private AVL supplierList = util.Utility.getSupplierAVL();

    private Alert alert;

    @FXML
    public void initialize() {
        try {
            Utility.addReadSuppliersFromFile("Supplier");
        } catch (FileNotFoundException e) {
            // Manejar adecuadamente la excepción o mostrar un mensaje de error
            e.printStackTrace();
        }

        Image image = new Image(util.Utility.getRouteImagen());
        logoImagen.setImage(image);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        updateTable();
    }

    private void updateTable() {
        try {
            this.alert = util.FXUtility.alert("Student List", "Display Students");
            alert.setAlertType(Alert.AlertType.ERROR);

            if (supplierList != null && !supplierList.isEmpty()) {
                int n = supplierList.size();
                for (int i = 1; i < n; i++) {
                    this.tableView.getItems().add((Supplier) supplierList.get(i));
                }
            } else {
                alert.setContentText("Supplier list is empty");
                alert.showAndWait();
            }
        } catch (TreeException e) {
            alert.setContentText("Error occurred while updating the table");
            alert.showAndWait();
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
    public void returnOnAction() {
        loadPage("menuAdmin.fxml");
    }


    @FXML
    public void btnActualizar(ActionEvent event) throws TreeException {
        busc.setTitle("Modificar curso");
        busc.setHeaderText("Ingrese el ID del curso a modificar:");
        busc.setContentText("");

        Optional<String> result = busc.showAndWait();
        if (result.isPresent()) {

            try {
                this.d=result.get();

            } catch (NumberFormatException ex) {

            }
        }
        compare = (supplierList.contains(new Supplier(id, "", "", "", "")));
        if (compare == true) {
            supplierList.remove(new Supplier(id, name, phoneNumber, email, address));
            nameTID.setTitle("Agregar datos");
            nameTID.setHeaderText("Ingrese el nombre del curso :");
            nameTID.setContentText("");

            Optional<String> result9 = nameTID.showAndWait();
            if (result.isPresent()) {

                try {
                    this.name = result9.get();

                } catch (NumberFormatException ex) {

                }
            }

            phoneNumberTID.setTitle("Agregar datos");
            phoneNumberTID.setHeaderText("Ingrese los creditos del curso :");
            phoneNumberTID.setContentText("");

            Optional<String> result29 = phoneNumberTID.showAndWait();
            if (result.isPresent()) {

                try {
                    this.phoneNumber = result29.get();

                } catch (NumberFormatException ex) {

                }
            }


            emailTID.setTitle("Agregar datos");
            emailTID.setHeaderText("Ingrese los creditos del curso :");
            emailTID.setContentText("");

            Optional<String> result299 = emailTID.showAndWait();
            if (result.isPresent()) {

                try {
                    this.email = result299.get();

                } catch (NumberFormatException ex) {

                }
            }

            addressTID.setTitle("Agregar datos");
            addressTID.setHeaderText("Ingrese los creditos del curso :");
            addressTID.setContentText("");

            Optional<String> result2999 = addressTID.showAndWait();
            if (result.isPresent()) {

                try {
                    this.address = result2999.get();

                } catch (NumberFormatException ex) {

                }
            }


        }
    }

    @FXML
    public void btnAgregar() {
        loadPage("AddSupplier.fxml");
    }

    private String eliminar;
    @FXML
    public void btnEliminar() throws TreeException {
        busc.setTitle("Eliminar proveedor");
        busc.setHeaderText("Ingrese el ID del proveedor a borrar:");
        busc.setContentText("");

        Optional<String> result5 = busc.showAndWait();
        if (result5.isPresent()) {

            try {
                this.eliminar = result5.get();

            } catch (NumberFormatException ex) {

            }
        }
        supplierList.remove(new Supplier(id,name,phoneNumber,email,address));
        txtMessages.setVisible(true);
        txtErrorMessages.setVisible(false);
        if (supplierList.isEmpty()) {

        } else {
            try {
                Supplier supplier = new Supplier((Supplier)supplierList.get(1));

                for (int j = 0; j <= tableView.getItems().size(); j++) {
                    this.tableView.getItems().clear();
                }
                for (int i = 1; i <= supplierList.size(); i++) {
                    supplier = new Supplier((Supplier)supplierList.get(i));
                    this.tableView.getItems().add(supplier);
                    this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    this.nombreColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    this.telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                    this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
                    this.direccionColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
                }
                this.tableView.setVisible(true);
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }

    }


}

