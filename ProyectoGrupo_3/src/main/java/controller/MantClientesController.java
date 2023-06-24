package controller;

import domain.Customer;
import domain.List.CircularLinkedList;
import domain.List.ListException;
import domain.List.SinglyLinkedList;
import domain.Node;
import domain.Security;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class MantClientesController {

    @FXML
    private TableColumn<Customer, String> addressCTV;

    @FXML
    private BorderPane bp;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> emailCTV;

    @FXML
    private TableColumn<Customer, Integer> idCustomerTV;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TableColumn<Customer, String> nameCTV;

    @FXML
    private TableColumn<Customer, String> phoneCTV;
    private Alert alert;
    private static String emailFrom = "ferreteriaclavooxidado@gmail.com";
    private static String passwordFrom = "buhbwyoayfqsbyxb";

    private String emailTo;
    private String subject;
    private String content;
    private String passwordU;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;
    private TextInputDialog dialog;
    private SinglyLinkedList customerList;
    private CircularLinkedList securityList;

    @FXML
    public void initialize() throws ListException {
        mProperties = new Properties();
        //cargamos la lista desde la clase Utility
        this.customerList = util.Utility.getCustomerList();
        this.securityList=util.Utility.getSecurityList();
        try {
            Utility.addreadSecuritiesFromFile("Security");
            Utility.addReadCustomersFromFile("Customer");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        this.idCustomerTV.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameCTV.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.phoneCTV.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.emailCTV.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.addressCTV.setCellValueFactory(new PropertyValueFactory<>("address"));
        updateTableView(customerList);

    }

    public void updateTableView(SinglyLinkedList studentList) {
        customerTableView.getItems().clear();
        try {
            this.alert = util.FXUtility.alert("Student List", "Display Students");
            alert.setAlertType(Alert.AlertType.ERROR);
            if (studentList != null && !studentList.isEmpty()) {
                int n = studentList.size();
                for (int i = 1; i <= n; i++) {
                    this.customerTableView.getItems().add((Customer) studentList.getNode(i).data);

                }
            }
        } catch (ListException ex) {
            alert.setContentText("Student list is empty");
            alert.showAndWait();
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
    void addCustomerOnAction(ActionEvent event) {
        //loadPage("SignUpController");
       loadPage("newCustomer.fxml");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        dialog = util.FXUtility.dialog("Cliente", "Digite el Id del cliente a eliminar");
        dialog.showAndWait();
        int value = Integer.parseInt(dialog.getResult().toString());
        this.alert = util.FXUtility.alert("Cliente", "Eliminado cliente");
        alert.setAlertType(Alert.AlertType.INFORMATION);
        try {
            System.out.println("lis diLO"+customerList.toString());
            if ( customerList.contains(value)){
                int index=customerList.indexOf(value);
                Node node =customerList.getNode(index);
               Customer cus= (Customer) node.getData();
                System.out.println( "ide"+ cus.getId());
                customerList.remove(cus.getId());
                alert.setContentText("Cliente "+value+ " eliminado");
               // alert.setContentText(String.valueOf(customerList.indexOf(value)));
                alert.showAndWait();
                util.Utility.setCustomerList(customerList);
                System.out.println("despues"+customerList);
                updateTableView(customerList);
                util.Utility.file(customerList,"Customer");
            }else{
                alert.setContentText("El id de ese cliente no existe");
                alert.showAndWait();
            }

        }  catch (ListException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void editOnAction(ActionEvent event) {
        dialog = util.FXUtility.dialog("Cliente", "Digite el Id del cliente que desea modificar");
        dialog.showAndWait();
        int value = Integer.parseInt(dialog.getResult());
        this.alert = util.FXUtility.alert("Cliente", " ");
        alert.setAlertType(Alert.AlertType.INFORMATION);
        try {
            System.out.println("lis diLO"+customerList.toString());
            if ( customerList.contains(value)){
                int index=customerList.indexOf(value);
                Node node =customerList.getNode(index);
                Customer cus= (Customer) node.getData();
                System.out.println( "ide"+ cus.getId());


                dialog = util.FXUtility.dialog("Cliente", "El id es "+cus.getId()+" ingrese el nuevo id");
                dialog.showAndWait();
                int idNew = Integer.parseInt(dialog.getResult());
                dialog = util.FXUtility.dialog("Cliente", "El nombre es "+cus.getName()+" ingrese el nuevo nombre");
                dialog.showAndWait();
                String nameNew = dialog.getResult();
                dialog = util.FXUtility.dialog("Cliente", "El núm Teléfono es "+cus.getPhoneNumber()+" ingrese el nuevo ");
                dialog.showAndWait();
                String phoneNew = dialog.getResult();
                dialog = util.FXUtility.dialog("Cliente", "El email es "+cus.getEmail()+" ingrese el nuevo ");
                dialog.showAndWait();
                String emailNew = dialog.getResult();
                dialog = util.FXUtility.dialog("Cliente", "La dirección es "+cus.getAddress()+" ingrese la  nueva ");
                dialog.showAndWait();
                String addressNew = dialog.getResult();

                customerList.remove(cus.getId());
                Customer cnew= new Customer(idNew,nameNew,phoneNew,emailNew,addressNew);
                customerList.add(cnew);
                String pass = generateUniquePassword();
                createEmail(cnew.getEmail(),cnew.getName(),cnew.getId(),pass);
                sendEmail();
                Security p1= new Security(String.valueOf(cnew.getId()),util.Utility.MD5(pass),"2");
                securityList.add( p1);
                util.Utility.file(securityList,"Security");
                util.Utility.file(customerList,"Customer");
                alert.setContentText("Cliente "+value+ " ha cambiado a "+cnew.toString() );
                // alert.setContentText(String.valueOf(customerList.indexOf(value)));
                alert.showAndWait();
                util.Utility.setCustomerList(customerList);
                System.out.println("despues"+customerList);
                updateTableView(customerList);
                util.Utility.file(customerList,"Customer");
            }else{
                alert.setContentText("El id de ese cliente no existe");
                alert.showAndWait();
            }

        }  catch (ListException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    private void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("La información de su cuenta ya fue enviada, por favor revise su correo.");
            loadPage("mantClientes.fxml");

        } catch (NoSuchProviderException ex) {
            throw new RuntimeException(ex);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
