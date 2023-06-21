package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class NewProductController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField currentStockTextField;

    @FXML
    private TextArea descriptioTextArea;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField minimumStockTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private ChoiceBox<?> supplierIdChoiceBox;

    @FXML
    void numericOnly(KeyEvent event) {

    }

    @FXML
    void retunrOnAction(ActionEvent event) {

    }

    @FXML
    void signUpOnAction(ActionEvent event) {

    }

}
