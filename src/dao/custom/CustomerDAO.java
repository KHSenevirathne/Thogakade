package dao.custom;

import dto.Customer;
import dao.CrudDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer, String> {
    /*public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException ;

    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;*/
    Customer getCustomer(String nic) throws SQLException, ClassNotFoundException;

    String setCustomerIDS() throws SQLException, ClassNotFoundException;

    ArrayList<String> getCustomerIDS() throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;
}
