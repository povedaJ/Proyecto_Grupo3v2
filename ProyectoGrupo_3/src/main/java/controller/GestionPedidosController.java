package controller;


import domain.Product;
import domain.Tree.AVL;
import domain.Tree.BTreeNode;
import domain.Tree.TreeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;
import util.Utility;

import java.io.IOException;

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
    private Object bitacora; //para guardar en la bitacora
    ObservableList<String> productosNombre;

    @FXML
    public void initialize() throws TreeException {
        notificacionLabel.setText("");
        productos= new AVL();
        pedidos= new AVL();
        llenarProductos();
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

    private void llenarProductos() {
        Utility.llenarProductosLista();

        Product[] list =Utility.getProductosList();
        int n = list.length;
        for (int i = 0; i < n; i++) {
            productos.add(list[i]);
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
        String cant = cantidadTextField.getText();
        if (productosChoiceBox.getValue()!=null){
            if (!cant.isEmpty()){
                String producto = (String) productosChoiceBox.getValue();
                if (Utility.isNumberExp(cant)){
                    int cantidad = Integer.parseInt(cant);
                    hacePedido(productos.getRoot(), producto, cantidad);
                    cambiarListaProductos(productos.getRoot(), 0); //se debe actualizar toda la lista de productos
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
                    Product pedido = p;
                    pedido.setCurrentStock(cantidad);
                    pedidos.add(pedido);

                    p.setCurrentStock(cant-cantidad); //actualiza la cantidad
                    productos.remove(node.data);
                    productos.add(p);
                    notificacionLabel.setText("El pedido se ha realizado");
                    return;
                }else{
                    notificacionLabel.setText("Del producto elegido solo hay "+p.getCurrentStock());
                }
            }
            hacePedido(node.left, producto, cantidad);
            hacePedido(node.right, producto, cantidad);
        }
    }

    public void cambiarListaProductos(BTreeNode node, int k) throws TreeException {
        Product[] list = new Product[productos.size()];
        if (node!=null){
            list[k]= (Product) node.data;
            cambiarListaProductos(node.left, k+1);
            cambiarListaProductos(node.right, k+1);
        }
        Utility.setProductsList(list);
    }

    @FXML
    void btnPedidosAutomaticos(ActionEvent event) {

    }

    public void btnRegresar(ActionEvent actionEvent) {
        //loadPage("menuAdmin.fxml");
    }
}
