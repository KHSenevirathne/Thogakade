package dao.custom;

import dto.SavedOrder;
import dao.SuperDAO;
import javafx.scene.control.Label;
import view.tm.ItemDetails;
import view.tm.SavedOrderDetailsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SavedOrderDAO  extends SuperDAO {

    boolean saveOrder(String orderId, String custNIC, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException ;

    ArrayList<SavedOrder> getAllOrderIDSWithNIC() throws SQLException, ClassNotFoundException ;

    String getNIC(String orderID) throws SQLException, ClassNotFoundException ;

    ArrayList<SavedOrderDetailsTM> getOrderDetails(String newValue, Label lblCustNIC) throws SQLException, ClassNotFoundException ;

    boolean saveOrderByObject(String oID, String custNIC, SavedOrderDetailsTM o) throws SQLException, ClassNotFoundException;

    boolean updateSavedOrder(String cNIC, String iCode, int q, double d, double t) throws SQLException, ClassNotFoundException ;

    boolean deleteOrderFromSavedOrderTable(String orderID) throws SQLException, ClassNotFoundException ;
}
