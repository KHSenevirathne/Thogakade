package dao.custom.impl;

import dto.Customer;
import dao.CrudUtil;
import dao.custom.CustomerDAO;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

   /* @Override
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,c.getCustomerID());
        stm.setObject(2,c.getCustomerTitle());
        stm.setObject(3,c.getCustomerName());
        stm.setObject(4,c.getCustomerAddress());
        stm.setObject(5,c.getCity());
        stm.setObject(6,c.getProvince());
        stm.setObject(7,c.getPostalCode());
        stm.setObject(8,c.getNationalID());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET custID=?, custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=?, nationalID=?  WHERE nationalID='"+c.getNationalID()+"'");
        stm.setObject(1,c.getCustomerID());
        stm.setObject(2,c.getCustomerTitle());
        stm.setObject(3,c.getCustomerName());
        stm.setObject(4,c.getCustomerAddress());
        stm.setObject(5,c.getCity());
        stm.setObject(6,c.getProvince());
        stm.setObject(7,c.getPostalCode());
        stm.setObject(8,c.getNationalID());
        return stm.executeUpdate()>0;
    }


    @Override
    public Customer getCustomer(String nic) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Customer WHERE nationalID='"+nic+"'");

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );

        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return customers;
    }
*/
    @Override
    public String setCustomerIDS() throws SQLException, ClassNotFoundException {
        //ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT custID FROM Customer ORDER BY custID DESC LIMIT 1").executeQuery();
        ResultSet rst = CrudUtil.executeQuery("SELECT custID FROM Customer ORDER BY custID DESC LIMIT 1");
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "C-00"+tempId;
            }else if(tempId<=99){
                return "C-0"+tempId;
            }else{
                return "C-"+tempId;
            }
        }else{
            return "C-001";
        }
    }

    @Override
    public ArrayList<String> getCustomerIDS() throws SQLException, ClassNotFoundException {
        ArrayList<String> custIDS = new ArrayList<>();
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            custIDS.add(rst.getString(1));
        }
        return custIDS;
    }

    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        /*Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,c.getCustomerID());
        stm.setObject(2,c.getCustomerTitle());
        stm.setObject(3,c.getCustomerName());
        stm.setObject(4,c.getCustomerAddress());
        stm.setObject(5,c.getCity());
        stm.setObject(6,c.getProvince());
        stm.setObject(7,c.getPostalCode());
        stm.setObject(8,c.getNationalID());
        return stm.executeUpdate()>0;*/

        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?)",c.getCustomerID(),c.getCustomerTitle(),c.getCustomerName(),c.getCustomerAddress(),c.getCity(),c.getProvince(),c.getPostalCode(),c.getNationalID());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        new Alert(Alert.AlertType.WARNING, "Not Implemented").show();
        return false;
    }

    @Override
    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET custID=?, custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=?, nationalID=?  WHERE nationalID='"+c.getNationalID()+"'");
        stm.setObject(1,c.getCustomerID());
        stm.setObject(2,c.getCustomerTitle());
        stm.setObject(3,c.getCustomerName());
        stm.setObject(4,c.getCustomerAddress());
        stm.setObject(5,c.getCity());
        stm.setObject(6,c.getProvince());
        stm.setObject(7,c.getPostalCode());
        stm.setObject(8,c.getNationalID());
        return stm.executeUpdate()>0;*/

        return CrudUtil.executeUpdate("UPDATE Customer SET custID=?, custTitle=?, custName=?, custAddress=?, city=?, province=?, postalCode=?, nationalID=?  WHERE nationalID='"+c.getNationalID()+"'",c.getCustomerID(),c.getCustomerTitle(),c.getCustomerName(),c.getCustomerAddress(),c.getCity(),c.getProvince(),c.getPostalCode(),c.getNationalID());
    }

    @Override
    public Customer search(String nic) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Customer WHERE nationalID='"+nic+"'");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE nationalID='"+nic+"'");
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );

        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return customers;
    }

    @Override
    public Customer getCustomer(String nic) throws SQLException, ClassNotFoundException {
        /*PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Customer WHERE nationalID='"+nic+"'");

        ResultSet rst = stm.executeQuery();*/
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE nationalID='"+nic+"'");
        if (rst.next()) {
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );

        } else {
            return null;
        }
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT custID FROM Customer WHERE custID=?", id).next();
    }
}
