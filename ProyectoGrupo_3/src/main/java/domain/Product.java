package domain;

public class Product {
    private int id;
    private static int autoId;// id autogenerado
    private String description;
    private Double price;
    private int currentStock;
    private Integer minimumStock;
    private int supplierId;

    public Product( String description, Double price, int currentStock, Integer minimumStock, int supplierId) {
        this.id=++autoId;
        this.description = description;
        this.price = price;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        this.supplierId = supplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public Integer getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(Integer minimumStock) {
        this.minimumStock = minimumStock;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currentStock=" + currentStock +
                ", minimumStock=" + minimumStock +
                ", supplierId=" + supplierId +
                '}';
    }
}
