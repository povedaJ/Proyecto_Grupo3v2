package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;

import java.io.IOException;

public class MenuUsuarioController {

    @javafx.fxml.FXML
    private BorderPane bp;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
