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

import java.io.IOException;

public class MenuAdminController {

    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private ImageView logoImagen;

    @FXML
    private Text txtMessage;

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
    void Exit(ActionEvent event) {

    }

    @FXML
    void archiSerializablesOnAction(ActionEvent event) {

    }

    @FXML
    void clientesOnAction(ActionEvent event) {
        loadPage("mantClientes.fxml");
    }

    @FXML
    void controlCostosOnAction(ActionEvent event) {
        loadPage("controlCostos.fxml");
    }

    @FXML
    void controlInventarioOnAction(ActionEvent event) {
        loadPage("controlInventario.fxml");
    }

    @FXML
    void infoEmpresarialOnAction(ActionEvent event) {
        loadPage("generalesNombreLogo.fxml");
    }

    @FXML
    void infoInventarioOnAction(ActionEvent event) {

    }

    @FXML
    void infoPedidosOnAction(ActionEvent event) {

    }

    @FXML
    void infoProductosOnAction(ActionEvent event) {

    }

    @FXML
    void previsionDemandaOnAction(ActionEvent event) {

    }

    @FXML
    void productosOnAction(ActionEvent event) {
        loadPage("newProduct.fxml");
    }

    @FXML
    void proveedoresOnAction(ActionEvent event) {
        loadPage("mantProveedores.fxml");
    }

}
