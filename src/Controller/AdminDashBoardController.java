package Controller;

import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dto.Customer;
import dto.Item;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tm.ReportTables;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static Controller.AdministratorLoginController.firstNameOfAdmin;

public class AdminDashBoardController {
    public AnchorPane adminDashBoardContext;
    public Label lblItemCode1;
    public Label lblItemCode2;
    public Label lblItemCode3;
    public TextField txtDescription1;
    public TextField txtPackSize1;
    public TextField txtUnitPrice1;
    public TextField txtQTYOnHand1;
    public TextField txtDiscount1;
    public TextField txtDescription2;
    public TextField txtPackSize2;
    public TextField txtUnitPrice2;
    public TextField txtQTYOnHand2;
    public TextField txtDiscount2;
    public JFXTextField txtSearchItemCode1;
    public JFXTextField txtSearchItemCode2;
    public Button btnUpdateItem;
    public TextField txtDescription3;
    public TextField txtPackSize3;
    public TextField txtUnitPrice3;
    public TextField txtQTYOnHand3;
    public TextField txtDiscount3;
    public Button btnDelete;
    public Label lblAdminName;
    public TextField txtDate;
    public TextField txtTime;
    public TableView tblAnnualIncome;
    public TableColumn colANNOID;
    public TableColumn colCustID;
    public TableColumn colAnnualTime;
    public TableColumn colAnnualDiscount;
    public TableColumn colAnnualPrice;
    public ComboBox<String> cmbYearForAnnualIncome;
    public Label lblAnnualIncome;
    public ComboBox<String> cmbMothForMonthlyIncome;
    public ComboBox<String> cmbYearForMonthlyIncome1;
    public TableView tblMonthlyIncome;
    public TableColumn colOIDForMonthly;
    public TableColumn colCIDForMonthly;
    public TableColumn colOTimeForMonthly;
    public TableColumn colTDiscountForMonthly;
    public TableColumn colTPriceForMonthly;
    public DatePicker datePicker;
    public TableView tblDailyDetails;
    public TableColumn colOIDForDaily;
    public TableColumn colCIDForDaily;
    public TableColumn colOTimeForDaily;
    public TableColumn colTDiscountForDaily;
    public TableColumn colTPriceForDaily;
    public ComboBox<String> cmbCustomerIDS;
    public TableView tblCustomerVise;
    public TableColumn colOIDOfCustomer;
    public TableColumn colCIDOfCustomer;
    public TableColumn colOTimeOfCustomer;
    public TableColumn colTDiscountOfCustomer;
    public TableColumn colTPriceOfCustomer;
    public Label lblDailyIncome;
    public Label lblCustomerVice;
    public Label lblMonthlyIncome;
    public Label lblMostMovableItem;
    public Label lblLeastMovableItem;
    public TableView tblCustomerDetails;
    public TableColumn colCustomerID;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerCity;
    public TableColumn colCustomerProvince;
    public TableColumn colCustomerPostalCode;
    public TableColumn colCustomerNationalID;
    public TableView tblItemDetails;
    public TableColumn colItemCode;
    public TableColumn colItemDescription;
    public TableColumn colItemPackSize;
    public TableColumn colItemUnitPrice;
    public TableColumn colItemQuantityInStore;
    public TableColumn colItemDiscount;

    String monthForMonthly = null;
    String yearForMonthly = null;

    Stage stage = null;
    ObservableList<ReportTables> monthlyObList =FXCollections.observableArrayList();

    ArrayList<Customer> customer = new ArrayList<>();
    ArrayList<Item> item = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        lblItemCode1.setText(new ItemDAOImpl().setItemIDS());
        lblItemCode3.setText("");
        btnUpdateItem.setDisable(true);
        btnDelete.setDisable(true);
        txtDescription3.setEditable(false);
        txtPackSize3.setEditable(false);
        txtUnitPrice3.setEditable(false);
        txtQTYOnHand3.setEditable(false);
        txtDiscount3.setEditable(false);
        cmbMothForMonthlyIncome.setDisable(true);

        cmbCustomerIDS.getItems().setAll(new CustomerDAOImpl().getCustomerIDS());

        lblAdminName.setText(firstNameOfAdmin);
        loadDateAndTime();

        ArrayList<String> year = new OrderDAOImpl().getYears();
        cmbYearForAnnualIncome.getItems().setAll(year);
        cmbYearForMonthlyIncome1.getItems().setAll(year);

        cmbMothForMonthlyIncome.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");

        customer = new CustomerDAOImpl().getAll();
        item = new ItemDAOImpl().getAll();

