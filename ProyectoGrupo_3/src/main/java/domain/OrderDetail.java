package domain;

public class OrderDetail {
    private int id;
    private static int autoId;// id autogenerado
    private int orderId;
    private int productId;
    private int quantity;
    private Double unitPrice;

    public OrderDetail(int orderId, int productId, int quantity, Double unitPrice) {
        this.id=++autoId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return String.valueOf(productId);
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
