package application.controllers;

import application.sql.entitys.work.Order;
import application.util.tablemanagers.OrderTableManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
    private AnchorPane newOrderView;
    private NewOrderController newOrderController;
    private Stage newOrderStage;

    public OrdersController() {
        orderTableManager = new OrderTableManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeHotFilterPanelsInvisible();
        setTableItems();
        setCellDataValues();

        initNewOrderViewLoader();

    }

    private void setCellDataValues() {
        orderNumberColumn.setCellValueFactory(param -> param.getValue().orderNumberProperty());
        statusColumn.setCellValueFactory(param -> param.getValue().statusProperty());
        deadlineColumn.setCellValueFactory(param -> param.getValue().deadlineProperty());
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

    private void makeHotFilterPanelsInvisible() {
        pendingPaymentOrdersFilterAnchorPane.setVisible(false);
        quicklyOrdersFilterAnchorPane.setVisible(false);
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

    public void showNewOrderView() {
        if(newOrderStage == null) {
            newOrderStage = new Stage();
            setStage(newOrderStage,"Новый заказ", newOrderView);
        }

        newOrderStage.showAndWait();
    }

    private void setStage(Stage stage, String title, Pane view) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(ordersTable.getScene().getWindow());
        stage.setTitle(title);
        stage.setResizable(false);
        Scene scene = new Scene(view);
        stage.setScene(scene);
    }

    public void newOrderButtonOnAction(ActionEvent actionEvent) {
        showNewOrderView();
    }

    public void onPressedKeySearchField(KeyEvent keyEvent) {
    }

    public void mouseClickedSearch(MouseEvent mouseEvent) {
    }

    public void pressedEnter(KeyEvent keyEvent) {
    }

    public void onMouseClickedTable(MouseEvent mouseEvent) {
    }

    public void pendingPaymentOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void quicklyOrdersOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void myOrdersOnMouseClicked(MouseEvent mouseEvent) {
    }
}
