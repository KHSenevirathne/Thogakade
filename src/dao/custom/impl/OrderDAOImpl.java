package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import view.tm.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean saveOrderToDBTable(OrderDBtm o) throws SQLException, ClassNotFoundException {
        /*Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO `Order` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, o.getoID());
            stm.setObject(2, o.getoDate());
            stm.setObject(3, o.getTime());
            stm.setObject(4, o.getcID());
            stm.setObject(5, o.getTotDiscount());
            stm.setObject(6, o.getTotPrice());
            return stm.executeUpdate() > 0;*/
        return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES(?,?,?,?,?,?)",o.getoID(),o.getoDate(),o.getTime(),o.getcID(),o.getTotDiscount(),o.getTotPrice());
    }

    @Override
    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> year = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT YEAR(orderDate) FROM `order`");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT YEAR(orderDate) FROM `order`");
        while (rst.next()) {
            if(isYearExists(rst.getString(1),year)){

            }else {
                year.add(rst.getString(1));
            }
        }
        return year;
    }

    private boolean isYearExists(String string , ArrayList<String> year){
        for(String y : year){
            if(y.equals(string)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> getDay() throws SQLException, ClassNotFoundException {
        ArrayList<String> year = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT DAY(orderDate) FROM `order`");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT DAY(orderDate) FROM `order`");
        if (rst.next()) {
            year.add(rst.getString(1));
        }
        return year;
    }

    @Override
    public ArrayList<ReportTables> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<ReportTables> yearlyDetails = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `order` WHERE YEAR(orderDate)='"+year+"'");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `order` WHERE YEAR(orderDate)='"+year+"'");
        while (rst.next()) {
            ReportTables s = new ReportTables(
                    rst.getString(1),
                    rst.getString(4),
                    rst.getString(3),
                    Double.parseDouble(rst.getString(5)),
                    Double.parseDouble(rst.getString(6))
            );
            yearlyDetails.add(s);
        }
        return yearlyDetails;
    }

    @Override
    public ArrayList<ReportTables> getMonthlyDetails(String month,String year) throws SQLException, ClassNotFoundException {
        ArrayList<ReportTables> monthlyDetails = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `order` WHERE MONTHNAME(orderDate)=? AND YEAR(orderDate)=?");
        stm.setObject(1,month);
        stm.setObject(2,year);
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `order` WHERE MONTHNAME(orderDate)=? AND YEAR(orderDate)=?",month,year);
        while (rst.next()) {
            ReportTables s = new ReportTables(
                    rst.getString(1),
                    rst.getString(4),
                    rst.getString(3),
                    Double.parseDouble(rst.getString(5)),
                    Double.parseDouble(rst.getString(6))
            );
            monthlyDetails.add(s);
        }
        return monthlyDetails;
    }

    @Override
    public ArrayList<ReportTables> getDailyDetails(LocalDate date) throws SQLException, ClassNotFoundException {
        ArrayList<ReportTables> monthlyDetails = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `order` WHERE orderDate='"+date+"'");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `order` WHERE orderDate='"+date+"'");
        while (rst.next()) {
            ReportTables s = new ReportTables(
                    rst.getString(1),
                    rst.getString(4),
                    rst.getString(3),
                    Double.parseDouble(rst.getString(5)),
                    Double.parseDouble(rst.getString(6))
            );
            monthlyDetails.add(s);
        }
        return monthlyDetails;
    }

    @Override
    public ArrayList<ReportTables> getCustomerViceIncome(String code) throws SQLException, ClassNotFoundException {
        ArrayList<ReportTables> customer = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `Order` WHERE cID='"+code+"'");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE cID='"+code+"'");
        while (rst.next()) {
            ReportTables s = new ReportTables(
                    rst.getString(1),
                    rst.getString(4),
                    rst.getString(3),
                    Double.parseDouble(rst.getString(5)),
                    Double.parseDouble(rst.getString(6))
            );
            customer.add(s);
        }
        return customer;
    }

}
