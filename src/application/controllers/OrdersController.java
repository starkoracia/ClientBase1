package application.controllers;

import application.sql.entitys.work.Order;
import application.util.ControllerManager;
import application.util.FocusRepeater;
import application.util.tablemanagers.OrderTableManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    public Button newOrderButton;
    public CustomTextField searchTextField;
    public FontAwesomeIconView searchIcon;
    public TableView<Order> ordersTable;
    public TableColumn<Order, String> orderNumberColumn;
    public TableColumn<Order, String> statusColumn;
    public TableColumn<Order, String> deadlineColumn;
    public TableColumn<Order, String> productTypeColumn;
    public TableColumn<Order, String> modelColumn;
    public TableColumn<Order, String> malfunctionColumn;
    public TableColumn<Order, String> clientColumn;
    public TableColumn<Order, String> amountColumn;
    public TableColumn<Order, String> doerNoteColumn;
    public AnchorPane pendingPaymentOrdersFilterAnchorPane;
    public AnchorPane quicklyOrdersFilterAnchorPane;
    public AnchorPane myOrdersFilterAnchorPane;

    private OrderTableManager orderTableManager;
    private FXMLLoader newOrderloader;
    private String newOrderViewLocation = "../view/NewOrder.fxml";
    private String orderInfoViewLocation = "../view/OrderINFO.fxml";
    private AnchorPane newOrderView;
    private NewOrderController newOrderController;
    private Stage newOrderStage;
    private FXMLLoader orderInfoloader;
    private OrderINFOController orderInfoController;
    private AnchorPane orderInfoView;
    private Stage orderInfoStage;
    private Order order;

    public OrdersController() {
        orderTableManager = new OrderTableManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableItems();
        setCellDataValues();

        initNewOrderViewLoader();
        initOrderInfoViewLoader();
        setSortTable();

        makeHotFilterPanelsInvisible();
    }

    private void setCellDataValues() {
        orderNumberColumn.setCellValueFactory(param -> param.getValue().orderNumberProperty());
        statusColumn.setCellValueFactory(param -> param.getValue().statusProperty());
        deadlineColumn.setCellValueFactory(param -> param.getValue().deadlineDifferenceProperty());
        productTypeColumn.setCellValueFactory(param -> param.getValue().productTypeProperty());
        modelColumn.setCellValueFactory(param -> param.getValue().modelProperty());
        malfunctionColumn.setCellValueFactory(param -> param.getValue().malfunctionProperty());
        clientColumn.setCellValueFactory(param -> param.getValue().clientProperty());
        amountColumn.setCellValueFactory(param -> param.getValue().amountProperty());
        doerNoteColumn.setCellValueFactory(param -> param.getValue().doerNoteProperty());
    }

    private void setTableItems() {
        ordersTable.setItems(orderTableManager.getBackupObservableList());
    }

    private void setSortTable() {
        ordersTable.getSortOrder().add(orderNumberColumn);
        orderNumberColumn.setSortType(TableColumn.SortType.DESCENDING);
        ordersTable.sort();
    }

    private void makeHotFilterPanelsInvisible() {
        ObservableList<Order> ordersQuickList = FXCollections.observableArrayList();
        boolean isHaveQuickOrders = false;
        for(Order order : orderTableManager.getObservableList()) {
            if(order.getQuickly()) {
                System.out.println("Quick");
                ordersQuickList.add(order);
                isHaveQuickOrders = true;
            }
        }
        if(isHaveQuickOrders) {
            quicklyOrdersFilterAnchorPane.setVisible(true);
        } else {
            quicklyOrdersFilterAnchorPane.setVisible(false);
        }
        pendingPaymentOrdersFilterAnchorPane.setVisible(false);

        myOrdersFilterAnchorPane.setVisible(false);
    }

    private void initNewOrderViewLoader() {
        try {
            newOrderloader = new FXMLLoader(getClass().getResource(newOrderViewLocation));
            newOrderView = newOrderloader.load();
            newOrderController = newOrderloader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initOrderInfoViewLoader() {
        try {
            orderInfoloader = new FXMLLoader(getClass().getResource(orderInfoViewLocation));
            orderInfoView = orderInfoloader.load();
            orderInfoController = orderInfoloader.getController();
            ControllerManager.setOrderINFOController(orderInfoController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOrderInfoView(Order order) {
        if (orderInfoStage == null) {
            orderInfoStage = new Stage();
            setStage(getThisWindow(), orderInfoStage, "Информация о заказе", orderInfoView);
        }
        setOrderInfoController(order);
        orderInfoShowAndWait();
    }

    private void setOrderInfoController(Order order) {
        orderInfoController.setOrderToOrderInfo(order);
        orderInfoController.setOrderInfoStage(orderInfoStage);
    }

    private void orderInfoShowAndWait() {
        orderInfoStage.showAndWait();
        ordersTable.sort();
    }

    public void showNewOrderView() {
        if (newOrderStage == null) {
            newOrderStage = new Stage();
            setStage(getThisWindow(), newOrderStage, "Новый заказ", newOrderView);
        }
        setNewOrderController();
        newOrderShowAndWait();
    }

    private void setStage(Window owner, Stage stage, String title, Pane view) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle(title);
        stage.setResizable(false);
        Scene scene = new Scene(view);
        stage.setScene(scene);
    }

    private void setNewOrderController() {
        newOrderController.setNewOrederStage(newOrderStage);
        newOrderController.clearInfoTextFields();
        FocusRepeater.repeat(newOrderController.clientComboBox.getEditor());
    }

    private void newOrderShowAndWait() {
        ControllerManager.getRootViewController().getRootViewStage().hide();
        newOrderStage.showAndWait();
        ordersTable.sort();
        ControllerManager.getRootViewController().getRootViewStage().show();
    }

    private Window getThisWindow() {
        return ControllerManager.getRootViewController().mainBorderPane.getScene().getWindow();
    }


    public void newOrderButtonOnAction(ActionEvent actionEvent) {
        showNewOrderView();
    }

    public void onPressedKeySearchField(KeyEvent keyEvent) {
    }

    public void mouseClickedSearch(MouseEvent mouseEvent) {
    }

    public void pressedEnterTable(KeyEvent keyEvent) {
    }

    public void onMouseClickedTable(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            showOrderInfoView(ordersTable.getSelectionModel().getSelectedItem());
        }
    }

    public void pendingPaymentOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void quicklyOrdersOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void myOrdersOnMouseClicked(MouseEvent mouseEvent) {
    }
}
