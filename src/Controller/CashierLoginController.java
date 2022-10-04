package Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.UserDAOImpl;
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
import java.util.Date;
import java.util.ResourceBundle;

public class CashierLoginController implements Initializable {
    public AnchorPane cashierContext;
    public Button btnCashier;
    public TextField txtUserName;
    public PasswordField txtPassword;
    static String firstNameOfCashier;
    public Label timeShow;
    public Label meridiemShow;
    public Label dateShow;
    Stage  stage = null;

    /*public void initialize(){
        btnCashier.setDisable(true);
    }*/

    public void goToAdministratorLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdministratorLogin.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }

/*    public void goToRegisterFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierContext.getChildren().clear();
        cashierContext.getChildren().add(load);
    }

*//*    public void closeTheProgramOnAction1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }*/

    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String include = new UserDAOImpl().getUserInfo("Cashier",txtUserName.getText(),txtPassword.getText());
        if(include!=null) {
            URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            cashierContext.getChildren().clear();
            cashierContext.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.WARNING, "User Name or Password is incorrect..please Try again..!").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCashier.setDisable(true);

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


    /*public boolean getUserInfo(String type) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `LoginDetail` WHERE userType='"+type+"'");
        ResultSet rst = stm.executeQuery();

        while (rst.next()){
            if(rst.getString(5).equals(txtUserName.getText()) && rst.getString(6).equals(txtPassword.getText())){
                firstNameOfCashier = rst.getString(1);
               return true;
            }
        }
        return false;
    }*/

    /*public void goToPasswordTXT(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void goToLoginBTN(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        loginOnAction(actionEvent);
    }

    public void minimizeOnAction2(MouseEvent mouseEvent) {
        stage = (Stage) cashierContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnAction1(MouseEvent mouseEvent) {
        stage = (Stage) cashierContext.getScene().getWindow();
        stage.setIconified(true);
    }*/

}
