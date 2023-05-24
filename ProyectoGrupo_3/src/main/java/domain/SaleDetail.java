package domain;

import java.time.LocalDateTime;

public class SaleDetail {
    private int id;
    private int saleId;
    private static int autoId;// id autogenerado
    private int productId;
    private int quantity;
    private Double unitPrice;



    public SaleDetail(int saleId, int productId, int quantity, Double unitPrice) {
        this.id=++autoId;
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
