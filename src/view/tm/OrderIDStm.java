package view.tm;

public class OrderIDStm {
    private String orderID;
    private String nic;

    public OrderIDStm(String orderID, String nic) {
        this.orderID = orderID;
        this.nic = nic;
    }

    public OrderIDStm() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "OrderIDStm{" +
                "orderID='" + orderID + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
