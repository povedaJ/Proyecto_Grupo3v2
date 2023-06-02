package controller;

import domain.CircularLinkedList;
import domain.ListException;
import domain.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {


    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField textFieldUsuario;
    @javafx.fxml.FXML
    private TextField textFieldContrasena;

    CircularLinkedList circularL = new CircularLinkedList();
    @FXML
    private Label txtError;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            circularL.add(new Security("Admin",MD5("1234"),"1"));
            circularL.add(new Security("User",MD5("12345"),"2"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void btnIngresar(ActionEvent event) throws NoSuchAlgorithmException, IOException, ListException {

         String usuario= this.textFieldUsuario.getText();
         String contrasena= this.textFieldContrasena.getText();


        Utility.file(circularL.toString(), "Security");
        if(circularL.contains(new Security(usuario, MD5(contrasena),"1"))){
            loadPage("menuAdmin.fxml");
        } else if(circularL.contains(new Security(usuario, MD5(contrasena),"2"))){
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
    public String MD5(String md5) throws IOException{

        try{
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i]&0xFF)|0x100).substring(1,3));
            }


            return sb.toString();

        }catch(java.security.NoSuchAlgorithmException e){
        }
        return null;
    }
}
