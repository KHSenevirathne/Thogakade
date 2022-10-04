package bo.custom;

import dto.Item;
import bo.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    ArrayList<Item> getAllItems() throws SQLException, ClassNotFoundException;

    boolean addItem(Item itemDTO) throws SQLException, ClassNotFoundException;

    boolean updateItem(Item itemDTO) throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
