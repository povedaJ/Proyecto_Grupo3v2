package controller;

import domain.Customer;
import domain.List.CircularLinkedList;
import domain.List.ListException;
import domain.List.SinglyLinkedList;
import domain.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class SignUpController {
    @FXML
    private TextField addressTextField;

    @FXML
    private BorderPane bp;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;
    private SinglyLinkedList customerList;
    private CircularLinkedList securityList;
    Alert alert;
    private static String emailFrom = "ferreteriaclavooxidado@gmail.com";
   // private static String emailFrom = "vivipoveda15@gmail.com";
    //private static String passwordFrom = "exgehhmbahbxeeyi";
    private static String passwordFrom = "buhbwyoayfqsbyxb";

    private String emailTo;
    private String subject;
    private String content;
    private String passwordU;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    public void initialize() {
        mProperties = new Properties();
        //carga la lista de clientes
         this.customerList = util.Utility.getCustomerList();
         this.securityList=util.Utility.getSecurityList();

        this.alert = util.FXUtility.alert("Sign up", "Add new customer...");
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        try {
            Utility.addreadSecuritiesFromFile("Security");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
    void numericOnly(KeyEvent event) {
        String input = event.getCharacter();
        if (!input.matches("\\d")) {
            event.consume();
        }
    }

    @FXML
    void retunrOnAction(ActionEvent event) {
        loadPage("initial_view.fxml");
    }

    @FXML
    void signUpOnAction(ActionEvent event) {
        try {
            if (isValid()) {
                int id = Integer.parseInt(this.idTextField.getText());
                Customer newCustomer = new Customer(
                        id,
                        this.nameTextField.textProperty().getValue(),
                        this.phoneNumberTextField.textProperty().getValue(),
                        this.emailTextField.textProperty().getValue(),
                        this.addressTextField.textProperty().getValue());
                if (customerList.isEmpty()|| !customerList.contains(newCustomer)){
                    customerList.add(newCustomer);
                    String pass = generateUniquePassword();
                    createEmail(newCustomer.getEmail(),newCustomer.getName(),newCustomer.getId(),pass);
                    sendEmail();
                    //settear lista de utiliti, la global
                    Security p1= new Security(this.idTextField.getText(),util.Utility.MD5(pass),"2");
                    securityList.add( p1);
                    util.Utility.file(securityList,"Security");
                    util.Utility.file(customerList,"Costumer");
                  //  util.Utility.securityList().add(new Security("Admin",util.Utility.MD5("1234"),"1"));
                    btnClean(); //llama al boton clean
                    System.out.println(securityList.toString());
                    System.out.println(customerList.toString());
                }else{
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("El usuario ya existe");
                    btnClean(); //llama al boton clean
                }
            } else {//alerta de complete los campos
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Por favor, Complete toda las información del formulario");
            }
            alert.showAndWait();

        } catch (ListException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//end

    void btnClean() {
        this.idTextField.clear();
        this.nameTextField.clear();
        this.phoneNumberTextField.clear();
        this.emailTextField.clear();
        this.addressTextField.clear();
    }

    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !nameTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty()
                && !emailTextField.getText().isEmpty() && !addressTextField.getText().isEmpty();
    }
    private void createEmail(String email,String name,int user,String password) {
        emailTo = email;
        subject = "Acceso a "+ util.Utility.getNameApp();
        passwordU= password;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String horaRegistro = dateFormat.format(date);

       // content = "Este mensaje es enviado por ferreterias 3 hermanos\n\n"+"User: "+user+"\n Password: "+password;
        content = "<html><body>"
                + "<h2 style='color: #333333;'>Mensaje enviado por " +util.Utility.getNameApp()+"</h2>"
                + "<p>Estimado/a " + name + ",</p>"
                + "<p>Le damos la bienvenida a nuestra nueva aplicación. A continuación, encontrará los detalles de su cuenta:</p>"
                + "<ul>"
                + "<li><strong>Usuario:</strong> " + user + "</li>"
                + "<li><strong>Contraseña:</strong> " + passwordU + "</li>"
                + "</ul>"
                + "<p>Fecha y hora de registro: " + horaRegistro + "</p>"
                + "<p>Por favor, guarde estos detalles de inicio de sesión de manera segura. Si tiene alguna pregunta o necesita ayuda, no dude en contactarnos.</p>"
                + "<p>Gracias por elegir " +util.Utility.getNameApp()+".</p>"
                + "<p>Atentamente,</p>"
                + "<p>Equipo de soporte de " +util.Utility.getNameApp()+"</p>"
                + "</body></html>";


        // Simple mail transfer protocol
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        mSession = Session.getDefaultInstance(mProperties);


        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");


        } catch (AddressException ex) {
            throw new RuntimeException(ex);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static String generateUniquePassword() {
        int length=8;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        String password = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return password.substring(0, length);
    }
    private void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("La información de su cuenta ya fue enviada, por favor revise su correo.");
            loadPage("initial_view.fxml");

        } catch (NoSuchProviderException ex) {
            throw new RuntimeException(ex);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
