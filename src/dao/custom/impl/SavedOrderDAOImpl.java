package dao.custom.impl;

import dto.SavedOrder;
import dao.CrudUtil;
import dao.custom.SavedOrderDAO;
import db.DbConnection;
import javafx.scene.control.Label;
import view.tm.ItemDetails;
import view.tm.SavedOrderDetailsTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SavedOrderDAOImpl implements SavedOrderDAO {

    @Override
    public boolean saveOrder(String orderId, String custNIC, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
        int count=0;
        for(ItemDetails temp : items) {
            /*Connection con = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO `SavedOrder` VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, orderId);
            stm.setObject(2, custNIC);
            stm.setObject(3, temp.getItemCode());
            stm.setObject(4, temp.getDescription());
            stm.setObject(5, temp.getQtyForSell());
            stm.setObject(6, temp.getDiscount());
            stm.setObject(7, temp.getTotal());
            stm.executeUpdate();*/
            CrudUtil.executeUpdate("INSERT INTO `SavedOrder` VALUES(?,?,?,?,?,?,?)",orderId,custNIC,temp.getItemCode(),temp.getDescription(),temp.getQtyForSell(),temp.getDiscount(),temp.getTotal());
            count++;
        }
        if(count!=0){
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<SavedOrder> getAllOrderIDSWithNIC() throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `SavedOrder`");
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `SavedOrder`");
        ArrayList<SavedOrder> temp = new ArrayList<>();
        while(rst.next()) {
            SavedOrder o =new SavedOrder(
                    rst.getString(1),
                    rst.getString(2)
            );
            temp.add(o);
        }
        return temp;
    }

    @Override
    public String getNIC(String orderID) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM savedorder WHERE oId='"+orderID+"'");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM savedorder WHERE oId='"+orderID+"'");
        if (rst.next()) {
            return rst.getString(2);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<SavedOrderDetailsTM> getOrderDetails(String newValue, Label lblCustNIC) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM  `savedorder` WHERE oId='"+newValue+"'");
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM  `savedorder` WHERE oId='"+newValue+"'");
        ArrayList<SavedOrderDetailsTM> items = new ArrayList<>();
        while (rst.next()){
            items.add(new SavedOrderDetailsTM(rst.getString(3),rst.getString(4),rst.getInt(5),rst.getDouble(6),rst.getDouble(7)));
            lblCustNIC.setText(rst.getString(2));
        }
        return items;
    }

    @Override
    public boolean saveOrderByObject(String oID,String custNIC,SavedOrderDetailsTM o) throws SQLException, ClassNotFoundException {
        /*Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO `SavedOrder` VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, oID);
        stm.setObject(2, custNIC);
        stm.setObject(3, o.getItemCode());
        stm.setObject(4, o.getDescription());
        stm.setObject(5, o.getQtyForSell());
        stm.setObject(6, o.getDiscount());
        stm.setObject(7, o.getTotal());
        return stm.executeUpdate()>0;*/
        return CrudUtil.executeUpdate("INSERT INTO `SavedOrder` VALUES(?,?,?,?,?,?,?)",oID,custNIC,o.getItemCode(),o.getDescription(),o.getQtyForSell(),o.getDiscount(),o.getTotal());
    }

    @Override
    public boolean updateSavedOrder(String cNIC,String iCode,int q,double d,double t) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `SavedOrder` SET quantity=?, discount=?, total=?  WHERE custNIC='"+cNIC+"' AND itemCode='"+iCode+"'");
        stm.setObject(5,q);
        stm.setObject(6,d);
        stm.setObject(7,t);
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteOrderFromSavedOrderTable(String orderID) throws SQLException, ClassNotFoundException {
        if (CrudUtil.executeUpdate("DELETE FROM `SavedOrder` WHERE oId='"+orderID+"'")){
            return true;
        }else{
            return false;
        }
    }
}
