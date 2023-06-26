package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuUsuarioController {


    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private ImageView logoImagen;


    @FXML
    private BorderPane pj;
        @FXML
        private Text txtMessage;
    @FXML
    public void initialize() {

        Image image = new Image(util.Utility.getRouteImagen());
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
        void Home(ActionEvent event) {
            System.exit(0);
        }

        @FXML
        void Exit(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("initial_view.fxml"));
              loadPage("initial_view.fxml");
        }

        @FXML
        void gestionPedidosOnAction(ActionEvent event) {
loadPage("gestionPedidos.fxml");
        }

        @FXML
        void perfilOnAction(ActionEvent event) {
            loadPage("mantClientes.fxml");
        }

        @FXML
        void reportePedidosOnAction(ActionEvent event) {

        }

    }

