package controller;

import domain.List.ListException;
import domain.List.SinglyLinkedList;
import domain.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {


    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField textFieldUsuario;
    @javafx.fxml.FXML
    private TextField passwordField;
    @FXML
    private Label txtError;

    @FXML
    private ImageView logoImagen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);

        try {

            System.out.println("Listsss\n"+Utility.addreadSecuritiesFromFile("Security"));
            util.Utility.exportToPDF2(util.Utility.getSecurityList(),"Security.2o");
           // util.Utility.exportToPDf2(util.Utility.getSecurityList(),"Security.2023");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private void btnIngresar(ActionEvent event) throws NoSuchAlgorithmException, IOException, ListException {

         String usuario= this.textFieldUsuario.getText();
         String contrasena= this. passwordField.getText();

        //System.out.println("Listsss\n"+Utility.addreadSecuritiesFromFile("ProyectoGrupo_3/Security"));
       // Utility.file(util.Utility.securityList().toString(), "Security");
        if(util.Utility.securityList().contains(new Security(usuario, util.Utility.MD5(contrasena),"1"))){
            loadPage("menuAdmin.fxml");
        } else if(util.Utility.securityList().contains(new Security(usuario, util.Utility.MD5(contrasena),"2"))){
            loadPage("menuUsuario.fxml");
        }else {
            this.txtError.setVisible(true);
        }
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
    void returnOnAction(ActionEvent event) {
loadPage("initial_view.fxml");
    }
}
