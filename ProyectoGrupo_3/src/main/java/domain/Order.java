package domain;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private static int autoId;// id autogenerado
    private java.time.LocalDateTime orderDate;
    private String orderStatus;
    private int supplierId;
    private Double totalCost;
    private String remarks;

    public Order(LocalDateTime orderDate, String orderStatus, int supplierId, Double totalCost, String remarks) {
        this.id=++autoId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.supplierId = supplierId;
        this.totalCost = totalCost;
        this.remarks = remarks;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        Order.autoId = autoId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Order: " +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", supplierId=" + supplierId +
                ", totalCost=" + totalCost +
                ", remarks='" + remarks + '\'';
    }

}
