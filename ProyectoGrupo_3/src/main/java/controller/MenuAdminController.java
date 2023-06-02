package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;

import java.io.IOException;

public class MenuAdminController {

    @FXML
    private BorderPane bp;

    @FXML
    private MenuBar mb;

    @FXML
    void menuClientes(ActionEvent event) {
    loadPage("mantClientes.fxml");
    }

    @FXML
    void menuCostos(ActionEvent event) {

    }

    @FXML
    void menuDemanda(ActionEvent event) {

    }

    @FXML
    void menuGenerales(ActionEvent event) {

    }

    @FXML
    void menuInventario(ActionEvent event) {

    }

    @FXML
    void menuProductos(ActionEvent event) {
        loadPage("mantProductos.fxml");
    }

    @FXML
    void menuProveedores(ActionEvent event) {
        loadPage("mantProveedores.fxml");
    }

    @FXML
    void menuReportes(ActionEvent event) {

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
    public void menuControlInventario(ActionEvent actionEvent) {
        loadPage("controlInventario.fxml");
    }
    @FXML
    public void menuPrevisiónDemanda(ActionEvent actionEvent) {
        loadPage("previsionDemanda.fxml");
    }
    @FXML
    public void menuGenerarReportes(ActionEvent actionEvent) {
        loadPage("reportes.fxml");
    }
    @FXML
    public void menuConfigNombreLogo(ActionEvent actionEvent) {
        loadPage("generalesNombreLogo.fxml");
    }
    @FXML
    public void menuArchivosSerializables(ActionEvent actionEvent) {
        loadPage("generalesArchivosSerializables.fxml");
    }

    public void menuControlCostos(ActionEvent actionEvent) {
        loadPage("controlCostos.fxml");
    }
}
