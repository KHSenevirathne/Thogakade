package view.tm;

public class OrderDBtm {
    private String oID;
    private String oDate;
    private String time;
    private  String cID;
    private  double totDiscount;
    private  double totPrice;

    public OrderDBtm(String oID, String oDate, String time, String cID, double totDiscount, double totPrice) {
        this.oID = oID;
        this.oDate = oDate;
        this.time = time;
        this.cID = cID;
        this.totDiscount = totDiscount;
        this.totPrice = totPrice;
    }
    public OrderDBtm(String oID, String cID,String time, double totDiscount, double totPrice) {
        this.oID = oID;
        this.cID = cID;
        this.time = time;
        this.totDiscount = totDiscount;
        this.totPrice = totPrice;
    }

    public String getoID() {
        return oID;
    }

    public void setoID(String oID) {
        this.oID = oID;
    }

    public String getoDate() {
        return oDate;
    }

    public void setoDate(String oDate) {
        this.oDate = oDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public double getTotDiscount() {
        return totDiscount;
    }

    public void setTotDiscount(double totDiscount) {
        this.totDiscount = totDiscount;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    @Override
    public String toString() {
        return "OrderDBtm{" +
                "oID='" + oID + '\'' +
                ", oDate='" + oDate + '\'' +
                ", time='" + time + '\'' +
                ", cID='" + cID + '\'' +
                ", totDiscount=" + totDiscount +
                ", totPrice=" + totPrice +
                '}';
    }
}
