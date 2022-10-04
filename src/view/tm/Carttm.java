package view.tm;

public class Carttm {
    private String itemCode;
    private String itemDescription;
    private int orderQuantity;
    private double discount;
    private double price;

    public Carttm(String itemCode, String itemDescription, int orderQuantity, double discount, double price) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.orderQuantity = orderQuantity;
        this.discount = discount;
        this.price = price;
    }

    public Carttm() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ListOfItem{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
