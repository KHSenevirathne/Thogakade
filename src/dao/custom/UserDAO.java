package dao.custom;

import dto.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {

    boolean setInfo(User u) throws SQLException, ClassNotFoundException;

    ArrayList<String> getUserTypes() throws SQLException, ClassNotFoundException ;

    String getUserInfo(String type, String txtUserName,String txtPassword) throws SQLException, ClassNotFoundException;
}
