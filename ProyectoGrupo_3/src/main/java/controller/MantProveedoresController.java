package controller;

import domain.List.ListException;
import domain.Tree.AVL;
import domain.Tree.BTreeNode;
import domain.Tree.Tree;
import domain.Tree.TreeException;
import domain.queue.Queue;
import domain.queue.QueueException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import domain.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class MantProveedoresController {

    @FXML
    private BorderPane bp;

    @FXML
    private TableView<Supplier> tableView;

    @FXML
    private TableColumn<Supplier, Integer> idColumn;

    @FXML
    private TableColumn<Supplier, String> nombreColumn;

    @FXML
    private TableColumn<Supplier, String> direccionColumn;

    @FXML
    private TableColumn<Supplier, String> telefonoColumn;

    @FXML
    private ImageView logoImagen;

    TextInputDialog busc = new TextInputDialog("");
    TextInputDialog nameTID = new TextInputDialog("");
    TextInputDialog idTID = new TextInputDialog("");
    TextInputDialog phoneNumberTID = new TextInputDialog("");
    TextInputDialog emailTID = new TextInputDialog("");
    TextInputDialog addressTID = new TextInputDialog("");

    private TextInputDialog dialog;
    @FXML
    private Label txtMessages;

    @FXML
    private Label txtErrorMessages;

    @FXML
    private TableColumn<Supplier, String> emailColumn;

    private AVL supplierList = util.Utility.getSupplierAVL();

    private AVL productList = util.Utility.getProductsAVL();

    private Alert alert;

    @FXML
    public void initialize() {
        try {
            Utility.addReadSuppliersFromFile("Supplier");
        } catch (FileNotFoundException e) {
            // Manejar adecuadamente la excepción o mostrar un mensaje de error
            e.printStackTrace();
        }

        Image image = new Image(util.Utility.getRouteImagen());
        logoImagen.setImage(image);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

       updateTable(supplierList);
    }


    private void updateTable(AVL supplierList) {
        try {
            this.alert = util.FXUtility.alert("Student List", "Display Students");
            alert.setAlertType(Alert.AlertType.ERROR);

            if (supplierList != null && !supplierList.isEmpty()) {
                int n = supplierList.size();
                for (int i = 1; i < n; i++) {
                    this.tableView.getItems().add((Supplier) supplierList.get(i));
                }
            } else {
                alert.setContentText("Supplier list is empty");
                alert.showAndWait();
            }
        } catch (TreeException e) {
            alert.setContentText("Error occurred while updating the table");
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
    public void returnOnAction() {
        loadPage("menuAdmin.fxml");
    }

    private boolean compare;
    private String d;

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    @FXML
    public void btnActualizar(ActionEvent event) throws TreeException {
//        busc.setTitle("Modificar curso");
//        busc.setHeaderText("Ingrese el ID del curso a modificar:");
//        busc.setContentText("");
//
//        Optional<String> result = busc.showAndWait();
//        if (result.isPresent()) {
//
//            try {
//                this.d=result.get();
//
//            } catch (NumberFormatException ex) {
//
//            }
//        }
//        compare = (supplierList.contains(new Supplier(id, "", "", "", "")));
//        if (compare == true) {
//            supplierList.remove(new Supplier(id, name, phoneNumber, email, address));
//            nameTID.setTitle("Agregar datos");
//            nameTID.setHeaderText("Ingrese el nombre del curso :");
//            nameTID.setContentText("");
//
//            Optional<String> result9 = nameTID.showAndWait();
//            if (result.isPresent()) {
//
//                try {
//                    this.name = result9.get();
//
//                } catch (NumberFormatException ex) {
//
//                }
//            }
//
//            phoneNumberTID.setTitle("Agregar datos");
//            phoneNumberTID.setHeaderText("Ingrese los creditos del curso :");
//            phoneNumberTID.setContentText("");
//
//            Optional<String> result29 = phoneNumberTID.showAndWait();
//            if (result.isPresent()) {
//
//                try {
//                    this.phoneNumber = result29.get();
//
//                } catch (NumberFormatException ex) {
//
//                }
//            }
//
//
//            emailTID.setTitle("Agregar datos");
//            emailTID.setHeaderText("Ingrese los creditos del curso :");
//            emailTID.setContentText("");
//
//            Optional<String> result299 = emailTID.showAndWait();
//            if (result.isPresent()) {
//
//                try {
//                    this.email = result299.get();
//
//                } catch (NumberFormatException ex) {
//
//                }
//            }
//
//            addressTID.setTitle("Agregar datos");
//            addressTID.setHeaderText("Ingrese los creditos del curso :");
//            addressTID.setContentText("");
//
//            Optional<String> result2999 = addressTID.showAndWait();
//            if (result.isPresent()) {
//
//                try {
//                    this.address = result2999.get();
//
//                } catch (NumberFormatException ex) {
//
//                }
//            }
//
//
//        }
        Supplier proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            supplierList.remove(proveedorSeleccionado);
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Actualizar proveedor");
            dialog.setHeaderText(null);
            dialog.setContentText("Ingresa los nuevos datos del proveedor:");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            TextField txtid= new TextField(String.valueOf(proveedorSeleccionado.getId()));
            txtid.setPromptText("id");
            TextField txtNombre = new TextField(proveedorSeleccionado.getName());
            txtNombre.setPromptText("name");
            TextField txtTelefono = new TextField(proveedorSeleccionado.getPhoneNumber());
            txtTelefono.setPromptText("phoneNumber");
            TextField txtCorreo = new TextField(proveedorSeleccionado.getEmail());
            txtCorreo.setPromptText("email");
            TextField txtDireccion = new TextField(proveedorSeleccionado.getAddress());
            txtDireccion.setPromptText("address");

            gridPane.addRow(0, new Label("id:"), txtid);
            gridPane.addRow(1, new Label("name:"), txtNombre);
            gridPane.addRow(2, new Label("phoneNumber:"), txtTelefono);
            gridPane.addRow(3, new Label("email:"), txtCorreo);
            gridPane.addRow(4, new Label("address:"), txtDireccion);

            dialog.getDialogPane().setContent(gridPane);

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {

                supplierList.remove(result.isPresent());
                int id = Integer.parseInt(txtid.getText());
                String nombre = txtNombre.getText();
                String telefono = txtTelefono.getText();
                String correo = txtCorreo.getText();
                String direccion = txtDireccion.getText();

                proveedorSeleccionado.setId(id);
                proveedorSeleccionado.setName(nombre);
                proveedorSeleccionado.setPhoneNumber(telefono);
                proveedorSeleccionado.setEmail(correo);
                proveedorSeleccionado.setAddress(direccion);
                tableView.getItems().clear();
                supplierList.add(proveedorSeleccionado);
                updateTable(supplierList);



                try {
                    Utility.file(supplierList,"Supplier");
                    util.Utility.setSupplierAVL(supplierList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mostrarInformacion("Proveedor actualizado", "El proveedor ha sido actualizado correctamente.");

            }
        } else {
            mostrarAlerta("Proveedor no seleccionado", "Por favor, selecciona un proveedor de la tabla.");
        }

    }

    @FXML
    public void btnAgregar() {
        loadPage("AddSupplier.fxml");
    }

    private String eliminar;
    @FXML
    public void btnEliminar() throws TreeException {
//        dialog = util.FXUtility.dialog("Proveedor", "Digite el Id del proveedor a eliminar");
//        dialog.showAndWait();
//        int value = Integer.parseInt(dialog.getResult().toString());
//        this.alert = util.FXUtility.alert("Proveedor", "Proveedor Eliminado");
//        alert.setAlertType(Alert.AlertType.INFORMATION);
//        try {
//            if (supplierList.contains(value)){
//                int index=supplierList.indexOf(value);
//                BTreeNode bTreeNode = (BTreeNode) supplierList.get(index);
//                Supplier supplier = (Supplier) bTreeNode.data;
//                System.out.println( "ide"+ supplier.getId());
//                supplierList.remove(supplier.getId());
//                alert.setContentText("Cliente "+value+ " eliminado");
//                // alert.setContentText(String.valueOf(customerList.indexOf(value)));
//                alert.showAndWait();
//                util.Utility.setSupplierAVL(supplierList);
//                System.out.println("despues"+supplierList);
//                updateTable(supplierList);
//                util.Utility.file(supplierList,"Customer");
//            }else{
//                alert.setContentText("El id de ese cliente no existe");
//                alert.showAndWait();
//            }
//
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // Lógica para el botón Eliminar
        // Obtener el proveedor seleccionado en la tabla
        Supplier proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            // Confirmar la eliminación del proveedor
            boolean confirmacion = mostrarConfirmacion("Eliminar proveedor",
                    "¿Estás seguro de que deseas eliminar este proveedor?");

            if (confirmacion) {
                // Eliminar el proveedor de la lista observable del TableView
                tableView.getItems().remove(proveedorSeleccionado);
                supplierList.remove(proveedorSeleccionado);
                mostrarInformacion("Proveedor eliminado",
                        "El proveedor ha sido eliminado correctamente.");
            }
        } else {
            mostrarAlerta("Proveedor no seleccionado",
                    "Por favor, selecciona un proveedor de la tabla.");
        }
    }

    public boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initModality(Modality.APPLICATION_MODAL);

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        return alert.showAndWait().orElse(buttonTypeNo) == buttonTypeYes;
    }

    public void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    }

