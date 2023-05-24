package domain;

import java.time.LocalDateTime;

public class Sale {
    private int id;
    private static int autoId;// id autogenerado
    private java.time.LocalDateTime saleDate;
    private int customerId;
    private String annotations;

    public Sale(LocalDateTime saleDate, int customerId, String annotations) {
        this.id=++autoId;
        this.saleDate = saleDate;
        this.customerId = customerId;
        this.annotations = annotations;
    }
}
