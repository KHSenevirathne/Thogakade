package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {
    public AnchorPane dashBoardContext;
    public Label timeShow;
    public Label meridiemShow;
    public Label dateShow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    Stage stage = null;

    public void goToCashierLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierLogin.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    public void goToAdministratorLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdministratorLogin.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardContext.getChildren().clear();
        dashBoardContext.getChildren().add(load);
    }

    /*public void closeTheProgramOnAction1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeOnAction1(MouseEvent mouseEvent) {
        stage = (Stage) dashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnAction2(MouseEvent mouseEvent) {
        stage = (Stage) dashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }*/
}
