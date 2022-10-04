package bo.custom;

import dto.Customer;
import bo.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    ArrayList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException;

    boolean addCustomer(Customer customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Customer customerDTO) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
