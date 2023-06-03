package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;

import java.io.IOException;

public class InitialViewController {
    @FXML
    private BorderPane bp;
    @FXML
    private ImageView logoImagen;


    @FXML
    public void initialize() {

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
    void logInOnAction(ActionEvent event) {
        loadPage("login.fxml");
    }

    @FXML
    void signUpOnAction(ActionEvent event) {
        loadPage("signUp.fxml");
    }

}
