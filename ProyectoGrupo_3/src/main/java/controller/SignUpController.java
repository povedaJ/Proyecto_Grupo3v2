package controller;

import domain.CircularLinkedList;
import domain.Customer;
import domain.ListException;
import domain.SinglyLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;

import java.io.IOException;
import java.util.Date;

public class SignUpController {
    @FXML
    private TextField addressTextField;

    @FXML
    private BorderPane bp;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;
    private SinglyLinkedList customerList;
    Alert alert;

    public void initialize() {
        //carga la lista de clientes
         this.customerList = util.Utility.getCustomerList();
        this.alert = util.FXUtility.alert("Sign up", "Add new customer...");
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
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
    void numericOnly(KeyEvent event) {
        String input = event.getCharacter();
        if (!input.matches("\\d")) {
            event.consume();
        }
    }

    @FXML
    void retunrOnAction(ActionEvent event) {
        loadPage("initial_view.fxml");
    }

    @FXML
    void signUpOnAction(ActionEvent event) {
        try {
            if (isValid()) {
                int id = Integer.parseInt(this.idTextField.getText());
                Customer newCustomer = new Customer(
                        id,
                        this.nameTextField.textProperty().getValue(),
                        this.phoneNumberTextField.textProperty().getValue(),
                        this.emailTextField.textProperty().getValue(),
                        this.addressTextField.textProperty().getValue());
                if (customerList.isEmpty()|| !customerList.contains(newCustomer)){
                    customerList.add(newCustomer);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("The client was registered, you will receive your username and password by email.");
                    //settear lista de utiliti, la global
                    btnClean(); //llama al boton clean
                    util.Utility.setCustomerList(customerList);
                }else{
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("The employee already exists in the list");
                    btnClean(); //llama al boton clean
                }
            } else {//alerta de complete los campos
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Complete the form with \nthe information, please");
            }
            alert.showAndWait();

        } catch (ListException ex) {
            System.out.println(ex.getMessage());
        }
    }//end

    void btnClean() {
        this.idTextField.clear();
        this.nameTextField.clear();
        this.phoneNumberTextField.clear();
        this.emailTextField.clear();
        this.addressTextField.clear();
    }

    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !nameTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty()
                && !emailTextField.getText().isEmpty() && !addressTextField.getText().isEmpty();
    }


}
