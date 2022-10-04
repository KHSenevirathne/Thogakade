package dao.custom.impl;

import dto.User;
import dao.CrudUtil;
import dao.custom.UserDAO;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean setInfo(User u) throws SQLException, ClassNotFoundException {
        /*Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO LoginDetail VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getFirstName());
        stm.setObject(2,u.getLastName());
        stm.setObject(3,u.getUserType());
        stm.setObject(4,u.getEmail());
        stm.setObject(5,u.getUserName());
        stm.setObject(6,u.getPassword());
        return stm.executeUpdate()>0;*/
        return CrudUtil.executeUpdate("INSERT INTO LoginDetail VALUES(?,?,?,?,?,?)",u.getFirstName(),u.getLastName(),u.getUserType(),u.getEmail(),u.getUserName(),u.getPassword());
    }

    @Override
    public ArrayList<String> getUserTypes() throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM LoginDetail");
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM LoginDetail");
        ArrayList<String> users = new ArrayList<>();
        while (rst.next()) {
            users.add(rst.getString(3));
        }
        return users;
    }

    @Override
    public String getUserInfo(String type, String txtUserName,String txtPassword) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `LoginDetail` WHERE userType='"+type+"'");
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `LoginDetail` WHERE userType='"+type+"'");
        while (rst.next()){
            if(rst.getString(5).equals(txtUserName) && rst.getString(6).equals(txtPassword)){
                return rst.getString(1);
            }
        }
        return null;
    }
}
