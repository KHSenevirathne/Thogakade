package Controller;

import dao.custom.impl.UserDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AdministratorLoginController implements Initializable {
    public AnchorPane AdministratorContext;
    public Button btnAdministrator;
    public Label lblNotAAdmin;
    public JFXButton btnSignUP;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label timeShow;
    public Label meridiemShow;
    public Label dateShow;
    Stage stage = null;

    static String firstNameOfAdmin;

    /*public void initialize() throws SQLException, ClassNotFoundException {
        btnAdministrator.setDisable(true);
        ArrayList<String> userTypes = new UserDAOImpl().getUserTypes();
        for(String user : userTypes){
            if(user.equals("Admin")){
                btnSignUP.setVisible(false);
                btnSignUP.setDisable(true);
                lblNotAAdmin.setVisible(false);
            }
        }
    }*/

    public void goToCashierLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierLogin.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorContext.getChildren().clear();
        AdministratorContext.getChildren().add(load);
    }

    public void goToRegisterFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorContext.getChildren().clear();
        AdministratorContext.getChildren().add(load);
    }

    public void loginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String include = new UserDAOImpl().getUserInfo("Admin",txtUserName.getText(),txtPassword.getText());
        if(include!=null) {
            URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            AdministratorContext.getChildren().clear();
            AdministratorContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING, "User Name or Password is incorrect..please Try again..!").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        btnAdministrator.setDisable(true);
        ArrayList<String> userTypes = null;
        try {
            userTypes = new UserDAOImpl().getUserTypes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(String user : userTypes){
            if(user.equals("Admin")){
                btnSignUP.setVisible(false);
                btnSignUP.setDisable(true);
                lblNotAAdmin.setVisible(false);
            }
        }

        Thread clock = new Thread(){
            public void run() {
                Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
                    Date time = new Date();

                    SimpleDateFormat meridiemFormat = new SimpleDateFormat("a");
                    Date meridiem = new Date();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();

                    timeShow.setText(timeFormat.format(time));
                    meridiemShow.setText(meridiemFormat.format(meridiem));
                    dateShow.setText(dateFormat.format(date));

                }),
                        new KeyFrame(Duration.seconds(1))
                );
                clock.setCycleCount(Animation.INDEFINITE);
                clock.play();
            }
        };
        clock.start();
    }

    /*public boolean getUserInfo(String type,String txtUserName,String txtPassword) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `LoginDetail` WHERE userType='"+type+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            if(rst.getString(5).equals(txtUserName) && rst.getString(6).equals(txtPassword)){
                firstNameOfAdmin = rst.getString(1);
                return true;
            }
        }
        return false;
    }*/

   /* public void goToPasswordTXT(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void goToLoginBTN(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        loginOnAction(actionEvent);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeOnAction2(MouseEvent mouseEvent) {
        stage = (Stage) AdministratorContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnAction1(MouseEvent mouseEvent) {
        stage = (Stage) AdministratorContext.getScene().getWindow();
        stage.setIconified(true);
    }*/
}
