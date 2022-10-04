package dto;

public class SearchMinMaxItem {
    private String itemCode;
    private int sumOfQTY;

    public SearchMinMaxItem() {
    }

    public SearchMinMaxItem(String itemCode) {
        this.itemCode = itemCode;
    }

    public SearchMinMaxItem(String itemCode, int sumOfQTY) {
        this.itemCode = itemCode;
        this.sumOfQTY = sumOfQTY;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getSumOfQTY() {
        return sumOfQTY;
    }

    public void setSumOfQTY(int sumOfQTY) {
        this.sumOfQTY = sumOfQTY;
    }

    @Override
    public String toString() {
        return "SearchMinMaxItem{" +
                "itemCode='" + itemCode + '\'' +
                ", sumOfQTY=" + sumOfQTY +
                '}';
    }
}
