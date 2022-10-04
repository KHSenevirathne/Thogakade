package dao.custom;

import dao.SuperDAO;
import view.tm.OrderDBtm;
import view.tm.ReportTables;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface OrderDAO extends SuperDAO {

    boolean saveOrderToDBTable(OrderDBtm o) throws SQLException, ClassNotFoundException;

    ArrayList<String> getYears() throws SQLException, ClassNotFoundException ;

    ArrayList<String> getDay() throws SQLException, ClassNotFoundException;

    ArrayList<ReportTables> getYearlyDetails(String year) throws SQLException, ClassNotFoundException;

    ArrayList<ReportTables> getMonthlyDetails(String month, String year) throws SQLException, ClassNotFoundException;

    ArrayList<ReportTables> getDailyDetails(LocalDate date) throws SQLException, ClassNotFoundException ;

    ArrayList<ReportTables> getCustomerViceIncome(String code) throws SQLException, ClassNotFoundException ;
}
