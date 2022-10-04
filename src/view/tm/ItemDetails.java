package view.tm;

public class ItemDetails {
    private String itemCode;
    private String description;
    private int qtyForSell;
    private double discount;
    private double total;

    public ItemDetails(String itemCode, String description, int qtyForSell, double discount, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyForSell = qtyForSell;
        this.discount = discount;
        this.total = total;
    }

    public ItemDetails() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
