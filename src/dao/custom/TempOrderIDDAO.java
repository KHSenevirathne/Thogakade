package dao.custom;

import bo.SuperBO;
import dao.SuperDAO;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TempOrderIDDAO extends SuperDAO {

    String setOrderIDS() throws SQLException, ClassNotFoundException;

    void saveIDTOTempTable(String orderID) throws SQLException, ClassNotFoundException;
}
