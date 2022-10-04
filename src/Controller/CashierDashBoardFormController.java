package Controller;

import bo.BoFactory;
import bo.custom.SaveOrderBO;
import dao.custom.impl.*;
import dto.Customer;
import dto.Item;
import dto.Order;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.tm.Carttm;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.ItemDetails;
import view.tm.ItemDetailtm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CashierDashBoardFormController {
    public AnchorPane cashierDashBoardContext;
    public TextField txtSearchCustomerID;
    public Label lblCustomerID;
    public TextField txtCustomerAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtPostalCode;
    public TextField txtCustomerTitle;
    public TextField txtNationalID;
    public TextField txtCustomerName;
    public Button btnUpdate;
    public Button btnAdd;
    public Label lblCashierName;
    public ComboBox<String> cmbItemDescription;
    public TableView tblItemDetail;
    public TableColumn colItemCode1;
    public TableColumn colDescription1;
    public TableColumn colPackSize1;
    public TableColumn colUnitPrice1;
    public TableColumn colQTYOnHand;
    public TextField txtQTYForSell;
    public TableView tblListOfItem;
    public TableColumn colItemCode2;
    public TableColumn colItemDescription;
    public TableColumn colOrderQuantity;
    public TableColumn colDiscount;
    public TableColumn colPrice;
    public Button btnAddToCart;
    public TextField txtUpdateOrderQTY;
    public Label lblGrossAmount;
    public Label lblTotalDiscount;
    public Label lblTotalNetAmount;
    public Label lblOrderID;
    public TextField txtDate;
    public TextField txtTime;
    private final SaveOrderBO saveOrderBO = (SaveOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.SAVE_ORDER);

    Stage stage = null;
    ObservableList<Carttm> obList = FXCollections.observableArrayList();
    int cartSelectedRow = -1;

    public void initialize() throws SQLException, ClassNotFoundException {
        btnAddToCart.setDisable(true);
        txtQTYForSell.setEditable(false);
        btnUpdate.setDisable(true);
        lblCustomerID.setText(saveOrderBO.setCustomerIDS());
        lblOrderID.setText(saveOrderBO.setOrderIDS());
        //lblCashierName.setText(firstNameOfCashier);
        //loadDateAndTime();
        List temp = saveOrderBO.getAllItemDescriptions();
        cmbItemDescription.getItems().addAll(temp);
        cmbItemDescription.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Item item = saveOrderBO.search(newValue);

                ItemDetailtm i = new ItemDetailtm(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
                ArrayList<ItemDetailtm> temp1 = new ArrayList<>();
                temp1.add(i);
                colItemCode1.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
                colDescription1.setCellValueFactory(new PropertyValueFactory<>("description"));
                colPackSize1.setCellValueFactory(new PropertyValueFactory<>("packSize"));
                colUnitPrice1.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                colQTYOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

                tblItemDetail.setItems(FXCollections.observableArrayList(temp1));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            txtQTYForSell.requestFocus();

        });

        tblListOfItem.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRow = (int) newValue;
        });

    }

    /*private void loadDateAndTime() {
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
    }*/

    public void closeTheProgramOnAction1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String nic = txtSearchCustomerID.getText();
        Customer c1= saveOrderBO.getCustomer(nic);
        if (c1==null) {
            refreshOnAction(actionEvent);
            new Alert(Alert.AlertType.WARNING, "No Customer for this ID...").show();
        } else {
            btnAddToCart.setDisable(false);
            txtQTYForSell.setEditable(true);
            btnUpdate.setDisable(false);
            btnAdd.setDisable(true);
            txtCustomerName.setText(c1.getCustomerName());
            txtCustomerAddress.setText(c1.getCustomerAddress());
            txtPostalCode.setText(c1.getPostalCode());
            txtNationalID.setText(c1.getNationalID());
            txtCity.setText(c1.getCity());
            txtProvince.setText(c1.getProvince());
            txtCustomerTitle.setText(c1.getCustomerTitle());
            lblCustomerID.setText(c1.getCustomerID());
        }
    }

    public void customerAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(!txtCustomerName.getText().equals("") && !txtCustomerAddress.getText().equals("") && !txtCustomerTitle.getText().equals("") && !lblCustomerID.getText().equals("") && !txtCity.getText().equals("") && !txtProvince.getText().equals("") && !txtPostalCode.getText().equals("") && !txtNationalID.getText().equals("")) {
            Customer c1 = new Customer(
                    lblCustomerID.getText(), txtCustomerTitle.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText(),
                    txtNationalID.getText()
            );
            if (saveOrderBO.add(c1)) {
                btnAdd.setDisable(true);
                new Alert(Alert.AlertType.CONFIRMATION, "Saved..").showAndWait();
                refreshOnAction(actionEvent);
            }
            else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please fill all fields and try again..").show();
        }
    }

    public void customerUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(!txtCustomerName.getText().equals("") && !txtCustomerAddress.getText().equals("") && !txtCustomerTitle.getText().equals("") && !lblCustomerID.getText().equals("") && !txtCity.getText().equals("") && !txtProvince.getText().equals("") && !txtPostalCode.getText().equals("") && !txtNationalID.getText().equals("")) {
            Customer c1 = new Customer(
                    lblCustomerID.getText(), txtCustomerTitle.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText(),
                    txtNationalID.getText()
            );
            if (saveOrderBO.update(c1)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated..").showAndWait();
                refreshOnAction(actionEvent);
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please fill all fields and try again..").show();
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void goToBTNAddToCart(ActionEvent actionEvent) {
        addToCartOnAction(actionEvent);
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        if (!txtQTYForSell.getText().equals("") ) {
            try {
                Item item = saveOrderBO.search(cmbItemDescription.getSelectionModel().getSelectedItem());
                if (obList.isEmpty()) {
                    double totDiscount = Integer.parseInt(item.getPackSize()) * (item.getDiscount()) * Integer.parseInt(txtQTYForSell.getText());
                    double price = item.getUnitPrice() * Integer.parseInt(item.getPackSize()) * (Integer.parseInt(txtQTYForSell.getText()));

                    Carttm carttm = new Carttm(item.getItemCode(), item.getDescription(), Integer.parseInt(txtQTYForSell.getText()), totDiscount, price);

                    if (item.getQtyOnHand() < carttm.getOrderQuantity()) {
                        new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                        txtQTYForSell.clear();
                    } else {
                        obList.add(carttm);
                        calculateCost();
                        txtQTYForSell.clear();
                    }

                    colItemCode2.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
                    colItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
                    colOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
                    colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
                    colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

                    tblListOfItem.setItems(FXCollections.observableArrayList(obList));
                    tblItemDetail.refresh();

                } else {
                    try {
                        for (Carttm c : obList) {
                            if (c.getItemCode().equals(item.getItemCode())) {
                                double totDiscount = Integer.parseInt(item.getPackSize()) * (item.getDiscount()) * Integer.parseInt(txtQTYForSell.getText());
                                double price = item.getUnitPrice() * Integer.parseInt(item.getPackSize()) * (Integer.parseInt(txtQTYForSell.getText()));

                                if (item.getQtyOnHand() < c.getOrderQuantity() +(Integer.parseInt(txtQTYForSell.getText()))) {
                                    new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                                    txtQTYForSell.clear();
                                } else {
                                    c.setOrderQuantity(c.getOrderQuantity() + Integer.parseInt(txtQTYForSell.getText()));
                                    c.setDiscount(c.getDiscount() + totDiscount);
                                    c.setPrice(c.getPrice() + price);
                                    calculateCost();
                                    tblListOfItem.refresh();
                                    txtQTYForSell.clear();
                                }
                                return;
                            }
                        }
                        double totDiscount = Integer.parseInt(item.getPackSize()) * (item.getDiscount()) * Integer.parseInt(txtQTYForSell.getText());
                        double price = item.getUnitPrice() * Integer.parseInt(item.getPackSize()) * (Integer.parseInt(txtQTYForSell.getText()));

                        Carttm carttm = new Carttm(item.getItemCode(), item.getDescription(), Integer.parseInt(txtQTYForSell.getText()), totDiscount, price);
                        if (item.getQtyOnHand() < carttm.getOrderQuantity()) {
                            new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                            txtQTYForSell.clear();
                        } else {
                            calculateCost();
                            obList.add(carttm);
                            txtQTYForSell.clear();
                        }
                        colItemCode2.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
                        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
                        colOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
                        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
                        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

                        tblListOfItem.setItems(FXCollections.observableArrayList(obList));

                    } catch (Exception e) {
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            calculateCost();
        }else{
            new Alert(Alert.AlertType.WARNING, "Please enter order Quantity and try again..!").show();
        }
    }

    public void deleteItemQTYOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cartSelectedRow==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRow);
            calculateCost();

            colItemCode2.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
            colOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
            colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            tblListOfItem.setItems(FXCollections.observableArrayList(obList));
        }
    }

    public void UpdateItemQTYOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cartSelectedRow==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            Carttm c = obList.get(cartSelectedRow);

            Item i = saveOrderBO.getItem(c.getItemCode());
            if(Integer.parseInt(txtUpdateOrderQTY.getText()) > i.getQtyOnHand()){
                new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                txtUpdateOrderQTY.clear();
            }else{
                c.setOrderQuantity(Integer.parseInt(txtUpdateOrderQTY.getText()));
                double tot = (Integer.parseInt(txtUpdateOrderQTY.getText()))* i.getUnitPrice() * Integer.parseInt(i.getPackSize());
                c.setPrice(tot);
                double discount = (Integer.parseInt(txtUpdateOrderQTY.getText()))* i.getDiscount() * Integer.parseInt(i.getPackSize());
                c.setDiscount(discount);
                obList.remove(cartSelectedRow);
                obList.add(cartSelectedRow,c);
                calculateCost();
                tblListOfItem.refresh();
                txtUpdateOrderQTY.clear();
            }
        }
    }
    void calculateCost(){
        double ttl=0;
        double dis=0.0;
        double netTot = 0.0;
        for (Carttm tm:obList
        ) {
            ttl+=tm.getPrice();
            dis+=tm.getDiscount();
            netTot+=ttl-dis;
        }
        lblGrossAmount.setText(ttl+" /=");
        lblTotalDiscount.setText(dis+" /=");
        lblTotalNetAmount.setText(netTot+" /=");
    }

    public void goToSavedFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderSavedForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) throws IOException {
        refreshOnAction(actionEvent);
    }

    public void saveOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<ItemDetails> items = new ArrayList<>();
        for (Carttm c : obList) {
            items.add(new ItemDetails(
                    c.getItemCode(),
                    c.getItemDescription(),
                    c.getOrderQuantity(),
                    c.getDiscount(),
                    c.getPrice()
            ));
        }
        Order order = new Order(lblOrderID.getText(),txtNationalID.getText(),items);
        if(new SavedOrderDAOImpl().saveOrder(order.getOrderId(),order.getCustNIC(),order.getItems())){
            saveOrderBO.saveIDTOTempTable(lblOrderID.getText());
            new Alert(Alert.AlertType.CONFIRMATION, "Order Saved Successfully...!").show();
            refreshOnAction(actionEvent);
        }else{
            new Alert(Alert.AlertType.WARNING, "Try again!").show();
        }
    }

    public void minimizeOnAction1(MouseEvent mouseEvent) {
        stage = (Stage) cashierDashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnAction2(MouseEvent mouseEvent) {
        stage = (Stage) cashierDashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }
}
