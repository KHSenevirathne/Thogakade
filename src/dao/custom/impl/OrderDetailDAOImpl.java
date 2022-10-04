package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DbConnection;
import javafx.collections.ObservableList;
import view.tm.SavedOrderDetailsTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAOImpl implements OrderDetailDAO{

    @Override
    public void saveOrderToOrderDetailTable(String orderID , ObservableList<SavedOrderDetailsTM> obList) throws SQLException, ClassNotFoundException {
        try {
            /*Connection con = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO `Order Detail` VALUES(?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);*/
            for (SavedOrderDetailsTM o : obList) {
                /*stm.setObject(1, orderID);
                stm.setObject(2, o.getItemCode());
                stm.setObject(3, o.getQtyForSell());
                stm.setObject(4, o.getDiscount());
                stm.setObject(5, o.getTotal());
                stm.executeUpdate();*/
                CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?,?)",orderID,o.getItemCode(),o.getQtyForSell(),o.getDiscount(),o.getTotal());
            }

        }catch (Exception e){}
    }
}
