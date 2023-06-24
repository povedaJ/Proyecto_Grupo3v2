package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ucr.proyecto.HelloApplication;

import java.io.IOException;

public class InitialViewController {
    @FXML
    private BorderPane bp;
    @FXML
    private Pane pcentral;
    @FXML
    private ImageView logoImagen;
    @FXML
    private Label companylabel;

    @FXML
    public void initialize() {

        Image image = new Image(util.Utility.getRouteImagen());
        logoImagen.setImage(image);
        companylabel.setText(util.Utility.getNameApp());
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
    void logInOnAction(ActionEvent event) {
        loadPage("login.fxml");
    }

    @FXML
    void signUpOnAction(ActionEvent event) {
        loadPage("signUp.fxml");
    }

}
