package Controller;

import bo.BoFactory;
import bo.custom.PurchaseOrderBO;
import dao.custom.impl.*;
import dto.Customer;
import dto.Item;
import dto.SavedOrder;
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
import view.tm.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderSavedFormController {
    public AnchorPane savedFormContext;
    public TableView<OrderIDStm> tblOIDAndNIC;
    public TableColumn colOrderID;
    public TableColumn colNIC;
    public ComboBox<String> cmbItemDescription;
    public TextField txtItemCode;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQTYOnHand;
    public ComboBox<String> cmbOrderID;
    public Label lblCustomerID;
    public TableView tblItemDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblNIC;
    public Label lblGrossAmount;
    public Label lblDiscount;
    public Label lblNetAmount;
    public TextField txtQTYForSell;
    public TextField txtDate;
    public TextField txtTime;
    public TextField txtCash;
    public Button btnAddToCart;
    public Button btnDelete;
    public Button btnUpdate;
    public Label lblBalance;
    public TextField txtUpdateQTY;
    public Button btnPlaceOrder;
    String nic=null;
    int cartSelectedRow = -1;
    String dateAndTime = null;
    Stage stage = null;
    private final PurchaseOrderBO saveOrderBO = (PurchaseOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.SAVE_ORDER);

    ObservableList<SavedOrderDetailsTM> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        txtQTYOnHand.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtPackSize.setEditable(false);
        txtItemCode.setEditable(false);
        btnPlaceOrder.setDisable(true);
        loadDateAndTime();

        tblItemDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRow = (int) newValue;
        });

        List temp = new ItemDAOImpl().getAllItemDescriptions();
        cmbItemDescription.getItems().addAll(temp);

        setOrderIdAndNicToTable(saveOrderBO.getAllOrderIDSWithNIC());

        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));

        cmbItemDescription.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Item item = saveOrderBO.search(newValue);
                ItemDetailtm i = new ItemDetailtm(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
                txtItemCode.setText(i.getItemCode());
                txtPackSize.setText(i.getPackSize());
                txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
                txtQTYOnHand.setText(String.valueOf(i.getQtyOnHand()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                obList.clear();
                setItemsToTable(newValue);
                nic = saveOrderBO.getNIC(newValue);
                Customer cust = saveOrderBO.getCustomer(nic);
                lblCustomerID.setText(cust.getCustomerID());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
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

    private void setItemsToTable(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<SavedOrderDetailsTM> s = saveOrderBO.getOrderDetails(newValue,lblNIC);
        s.forEach(e->{
            obList.add(new SavedOrderDetailsTM(e.getItemCode(),e.getDescription(),e.getQtyForSell(),e.getDiscount(),e.getTotal()));
        });
        tblItemDetails.setItems(obList);
        calculate(obList);
        setDataToTable();
    }

    private void calculate(ObservableList<SavedOrderDetailsTM> obList){
        double discount =0.0 ;
        double total =0.0 ;
        double netTotal =0.0 ;
        for (SavedOrderDetailsTM s : obList){
            discount+=s.getDiscount();
            total+=s.getTotal();
        }
        netTotal=total-discount;
        lblGrossAmount.setText(String.valueOf(total));
        lblDiscount.setText(String.valueOf(discount));
        lblNetAmount.setText(String.valueOf(netTotal));
    }

    public void setDataToTable(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyForSell"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void closeTheProgramOnAction1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgramOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void backOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        savedFormContext.getChildren().clear();
        savedFormContext.getChildren().add(load);
    }
    private void setOrderIdAndNicToTable(ArrayList<SavedOrder> orderIdAndNic) {
        ObservableList<OrderIDStm> savedOIDAndNic= FXCollections.observableArrayList();
        orderIdAndNic.forEach(e->{
            OrderIDStm savedOidAndNicDetailsTM = new OrderIDStm(e.getoId(),e.getNIC());
            int i=0;
            do {
                if(savedOIDAndNic.size()==0){
                    savedOIDAndNic.add(savedOidAndNicDetailsTM);
                    break;
                }else if(isExists(savedOidAndNicDetailsTM,savedOIDAndNic)){

                }else{
                    savedOIDAndNic.add(savedOidAndNicDetailsTM);
                }
                i++;
            }while(i<savedOIDAndNic.size());
        });

        for (int i = 0; i < savedOIDAndNic.size(); i++) {
            cmbOrderID.getItems().add(savedOIDAndNic.get(i).getOrderID());
        }
        tblOIDAndNIC.setItems(savedOIDAndNic);
    }
    private boolean isExists(OrderIDStm savedOidAndNicDetailsTM,ObservableList<OrderIDStm> savedOIDAndNic){
        for (int i = 0; i <savedOIDAndNic.size() ; i++) {
            if(savedOidAndNicDetailsTM.getOrderID().equals(savedOIDAndNic.get(i).getOrderID())){
                return true;
            }
        }
        return false;
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
       if (!txtQTYForSell.getText().equals("") ) {
            try {

                Item item = saveOrderBO.search(cmbItemDescription.getSelectionModel().getSelectedItem());

                if (obList.isEmpty()) {
                    double totDiscount = Integer.parseInt(item.getPackSize()) * (item.getDiscount()) * Integer.parseInt(txtQTYForSell.getText());
                    double price = item.getUnitPrice() * Integer.parseInt(item.getPackSize()) * (Integer.parseInt(txtQTYForSell.getText()));

                    SavedOrderDetailsTM carttm = new SavedOrderDetailsTM(item.getItemCode(), item.getDescription(), Integer.parseInt(txtQTYForSell.getText()), totDiscount, price);

                    if (item.getQtyOnHand() < carttm.getQtyForSell()) {
                        new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                        txtQTYForSell.clear();
                    } else {
                        obList.add(carttm);
                        calculate(obList);
                        txtQTYForSell.clear();
                    }
                    setDataToTable();
                    tblItemDetails.setItems(obList);
                    tblItemDetails.refresh();

                } else {
                    try {
                        for (SavedOrderDetailsTM c : obList) {
                            if (c.getItemCode().equals(item.getItemCode())) {
                                double totDiscount = Integer.parseInt(item.getPackSize()) * (item.getDiscount()) * Integer.parseInt(txtQTYForSell.getText());
                                double price = item.getUnitPrice() * Integer.parseInt(item.getPackSize()) * (Integer.parseInt(txtQTYForSell.getText()));

                                if (item.getQtyOnHand() < c.getQtyForSell() +(Integer.parseInt(txtQTYForSell.getText()))) {
                                    new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                                    txtQTYForSell.clear();
                                } else {
                                    int q =c.getQtyForSell() + Integer.parseInt(txtQTYForSell.getText());
                                    double d = c.getDiscount() + totDiscount;
                                    double t = c.getTotal() + price;
                                    c.setQtyForSell(c.getQtyForSell() + Integer.parseInt(txtQTYForSell.getText()));
                                    c.setDiscount(c.getDiscount() + totDiscount);
                                    c.setTotal(c.getTotal() + price);

                                    setDataToTable();
                                    calculate(obList);
                                    tblItemDetails.refresh();
                                    txtQTYForSell.clear();
                                }
                                return;

                            }
                        }
                        double totDiscount = Integer.parseInt(item.getPackSize()) * (item.getDiscount()) * Integer.parseInt(txtQTYForSell.getText());
                        double price = item.getUnitPrice() * Integer.parseInt(item.getPackSize()) * (Integer.parseInt(txtQTYForSell.getText()));
                        SavedOrderDetailsTM carttm = new SavedOrderDetailsTM(item.getItemCode(), item.getDescription(), Integer.parseInt(txtQTYForSell.getText()), totDiscount, price);

                        if (item.getQtyOnHand() < carttm.getQtyForSell()) {
                            new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                            txtQTYForSell.clear();
                        } else {
                            calculate(obList);
                            obList.add(carttm);
                            txtQTYForSell.clear();
                        }
                        setDataToTable();
                        tblItemDetails.setItems(obList);

                    } catch (Exception e) {
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            calculate(obList);
        }else{
            new Alert(Alert.AlertType.WARNING, "Please enter order Quantity and try again..!").show();
        }
    }

    public void getBalance(ActionEvent actionEvent) {
        txtQTYForSell.setEditable(false);
        btnAddToCart.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        double net = Double.parseDouble(txtCash.getText())-Double.parseDouble(lblNetAmount.getText());
        lblBalance.setText(String.valueOf(net)+" /=");
        btnPlaceOrder.setDisable(false);
    }

    public void goToAddToCartOnAction(ActionEvent actionEvent) {
        addToCartOnAction(actionEvent);
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        if (cartSelectedRow==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Do you want to delete Order Item..").showAndWait();
            obList.remove(cartSelectedRow);
            calculate(obList);
            setDataToTable();
            tblItemDetails.setItems(FXCollections.observableArrayList(obList));
        }
    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cartSelectedRow==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            SavedOrderDetailsTM c = obList.get(cartSelectedRow);
            Item i = saveOrderBO.getItem(c.getItemCode());
            if(Integer.parseInt(txtUpdateQTY.getText()) > i.getQtyOnHand()){
                new Alert(Alert.AlertType.WARNING, "Order quantity is out of stock...!").show();
                txtUpdateQTY.clear();
            }else{
                c.setQtyForSell(Integer.parseInt(txtUpdateQTY.getText()));
                double tot = (Integer.parseInt(txtUpdateQTY.getText()))* i.getUnitPrice() * Integer.parseInt(i.getPackSize());
                c.setTotal(tot);
                double discount = (Integer.parseInt(txtUpdateQTY.getText()))* i.getDiscount() * Integer.parseInt(i.getPackSize());
                c.setDiscount(discount);
                obList.add(cartSelectedRow,c);
                obList.remove(cartSelectedRow);
                calculate(obList);
                tblItemDetails.refresh();
                txtUpdateQTY.clear();
            }
        }
    }

    public void cancelOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if(saveOrderBO.deleteOrderFromSavedOrderTable(cmbOrderID.getSelectionModel().getSelectedItem())){
            new Alert(Alert.AlertType.WARNING, "Do you Want to Cancel Order...!").showAndWait();
            URL resource = getClass().getResource("../view/OrderSavedForm.fxml");
            Parent load = FXMLLoader.load(resource);
            savedFormContext.getChildren().clear();
            savedFormContext.getChildren().add(load);
        }else{}
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        OrderDBtm d = new OrderDBtm(cmbOrderID.getSelectionModel().getSelectedItem(),txtDate.getText(),txtTime.getText(),lblCustomerID.getText(),Double.parseDouble(lblDiscount.getText()),Double.parseDouble(lblGrossAmount.getText()));

        if(saveOrderBO.saveOrderToDBTable(d)){}
            saveOrderBO.saveOrderToOrderDetailTable(cmbOrderID.getSelectionModel().getSelectedItem(),obList);
            saveOrderBO.updateItemTable(obList);
        if(saveOrderBO.deleteOrderFromSavedOrderTable(cmbOrderID.getSelectionModel().getSelectedItem())){
            URL resource = getClass().getResource("../view/OrderSavedForm.fxml");
            Parent load = FXMLLoader.load(resource);
            savedFormContext.getChildren().clear();
            savedFormContext.getChildren().add(load);
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Order Placed Successfully..!").show();
    }

    public void minimizeOnAction2(MouseEvent mouseEvent) {
        stage = (Stage)savedFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeOnAction1(MouseEvent mouseEvent) {
        stage = (Stage)savedFormContext.getScene().getWindow();
        stage.setIconified(true);
    }
}
