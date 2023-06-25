package controller;


import domain.Product;
import domain.Sale;
import domain.Tree.AVL;
import domain.Tree.BTreeNode;
import domain.Tree.TreeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.FXUtility;
import util.Utility;

import java.io.IOException;
import java.time.LocalDateTime;

public class GestionPedidosController {
    @FXML
    private BorderPane bp;
    @FXML
    private TextField cantidadTextField;
    @FXML
    private TextField cantidadAutoTextField;

    @FXML
    private Label notificacionLabel;

    @FXML
    private ChoiceBox<String> productosChoiceBox;

    private AVL productos; //salen del inventario
    private AVL pedidos;
    ObservableList<String> productosNombre;
    private int cantPedido;
    private Sale sale;
    private AVL orders;
    private Alert alert;

    @FXML
    public void initialize() throws TreeException {
        notificacionLabel.setText("");
        productos= util.Utility.getProductsAVL();
        pedidos= new AVL();
        orders= util.Utility.getOrdersManagement();
        if (orders!=null){
            orders= new AVL();
        }
        productosNombre= FXCollections.observableArrayList();
        extraerNombres(productos.getRoot());
        productosChoiceBox.setItems(productosNombre);
    }

    private void extraerNombres(BTreeNode node) {
        if (node!=null){
            Product p = (Product) node.data;
            productosNombre.add(p.getDescription());
            extraerNombres(node.left);
            extraerNombres(node.right);
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
    void btnPedidos(ActionEvent event) throws TreeException {
        this.alert = FXUtility.alert("", "");
        notificacionLabel.setText("");
        String cant = cantidadTextField.getText();
        if (productosChoiceBox.getValue()!=null){
            if (!cant.isEmpty()){
                String producto = (String) productosChoiceBox.getValue();
                if (Utility.isNumberExp(cant)){
                    int cantidad = Integer.parseInt(cant);
                    hacePedido(productos.getRoot(), producto, cantidad);
                    util.Utility.setProductsAVL(productos); //actualiza la lista de productos
                    util.Utility.setOrdersManagement(orders);
                }else{
                    notificacionLabel.setText("Solo puede ingresar números");
                }
            }else{
                notificacionLabel.setText("Debe ingresar la cantidad que desea");
            }
        }else{
            notificacionLabel.setText("Debe seleccionar el producto");
        }
        cantidadTextField.clear();
    }

    private void hacePedido(BTreeNode node, String producto, int cantidad) throws TreeException {
        if (node!=null){
            Product p = (Product) node.data;
            if (Utility.compare(p.getDescription(), producto)==0){
                int cant = p.getCurrentStock();
                if (cantidad <= cant){
                    Product  pedido= new Product(p.getDescription(), p.getPrice(), p.getCurrentStock(),p.getMinimumStock(),p.getSupplierId());
                    if (!pedidos.isEmpty() && pedidos.contains(pedido)){
                        //Alert, para avisarle al cliente que ya pidio este producto
                        alert.setContentText("Usted ya ha pedido este producto\n Desea continuar?");
                        alert.showAndWait();
                        alert.getResult();
                        if (alert.getResult()!=null){ //sí desea continuar
                            pedidoCantActual(pedidos.getRoot(), producto);
                            pedido.setCurrentStock(cantPedido+cantidad);
                            pedidos.remove(pedido);
                            pedidos.add(pedido);
                            notificacionLabel.setText("La cantidad actual del pedido es "+pedido.getCurrentStock());
                        }
                    }else{
                        pedido.setCurrentStock(cantidad);
                        pedidos.add(pedido);
                        notificacionLabel.setText("El pedido se ha realizado");
                    }
                    guardarPedido(pedido, cantidad);
                    p.setCurrentStock(cant-cantidad); //actualiza la cantidad
                    productos.remove(node.data);
                    productos.add(p);
                }else{
                    notificacionLabel.setText("Del producto elegido solo hay "+p.getCurrentStock());
                }
            }
            hacePedido(node.left, producto, cantidad);
            hacePedido(node.right, producto, cantidad);
        }
    }

    @FXML
    void btnPedidosAutomaticos(ActionEvent event) throws TreeException {
        String cant = cantidadAutoTextField.getText();
        if (!cant.isEmpty()){
            if (util.Utility.isNumberExp(cant)){
                int cantidad = Integer.parseInt(cant);
                for (int i = 0; i < cantidad; i++) {
                    int num= util.Utility.random(productosChoiceBox.getItems().size())-1; //probar
                    hacePedidoAuto(productos.getRoot(), cantidad, num);
                }
                util.Utility.setProductsAVL(productos); //actualiza la lista de productos
                util.Utility.setOrdersManagement(orders);
                notificacionLabel.setText("Se realizo la petición automática");
                guardarPedidoAuto(pedidos.getRoot());
            }else{
                notificacionLabel.setText("Solo puede ingresar números");
            }
        }else{
            notificacionLabel.setText("Debe ingresar la cantidad de productos que desea");
        }
    }

    private void hacePedidoAuto(BTreeNode node, int cantidad, int num) throws TreeException {
        int newCant= 1; //cantidad de unidades por producto
        if (node!=null)  {  //probar
            String producto = productosChoiceBox.getItems().get(num);
            Product p = (Product) node.data;
            if (util.Utility.compare(p.getDescription(), producto)==0){
                int cantActual = p.getCurrentStock();
                if ((cantActual-newCant) >=0){ //la cantidad del producto que queda no puede ser negativo
                    Product  pedido= new Product(p.getDescription(),p.getPrice(),p.getCurrentStock(),p.getMinimumStock(),p.getSupplierId());
                    if (!pedidos.isEmpty() && pedidos.contains(pedido)){
                        pedidoCantActual(pedidos.getRoot(), producto);
                        newCant= cantPedido+newCant;
                        pedidos.remove(p);
                    }
                    pedido.setCurrentStock(newCant);
                    pedidos.add(pedido);

                    p.setCurrentStock(cantActual-newCant);
                    productos.remove(node.data); //probar
                    productos.add(p);
                }else {
                    cantidad++;
                }
            }
            hacePedidoAuto(node.left, cantidad, num);
            hacePedidoAuto(node.right, cantidad, num);
        }
    }

    private void pedidoCantActual(BTreeNode node, String productos) {
        if (node!=null){
            Product p = (Product) node.data;
            if (util.Utility.compare(p.getDescription(), productos)==0){
                cantPedido= p.getCurrentStock();
            }
            pedidoCantActual(node.left, productos);
            pedidoCantActual(node.right, productos);
        }
    }

    private void guardarPedido(Product product, int cantidad){
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        sale= new Sale(fechaHoraActual, cantidad, product.getDescription());
        orders.add(sale);
    }

    private void guardarPedidoAuto(BTreeNode node){
        if (node!=null){
            Product p = (Product) node.data;
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            sale= new Sale(fechaHoraActual,p.getCurrentStock(), p.getDescription());
            orders.add(sale);
            guardarPedidoAuto(node.left);
            guardarPedidoAuto(node.right);
        }
    }

    public void btnRegresar(ActionEvent actionEvent) {
        //loadPage("menuAdmin.fxml");
    }
}
