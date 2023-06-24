package controller;

import domain.Customer;
import domain.List.ListException;
import domain.List.SinglyLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MantClientesController {

    @FXML
    private TableColumn<Customer, String> addressCTV;

    @FXML
    private BorderPane bp;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> emailCTV;

    @FXML
    private TableColumn<Customer, Integer> idCustomerTV;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TableColumn<Customer, String> nameCTV;

    @FXML
    private TableColumn<Customer, String> phoneCTV;
    private Alert alert;
    private Dialog dialog;
    private SinglyLinkedList customerList;

    @FXML
    public void initialize() throws ListException {
        //cargamos la lista desde la clase Utility
        customerList = util.Utility.getCustomerList();
        try {
            Utility.addReadCustomersFromFile("Customer");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        this.idCustomerTV.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameCTV.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.phoneCTV.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.emailCTV.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.addressCTV.setCellValueFactory(new PropertyValueFactory<>("address"));
        updateTableView(customerList);

    }

    public void updateTableView(SinglyLinkedList studentList) {
        try {
            this.alert = util.FXUtility.alert("Student List", "Display Students");
            alert.setAlertType(Alert.AlertType.ERROR);
            if (studentList != null && !studentList.isEmpty()) {
                int n = studentList.size();
                for (int i = 1; i <= n; i++) {
                    this.customerTableView.getItems().add((Customer) studentList.getNode(i).data);

                }
            }
        } catch (ListException ex) {
            alert.setContentText("Student list is empty");
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
    void addCustomerOnAction(ActionEvent event) {
        //loadPage("newCustomer.fxml");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void editOnAction(ActionEvent event) {

    }

}