        cmbYearForAnnualIncome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<ReportTables> yearlyDetails = new ArrayList<>();
            try {

                yearlyDetails = new OrderDAOImpl().getYearlyDetails(newValue);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateIncome(yearlyDetails,lblAnnualIncome);

            colANNOID.setCellValueFactory(new PropertyValueFactory<>("OID"));
            colCustID.setCellValueFactory(new PropertyValueFactory<>("CID"));
            colAnnualTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            colAnnualDiscount.setCellValueFactory(new PropertyValueFactory<>("totDiscount"));
            colAnnualPrice.setCellValueFactory(new PropertyValueFactory<>("totPrice"));

            tblAnnualIncome.setItems(FXCollections.observableArrayList(yearlyDetails));
        });

        cmbYearForMonthlyIncome1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!cmbYearForMonthlyIncome1.getSelectionModel().getSelectedItem().equals("null") && !cmbMothForMonthlyIncome.getSelectionModel().getSelectedItem().equals("null")) {
                    try {
                        monthlyObList.clear();
                        monthlyTableDetails(newValue, cmbYearForMonthlyIncome1.getValue());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }catch(Exception e){}

            cmbMothForMonthlyIncome.setDisable(false);
        });

        cmbMothForMonthlyIncome.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
            if(!cmbYearForMonthlyIncome1.getSelectionModel().getSelectedItem().equals("null") && !cmbMothForMonthlyIncome.getSelectionModel().getSelectedItem().equals("null")) {

                try {
                    monthlyObList.clear();
                    monthlyTableDetails(newValue,cmbYearForMonthlyIncome1.getValue());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            }catch(Exception e){}

        });


        datePicker.valueProperty().addListener((observable, oldDate, newDate)->{
            ArrayList<ReportTables> dailyDetails = new ArrayList<>();

            try {

                dailyDetails = new OrderDAOImpl().getDailyDetails(newDate);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            calculateIncome(dailyDetails,lblDailyIncome);

            colOIDForDaily.setCellValueFactory(new PropertyValueFactory<>("OID"));
            colCIDForDaily.setCellValueFactory(new PropertyValueFactory<>("CID"));
            colOTimeForDaily.setCellValueFactory(new PropertyValueFactory<>("time"));
            colTDiscountForDaily.setCellValueFactory(new PropertyValueFactory<>("totDiscount"));
            colTPriceForDaily.setCellValueFactory(new PropertyValueFactory<>("totPrice"));

            tblDailyDetails.setItems(FXCollections.observableArrayList(dailyDetails));

        });

        cmbCustomerIDS.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                ArrayList<ReportTables> customer = new OrderDAOImpl().getCustomerViceIncome(newValue);
                calculateIncome(customer,lblCustomerVice);
                colOIDOfCustomer.setCellValueFactory(new PropertyValueFactory<>("OID"));
                colCIDOfCustomer.setCellValueFactory(new PropertyValueFactory<>("CID"));
                colOTimeOfCustomer.setCellValueFactory(new PropertyValueFactory<>("time"));
                colTDiscountOfCustomer.setCellValueFactory(new PropertyValueFactory<>("totDiscount"));
                colTPriceOfCustomer.setCellValueFactory(new PropertyValueFactory<>("totPrice"));

                tblCustomerVise.setItems(FXCollections.observableArrayList(customer));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCustomerProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustomerNationalID.setCellValueFactory(new PropertyValueFactory<>("nationalID"));

        tblCustomerDetails.setItems(FXCollections.observableArrayList(customer));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemQuantityInStore.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colItemDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tblItemDetails.setItems(FXCollections.observableArrayList(item));
    }

    private void monthlyTableDetails (String month,String year) throws SQLException, ClassNotFoundException {
        ArrayList<ReportTables> monthlyDetails = new OrderDAOImpl().getMonthlyDetails(month,year);
        if(monthlyDetails.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Empty Orders",ButtonType.CLOSE).show();
            monthlyDetails.clear();
            colOIDForMonthly.setCellValueFactory(new PropertyValueFactory<>("OID"));
            colCIDForMonthly.setCellValueFactory(new PropertyValueFactory<>("CID"));
            colOTimeForMonthly.setCellValueFactory(new PropertyValueFactory<>("time"));
            colTDiscountForMonthly.setCellValueFactory(new PropertyValueFactory<>("totDiscount"));
            colTPriceForMonthly.setCellValueFactory(new PropertyValueFactory<>("totPrice"));

            tblMonthlyIncome.setItems(FXCollections.observableArrayList(monthlyDetails));
        }else {
            calculateIncome(monthlyDetails,lblMonthlyIncome);
            colOIDForMonthly.setCellValueFactory(new PropertyValueFactory<>("OID"));
            colCIDForMonthly.setCellValueFactory(new PropertyValueFactory<>("CID"));
            colOTimeForMonthly.setCellValueFactory(new PropertyValueFactory<>("time"));
            colTDiscountForMonthly.setCellValueFactory(new PropertyValueFactory<>("totDiscount"));
            colTPriceForMonthly.setCellValueFactory(new PropertyValueFactory<>("totPrice"));

            tblMonthlyIncome.setItems(FXCollections.observableArrayList(monthlyDetails));
        }
    }

    private void calculateIncome(ArrayList<ReportTables> temp,Label lbl){
        double netTotal=0.0;
        double tDiscount=0.0;
        double tPrice=0.0;
        for(ReportTables r : temp){
            tDiscount+= r.getTotDiscount();
            tPrice+= r.getTotPrice();
        }
        netTotal = tPrice - tDiscount;
        lbl.setText(String.valueOf(netTotal)+" /=");
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        txtDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            txtTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void closeTheProgramOnAction1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminDashBoardContext.getChildren().clear();
        adminDashBoardContext.getChildren().add(load);
    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtDescription1.getText().equals("") && !txtDiscount1.getText().equals("") && !txtPackSize1.getText().equals("") && !txtQTYOnHand1.getText().equals("") && !txtUnitPrice1.getText().equals("")){
            Item item = new Item(lblItemCode1.getText(),txtDescription1.getText(),txtPackSize1.getText(),Double.parseDouble(txtUnitPrice1.getText()), Integer.parseInt(txtQTYOnHand1.getText()),Double.parseDouble(txtDiscount1.getText()));
            if(new ItemDAOImpl().add(item)){
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved Successfully..!").show();
                txtDescription1.clear();
                txtPackSize1.clear();
                txtUnitPrice1.clear();
                txtQTYOnHand1.clear();
                txtDiscount1.clear();
                initialize();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fill all Fields and Try Again..").show();
        }
    }

    public void searchItemOnAction1(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtSearchItemCode1.getText();
        Item item = new ItemDAOImpl().getItem(itemCode);
        if(item==null){
            new Alert(Alert.AlertType.WARNING, "Item Code is Not Exists...").show();
            txtDescription2.clear();
            txtPackSize2.clear();
            txtUnitPrice2.clear();
            txtQTYOnHand2.clear();
            txtDiscount2.clear();
            lblItemCode2.setText("");
            txtSearchItemCode1.clear();
        }else{
            btnUpdateItem.setDisable(false);
            btnDelete.setDisable(false);
            lblItemCode2.setText(item.getItemCode());
            txtDescription2.setText(item.getDescription());
            txtPackSize2.setText(item.getPackSize());
            txtUnitPrice2.setText(String.valueOf(item.getUnitPrice()));
            txtQTYOnHand2.setText(String.valueOf(item.getQtyOnHand()));
            txtDiscount2.setText(String.valueOf(item.getDiscount()));
        }
    }

    public void searchItemOnAction2(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = txtSearchItemCode2.getText();
        Item item = new ItemDAOImpl().getItem(itemCode);
        if(item==null){
            new Alert(Alert.AlertType.WARNING, "Item Code is Not Exists...").show();
            txtDescription3.clear();
            txtPackSize3.clear();
            txtUnitPrice3.clear();
            txtQTYOnHand3.clear();
            txtDiscount3.clear();
            lblItemCode3.setText("");
            txtSearchItemCode2.clear();
        }else{
            btnDelete.setDisable(false);
            lblItemCode3.setText(item.getItemCode());
            txtDescription3.setText(item.getDescription());
            txtPackSize3.setText(item.getPackSize());
            txtUnitPrice3.setText(String.valueOf(item.getUnitPrice()));
            txtQTYOnHand3.setText(String.valueOf(item.getQtyOnHand()));
            txtDiscount3.setText(String.valueOf(item.getDiscount()));
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!txtDescription2.getText().equals("") && !txtDiscount2.getText().equals("") && !txtPackSize2.getText().equals("") && !txtQTYOnHand2.getText().equals("") && !txtUnitPrice2.getText().equals("")) {
            Item item = new Item(lblItemCode2.getText(),txtDescription2.getText(),txtPackSize2.getText(),Double.parseDouble(txtUnitPrice2.getText()), Integer.parseInt(txtQTYOnHand2.getText()),Double.parseDouble(txtDiscount2.getText()));
            if (new ItemDAOImpl().update(item)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").showAndWait();
                txtDescription2.clear();
                txtPackSize2.clear();
                txtUnitPrice2.clear();
                txtQTYOnHand2.clear();
                txtDiscount2.clear();
                lblItemCode2.setText("");
                txtSearchItemCode1.clear();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fill all Fields and Try Again..").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (new ItemDAOImpl().delete(lblItemCode2.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            txtDescription2.clear();
            txtPackSize2.clear();
            txtUnitPrice2.clear();
            txtQTYOnHand2.clear();
            txtDiscount2.clear();
            lblItemCode2.setText("");
            txtSearchItemCode1.clear();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }


    public void minimizeOnAction2(MouseEvent mouseEvent) {
        stage = (Stage) adminDashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnAction1(MouseEvent mouseEvent) {
        stage = (Stage) adminDashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void goToRegisterForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminDashBoardContext.getChildren().clear();
        adminDashBoardContext.getChildren().add(load);
    }

    public void goToDiscount(ActionEvent actionEvent) {
        txtDiscount1.requestFocus();
    }

    public void goToUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice1.requestFocus();
    }

    public void goToQTYOnHand(ActionEvent actionEvent) {
        txtQTYOnHand1.requestFocus();
    }

    public void goToDescription(ActionEvent actionEvent) {
        txtDescription1.requestFocus();
    }

    public void goToAddBTN(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        addItemOnAction(actionEvent);
    }
}
