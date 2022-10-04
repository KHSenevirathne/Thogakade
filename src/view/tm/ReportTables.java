package view.tm;

public class ReportTables {
    private String OID;
    private String CID;
    private String time;
    private  double totDiscount;
    private  double totPrice;

    public ReportTables(String OID, String CID, String time, double totDiscount, double totPrice) {
        this.OID = OID;
        this.CID = CID;
        this.time = time;
        this.totDiscount = totDiscount;
        this.totPrice = totPrice;
    }

    public ReportTables() {
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        return "ReportTables{" +
                "OID='" + OID + '\'' +
                ", CID='" + CID + '\'' +
                ", time='" + time + '\'' +
                ", totDiscount=" + totDiscount +
                ", totPrice=" + totPrice +
                '}';
    }
}
