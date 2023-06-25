package controller;



import domain.Order;
import domain.OrderDetail;
import domain.Product;
import domain.Tree.AVL;
import domain.Tree.BTree;
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
import java.time.LocalDateTime;

public class ControlInventarioController {

    @FXML
    private BorderPane bp;

    @FXML
    private TextField cantidadTextField;

    @FXML
    private Label notificacionLabel;

    @FXML
    private ChoiceBox productosChoiceBox;
    private BTree registro;
    private int tamañoRegistro;
    ObservableList<String> nombreProductos;
    OrderDetail orderDetail;
    private BTree orders;
    private BTree orderBTree;
    private AVL devuelveProduct;

    @FXML
    public void initialize() throws TreeException {
        notificacionLabel.setText("");
        registro= new BTree();
        AVL products = util.Utility.getProductsAVL();
        llenarBTree(products.getRoot()); //con util.Utility.getProductsAVL
        nombreProductos= FXCollections.observableArrayList();
        extraerNombres(registro.getRoot());
        productosChoiceBox.setItems(nombreProductos);
        tamañoRegistro = registro.size();

        orders=util.Utility.getInventoryBT();
        if (orders.isEmpty()){
            orders= new BTree();
        }
        orderBTree = util.Utility.getOrdersBTree();
        if (orderBTree.isEmpty()){
            orderBTree = new BTree();
        }
    }

    public void llenarBTree(BTreeNode node){
        if (node!=null){
            Product p = (Product) node.data;
            if (p!=null){
                registro.add(p);
            }
            llenarBTree(node.left);
            llenarBTree(node.right);
        }
    }

    public void extraerNombres(BTreeNode node){
        if (node!=null){
            Product p = (Product) node.data;
            nombreProductos.add(p.getDescription());
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
    void btnActualizaciónAutomatica(ActionEvent event) throws TreeException {
        int n = tamañoRegistro;
        if(n < registro.size()){
            eliminaNulos(registro.getRoot());
            if(n < registro.size()){
                eliminaNulos(registro.getRoot());
            }
        }
        cambiaAutomatico(registro.getRoot());

        if(n != registro.size()){
            eliminaNulos(registro.getRoot());
        }
        if(n != registro.size()){
            eliminaNulos(registro.getRoot());
        }
        if(n != registro.size()){
            eliminaNulos(registro.getRoot());
        }
        devuelveProduct= new AVL();
        cambiarListaProductos(registro.getRoot()); //llena devuelveProduct
        util.Utility.setProductsAVL(devuelveProduct); //cambia actualiza productos

        notificacionLabel.setText("Las cantidades se actializaron");
        guardaOrderAuto(registro.getRoot());
    }

    public void cambiaAutomatico(BTreeNode node) throws TreeException {
        int cant= Utility.random(150); //Cantidad maxima de productos
        if (node!=null){
            Product p = (Product) node.data;
            if (p!=null){
                p.setCurrentStock(cant); //cambia la cantidad
                registro.remove(node.data);
                registro.add(p);
            }else{
                registro.remove(node.data);
            }
            cambiaAutomatico(node.left);
            cambiaAutomatico(node.right);
        }
    }
    public void eliminaNulos(BTreeNode node) throws TreeException {
        if (node!=null){
            if(node.data==null){
                registro.remove(node.data);
            }
            eliminaNulos(node.left);
            eliminaNulos(node.right);
        }else{
        registro.remove(null);
        }
    }

    @FXML
    void btnActualizar(ActionEvent event) throws TreeException {
        String cant = cantidadTextField.getText();
        if (productosChoiceBox.getValue()!=null){
            if (!cant.isEmpty()){
                String producto = (String) productosChoiceBox.getValue();
                if (Utility.isNumberExp(cant)){
                    int cantidad = Integer.parseInt(cant);
                    cambiarCantidad(registro.getRoot(), producto, cantidad);

                    devuelveProduct= new AVL();
                    cambiarListaProductos(registro.getRoot());
                    util.Utility.setProductsAVL(devuelveProduct); //se debe actualizar toda la lista de productos

                    notificacionLabel.setText("La cantidad se actualizo");
                }else{
                    notificacionLabel.setText("Solo puede ingresar numeros");
                }
            }else{
                notificacionLabel.setText("Debe ingresar la cantidad a actualizar");
            }
        }else{
            notificacionLabel.setText("Debe seleccionar el producto");
        }
        cantidadTextField.clear();
    }



    public void cambiarCantidad(BTreeNode node, String nombre, int cant) throws TreeException {
        if (node!=null){
            Product p = (Product) node.data;
            if (p!=null){
                if (Utility.compare(p.getDescription(), nombre)==0){
                    p.setCurrentStock(cant); //cambia la cantidad
                    registro.remove(node.data);
                    registro.add(p);
                    guardaOrder(p);
                }
            }
            cambiarCantidad(node.left, nombre, cant);
            cambiarCantidad(node.right, nombre, cant);
        }
    }

    public void cambiarListaProductos(BTreeNode node) {
        if (node!=null){
            Product p = (Product) node.data;
            if (p!=null){
                devuelveProduct.add(p);
            }
            cambiarListaProductos(node.left);
            cambiarListaProductos(node.right);
        }
    }

    public void guardaOrder(Product product) throws TreeException {
        int cant = product.getCurrentStock();
        double totalCost = cant * product.getPrice();
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Order order = new Order(fechaHoraActual, "En espera", product.getSupplierId(), totalCost, "");
        orderDetail= new OrderDetail(order.getId(), product.getId(), cant, product.getPrice());

        if (orders.isEmpty()){
            orders.add(orderDetail);
            orderBTree.add(order);
        }else {
            if (!orders.contains(orderDetail)){
                orders.add(orderDetail);
                orderBTree.add(order);
            }else{
                contieneProduct(orders.getRoot(), product, cant);
                contieneProduct2(orderBTree.getRoot(), product, totalCost);
            }
        }
    }

    private void contieneProduct2(BTreeNode node, Product product, double cant) throws TreeException {
        if (node!=null){
            Order order = (Order) node.data;
            if (order!=null){
                if (util.Utility.compare(order, product)==0){
                    order.setTotalCost(cant);
                }
            }
            contieneProduct2(node.left, product, cant);
            contieneProduct2(node.right, product, cant);
        }
    }

    private void contieneProduct(BTreeNode node, Product product, int cant) throws TreeException {
        if (node!=null){
            OrderDetail orderD = (OrderDetail) node.data;
            if (orderD!=null){
                if (util.Utility.compare(orderD, product)==0){
                    orderD.setQuantity(cant);
                }
            }
            contieneProduct(node.left, product, cant);
            contieneProduct(node.right, product, cant);
        }
    }

    public void guardaOrderAuto(BTreeNode node){
        if (node!=null){
            Product p = (Product) node.data;
            double totalCost = p.getCurrentStock() * p.getPrice();
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            Order order = new Order(fechaHoraActual, "En espera", p.getSupplierId(), totalCost, "");
            orderDetail= new OrderDetail(order.getId(), p.getId(), p.getCurrentStock(), p.getPrice());
            orders.add(orderDetail);
            orderBTree.add(order);
            guardaOrderAuto(node.left);
            guardaOrderAuto(node.right);
        }
    }

    @FXML
    void btnRegresar(ActionEvent event) {
        loadPage("menuAdmin.fxml");
    }


}
