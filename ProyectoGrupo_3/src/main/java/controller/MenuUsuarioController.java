package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.proyecto.HelloApplication;

import java.io.IOException;

public class MenuUsuarioController {

        @FXML
        private BorderPane bp;

        @FXML
        private Text txtMessage;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        @FXML
        void Exit(ActionEvent event) {
            System.exit(0);
        }

        @FXML
        void Home(ActionEvent event) {

        }

        @FXML
        void gestionPedidosOnAction(ActionEvent event) {
loadPage("gestionPedidos.fxml");
        }

        @FXML
        void perfilOnAction(ActionEvent event) {

        }

        @FXML
        void reportePedidosOnAction(ActionEvent event) {

        }

    }

