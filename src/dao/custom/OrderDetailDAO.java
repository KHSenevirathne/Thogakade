package dao.custom;

import dao.SuperDAO;
import db.DbConnection;
import javafx.collections.ObservableList;
import view.tm.SavedOrderDetailsTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface OrderDetailDAO extends SuperDAO {
    void saveOrderToOrderDetailTable(String orderID, ObservableList<SavedOrderDetailsTM> obList) throws SQLException, ClassNotFoundException;
}
