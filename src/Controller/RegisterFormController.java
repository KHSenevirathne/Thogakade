package Controller;

import dao.custom.impl.UserDAOImpl;
import dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class RegisterFormController {
    public AnchorPane registerFormContext;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtMail;
    public ComboBox<String> cmbUserType;
    public TextField txtUserName;
    public TextField txtPassword;
    public PasswordField txtConfirmPassword;
    Stage stage = null;

    public void initialize(){
        cmbUserType.getItems().setAll("Cashier","Admin");

        cmbUserType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            txtUserName.requestFocus();
        });
    }

    public void goBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) registerFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    /*public void goBackOnAction1(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        registerFormContext.getChildren().clear();
        registerFormContext.getChildren().add(load);
    }

    public void closeTheProgramOnAction1(ContextMenuEvent contextMenuEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }*/

    public void RegisterOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
            if (!txtFirstName.getText().equals("") && !txtLastName.getText().equals("") && !cmbUserType.getSelectionModel().getSelectedItem().equals("null") && !txtMail.getText().equals("") && !txtUserName.getText().equals("") && !txtPassword.getText().equals("") && !txtConfirmPassword.getText().equals("")) {
                User user = new User(txtFirstName.getText(), txtLastName.getText(), cmbUserType.getSelectionModel().getSelectedItem(), txtMail.getText(), txtUserName.getText(), txtPassword.getText());
                if (new UserDAOImpl().setInfo(user)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Registered Successfully").show();
                    txtFirstName.clear();
                    txtLastName.clear();
                    txtMail.clear();
                    cmbUserType.getSelectionModel().clearSelection();
                    txtUserName.clear();
                    txtPassword.clear();
                    txtConfirmPassword.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Fill all Fields and Try Again..").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Password Confirmation is incorrect..!").show();
        }
    }

    /*public void goToLastNameTXT(ActionEvent actionEvent) {
        txtLastName.requestFocus();
    }

    public void goToEnterPasswordTXT(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void goToConfirmPasswordTXT(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void goToRegisterBTN(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegisterOnAction(actionEvent);
    }

    public void goToEmailTXT(ActionEvent actionEvent) {
        txtMail.requestFocus();
    }

    public void minimizeOnACtion2(MouseEvent mouseEvent) {
        stage = (Stage) registerFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnACtion1(MouseEvent mouseEvent) {
        stage = (Stage) registerFormContext.getScene().getWindow();
        stage.setIconified(true);
    }*/
}
