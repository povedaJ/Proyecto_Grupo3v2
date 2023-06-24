package controller;

import domain.Binnacle;
import domain.List.CircularLinkedList;
import domain.List.ListException;
import domain.Security;
import domain.Supplier;
import domain.Tree.AVL;
import domain.Tree.TreeException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddSupplierController implements Initializable {


    @FXML
    private BorderPane bp;
    private CircularLinkedList userList = util.Utility.getSecurityList();
    private AVL supplierList = util.Utility.getSupplierAVL();

    private AVL binnacleList = util.Utility.getBinnacleAVL();

    private Security userInfo;
    @FXML
    private ImageView logoImagen;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField addressTextField;

    Alert alert;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    idTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                nameTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        phoneNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


        addressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                addressTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        this.alert = util.FXUtility.alert("Proveedores","Registrar nuevo Proveedor");
        this.supplierList = util.Utility.getSupplierAVL();
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        try {
//            binnacleList.add(new Binnacle(LocalDateTime.now(), userInfo.getUser(), "Salió de Añadir Proveedor"));
//            util.Utility.addreadBinnacleFromFile("Binnacle");
            Utility.addreadSuppliesFromFile("Supplier");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //    public void btnAñadir(ActionEvent actionEvent) throws IOException, ListException {
//
//        if (emptyFields()) {
//
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("");
//            alert.setHeaderText("Error");
//            alert.setContentText("No pueden haber campos vacíos");
//            alert.showAndWait();
//
//        } else {
//
//            Supplier s = new Supplier(Integer.parseInt(textFieldID.getText()), textFieldName.getText(), textFieldPhone.getText(), textFieldEmail.getText(), textFieldAddress.getText());
//            supplierList.add(s);
//
//            userInfo = (Security) userList.getNode(1).data;
//            binnacleList.add(new Binnacle(LocalDateTime.now(), userInfo.getUser(), "Se ha Añadido un nuevo Proveedor"));
//            Utility.setBinnacleAVL(binnacleList);
//            Utility.setSupplierAVL(supplierList);
//            //Utility.addreadSupplierFromFile("ProyectoGrupo_3/Supplier");
//            util.Utility.file(supplierList,"Supplier");
//            util.Utility.file(binnacleList,"Binnacle");
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//
//            alert.setTitle("");
//            alert.setHeaderText("");
//            alert.setContentText("Proveedor agregado con éxito");
//            alert.showAndWait();
//
//            textFieldID.setText("");
//            textFieldName.setText("");
//            textFieldPhone.setText("");
//            textFieldEmail.setText("");
//            textFieldAddress.setText("");
//
//
//        }
//    }
    public void btnRegresar(ActionEvent actionEvent) throws ListException, IOException {


        userInfo = (Security) userList.getNode(1).data;
        binnacleList.add(new Binnacle(LocalDateTime.now(), userInfo.getUser(), "Salió de Añadir Proveedor"));
        util.Utility.addreadBinnacleFromFile("Binnacle");
        util.Utility.file(binnacleList,"Binnacle");
        util.Utility.setBinnacleAVL(binnacleList);
        loadPage("mantProveedores.fxml");

    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean emptyFields() {
        if (idTextField.getText().equals("") || nameTextField.getText().equals("") || phoneNumberTextField.getText().equals("") || emailTextField.getText().equals("") || addressTextField.getText().equals("")) {
            return true;
        }
        return false;
    }

    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !nameTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty()
                && !emailTextField.getText().isEmpty() && !addressTextField.getText().isEmpty();
    }


    @FXML
    void numericOnly(KeyEvent event) {
        String input = event.getCharacter();
        if (!input.matches("\\d")) {
            event.consume();
        }
    }


    public void retunrOnAction(ActionEvent actionEvent) {
        loadPage("mantProveedores.fxml");
    }

    public void AgregarOnAction(ActionEvent actionEvent) {
        try {
            if (isValid() && util.Utility.isCorreo(emailTextField.getText())) {
                int id = Integer.parseInt(this.idTextField.getText());
                Supplier newSupplier = new Supplier(
                        id,
                        this.nameTextField.textProperty().getValue(),
                        this.phoneNumberTextField.textProperty().getValue(),
                        this.emailTextField.textProperty().getValue(),
                        this.addressTextField.textProperty().getValue());

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("New supplier added");
                if (supplierList.isEmpty() || !supplierList.contains(newSupplier)) {
                    supplierList.add(newSupplier);
                    util.Utility.file(supplierList, "Supplier");
                    btnClean();
                    System.out.println(supplierList.toString());
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("The supplier already exists in the list");
                    btnClean(); //llama al boton clean
                }
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Complete the form with \nthe information, please");
            }


                this.alert.showAndWait();

        } catch (TreeException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    void btnClean() {
        this.idTextField.clear();
        this.nameTextField.clear();
        this.phoneNumberTextField.clear();
        this.emailTextField.clear();
        this.addressTextField.clear();
    }
}
