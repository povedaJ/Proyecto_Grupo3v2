package controller;

import domain.List.ListException;
import domain.List.SinglyLinkedList;
import domain.Product;
import domain.Supplier;
import domain.Tree.AVL;
import domain.Tree.BTreeNode;
import domain.Tree.TreeException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;


public class newProductController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField currentStockTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;
    Alert alert;
    private static AVL supplierAVL = new AVL();//proveedores
    private static AVL productsAVL = new AVL();// Productos
    @FXML
    private TextField minimunStockTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private ChoiceBox<Supplier> supplierChoiceBox;


    public void initialize() {
util.Utility.llenarSupplier();
        //carga la lista de clientes
        this.supplierAVL = util.Utility.getSupplierAVL();
        this.productsAVL = util.Utility.getProductsAVL();

        this.alert = util.FXUtility.alert("Producto ", "Añadir producto...");
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
        try {
//            if (!productsAVL.isEmpty()) {
//                Utility.addreadProductsFromFile("Products");
//            }
//            if (!supplierAVL.isEmpty()) {
//                Utility.addReadSuppliersFromFile("Supplier");
//            }
            //Utility.addreadProductsFromFile("Products");
            System.out.println(supplierAVL);
            Utility.addReadSuppliersFromFile("Supplier");
            supplierAVL.add(new Supplier(6090,"Colono","8787878","colono@gmail.com","Curridabat"));

            SinglyLinkedList listSupplier= getList(supplierAVL.getRoot());
            for (int i = 0; i <listSupplier.size() ; i++) {
                //System.out.println(listSupplier.getNode(i).getData());
            }
          //  supplierAVL.getList().size();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    public SinglyLinkedList getList(BTreeNode node){

        SinglyLinkedList list=new SinglyLinkedList();
        if(node!=null){
            list.add(node.data);
            //+"("+node.path+"), ";
            list.add(getList(node.left));
            list.add(getList(node.right));
        }
        return list;
    }
    @FXML
    void retunrOnAction(ActionEvent event) {

    }

    @FXML
    void addOnAction(ActionEvent event) {
        try {
        if (isValid()) {
            int id = Integer.parseInt(this.idTextField.getText());
            int currentStock = Integer.parseInt(this.currentStockTextField.getText());
            int minimunStock = Integer.parseInt(this.minimunStockTextField.getText());
           double price = Double.parseDouble(this.priceTextField.getText());
Supplier selecSupplier= supplierChoiceBox.getValue();
            Product newProduct = new Product(id,
                    descriptionTextField.getText(), price,currentStock,
                    minimunStock,selecSupplier.getId());

        if (productsAVL.isEmpty()|| !supplierAVL.contains(newProduct.getId())){
            productsAVL.add(newProduct);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("El producto fue añadido correctamente");
            util.Utility.file(productsAVL,"Products");
            btnClean();
            System.out.println(productsAVL.toString());
        }else{
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("El usuario ya existe");
            btnClean(); //llama al boton clean
        }
        } else {//alerta de complete los campos
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Por favor complete toda las información del formulario");
        }
            alert.showAndWait();

        } catch (TreeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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

    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !descriptionTextField.getText().isEmpty() && !currentStockTextField.getText().isEmpty()
                && !minimunStockTextField.getText().isEmpty() && !priceTextField.getText().isEmpty() & supplierChoiceBox.getValue() != null;
    }

    void btnClean() {
        this.idTextField.clear();
        this.descriptionTextField.clear();
        this.currentStockTextField.clear();
        this.minimunStockTextField.clear();
        this.priceTextField.clear();
        this.supplierChoiceBox.setValue(null);
    }

    @FXML
    void numericOnly(KeyEvent event) {

    }

}
