package bo.custom;

import dto.Customer;
import dto.Item;
import bo.SuperBO;
import view.tm.ItemDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SaveOrderBO extends SuperBO {
    List<String> getAllItemDescriptions() throws SQLException, ClassNotFoundException;
    Item search(String descript) throws SQLException, ClassNotFoundException;
    Customer getCustomer(String nic) throws SQLException, ClassNotFoundException;
    boolean update(Customer c) throws SQLException, ClassNotFoundException;
    Item getItem(String code) throws SQLException, ClassNotFoundException;
    boolean saveOrder(String orderId, String custNIC, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException;
    void saveIDTOTempTable(String orderID) throws SQLException, ClassNotFoundException;
    String setCustomerIDS() throws SQLException, ClassNotFoundException;
    String setOrderIDS() throws SQLException, ClassNotFoundException;
    boolean add(Customer c) throws SQLException, ClassNotFoundException;
}
