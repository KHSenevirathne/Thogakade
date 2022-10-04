package bo.custom.impl;

import dto.Customer;
import dto.Item;
import bo.custom.SaveOrderBO;
import dao.DAOFactory;
import dao.custom.*;
import view.tm.ItemDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaveOrderBOImpl implements SaveOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    private final SavedOrderDAO savedOrderDAO = (SavedOrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.SAVEDORDER);
    private final TempOrderIDDAO tempOrderID = (TempOrderIDDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.TEMPORDERID);


    @Override
    public List<String> getAllItemDescriptions() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemDescriptions();
    }

    @Override
    public Item search(String descript) throws SQLException, ClassNotFoundException {
        return itemDAO.search(descript);
    }

    @Override
    public Customer getCustomer(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomer(nic);
    }

    @Override
    public boolean update(Customer c) throws SQLException, ClassNotFoundException {
        return customerDAO.update(c);
    }

    @Override
    public Item getItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.getItem(code);
    }

    @Override
    public boolean saveOrder(String orderId, String custNIC, ArrayList<ItemDetails> items) throws SQLException, ClassNotFoundException {
        return savedOrderDAO.saveOrder(orderId,custNIC,items);
    }

    @Override
    public void saveIDTOTempTable(String orderID) throws SQLException, ClassNotFoundException {
        tempOrderID.saveIDTOTempTable(orderID);
    }

    @Override
    public String setCustomerIDS() throws SQLException, ClassNotFoundException {
        return customerDAO.setCustomerIDS();
    }

    @Override
    public String setOrderIDS() throws SQLException, ClassNotFoundException {
        return tempOrderID.setOrderIDS();
    }

    @Override
    public boolean add(Customer c) throws SQLException, ClassNotFoundException {
        return customerDAO.add(c);
    }
}
