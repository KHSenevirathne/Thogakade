package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.TempOrderIDDAO;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TempOrderIDDAOImpl implements TempOrderIDDAO {

    @Override
    public String setOrderIDS() throws SQLException, ClassNotFoundException {
        //ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT oId FROM TempOrderID ORDER BY oId DESC LIMIT 1").executeQuery();
        ResultSet rst = CrudUtil.executeQuery("SELECT oId FROM TempOrderID ORDER BY oId DESC LIMIT 1");
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<=99){
                return "O-0"+tempId;
            }else{
                return "O-"+tempId;
            }
        }else{
            return "O-001";
        }
    }

    @Override
    public void saveIDTOTempTable(String orderID) throws SQLException, ClassNotFoundException {
        /*Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO `TempOrderID` VALUES(?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, orderID);
        stm.executeUpdate();*/
        CrudUtil.executeUpdate("INSERT INTO `TempOrderID` VALUES(?)",orderID);
    }
}
