package application.controllers;

import application.sql.entitys.work.Client;
import application.sql.entitys.work.Payment;
import application.util.ControllerManager;
import application.util.tablemanagers.PaymentTableManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentsController implements Initializable {
    public CustomTextField searchTextField;
    public FontAwesomeIconView searchIcon;
    public Button newInComePaymentButton;
    public Button newOutComePaymentButton;
    public Label balanceLabel;
    public TableView<Payment> paymentsTable;
    public TableColumn<Payment, Integer> amountTableColumn;
    public TableColumn<Payment, String> commentTableColumn;
    public TableColumn<Payment, String> employeeTableColumn;
    public TableColumn<Payment, String> balanceTableColumn;
    public TableColumn<Payment, String> dateTableColumn;

    private SimpleStringProperty balance;
    private PaymentTableManager paymentTableManager;
    private FXMLLoader newPaymentloader;
    private FXMLLoader paymentInfoloader;
    private String newPaymentLocation = "../view/NewPayment.fxml";
    private String paymentInfoLocation = "../view/PaymentINFO.fxml";
    private AnchorPane newPaymentView;
    private NewPaymentController newPaymentController;
    private PaymentINFOController paymentInfoController;
    private Stage newPaymentStage;
    private Stage paymentInfoStage;
    private AnchorPane paymentInfoView;
    private Scene newPaymentScene;
    private Scene paymentInfoScene;

    public PaymentsController() {
        balance = new SimpleStringProperty();
        paymentTableManager = new PaymentTableManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBalance();
        setTableItems();
        setCellDataValues();
        setSearchField();

        initNewPaymentViewLoader();
        initPaymentInfoViewLoader();

        setSortTable();
    }

    private void setSearchField() {
        setChangeTextListenerToSearchField();
        setClearedButtonToSearchField(searchTextField);
    }

    private void setChangeTextListenerToSearchField() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> doSearch());
    }

    private Window getThisWindow() {
        return ControllerManager.getRootViewController().mainBorderPane.getScene().getWindow();
    }

    private void setBalance() {
        addBalanceChangeListener();
        setBalanceLabelProperty();
    }

    private void setCellDataValues() {
        amountTableColumn.setCellValueFactory(param -> param.getValue().amountProperty().asObject());
        commentTableColumn.setCellValueFactory(param -> param.getValue().commentProperty());
        employeeTableColumn.setCellValueFactory(param -> param.getValue().employeeProperty());
        dateTableColumn.setCellValueFactory(param -> param.getValue().dateTimeProperty());
        balanceTableColumn.setCellValueFactory(param -> param.getValue().finalBalanceProperty());

        paymentsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        setTableColumnsStyle();

        setCellColor(amountTableColumn);
    }

    private void setTableColumnsStyle() {
        dateTableColumn.setStyle("-fx-font-size: 14px;");
        amountTableColumn.setStyle("-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-alignment: CENTER;");
        commentTableColumn.setStyle("-fx-font-size: 12px;");
        employeeTableColumn.setStyle("-fx-font-size: 12px;");
        balanceTableColumn.setStyle("-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-alignment: CENTER-RIGHT;");
    }

    private void setCellColor(TableColumn<Payment, Integer> calltypel) {
        calltypel.setCellFactory(column -> {

            return new TableCell<Payment, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(!empty) {
                        setText(getItem().toString());
                        setGraphic(null);

                        TableRow<Payment> currentRow = getTableRow();
                        Payment currentPayment = currentRow.getItem();

                        if (!isEmpty() && currentPayment != null) {
                            if (currentPayment.getInOutValue().equals("-"))
                                this.setStyle(this.getStyle() + ";-fx-text-fill: red");
                            else
                                this.setStyle(this.getStyle() + ";-fx-text-fill: green");
                        }
                    } else {
                        setText(null);
                        setGraphic(null);
                    }
                }
            };
        });
    }

    private void setSortTable() {
        paymentsTable.getSortOrder().add(dateTableColumn);
        dateTableColumn.setSortType(TableColumn.SortType.DESCENDING);
        paymentsTable.sort();
    }

    private void setTableItems() {
        paymentsTable.setItems(paymentTableManager.getBackupObservableList());
    }

    private void addBalanceChangeListener() {
        paymentTableManager.getBackupObservableList().addListener((ListChangeListener<? super Payment>) c ->
                setBalanceProperty());
    }

    private void setBalanceProperty() {
        balanceProperty().set(String.format("%s грн", getCalculateBalance().toString()));
    }

    private BigDecimal getCalculateBalance() {
        BigDecimal tempBalance = new BigDecimal("0");
        for (Payment payment : paymentTableManager.getObservableList()) {
            tempBalance = tempBalance.add(payment.getBigDecimalAmount());
        }
        return tempBalance;
    }

    private void setBalanceLabelProperty() {
        balanceLabel.textProperty().bind(balanceProperty());
        setBalanceProperty();
    }

    public void onPressedKeySearchField(KeyEvent keyEvent) {
    }

    public void mouseClickedSearch(MouseEvent mouseEvent) {
        doSearch();
    }

    private void doSearch() {
        ObservableList<Payment> backupObservableList = paymentTableManager.getBackupObservableList();
        backupObservableList.clear();
        for(Payment payment : paymentTableManager.getObservableList()) {
            if (payment.getPaymentDateTime().toString().contains(searchTextField.getText())
                    || payment.getAmount().contains(searchTextField.getText())
                    || payment.getComment().toLowerCase().contains(searchTextField.getText().toLowerCase())) {
                backupObservableList.add(payment);
            }
        }
        paymentsTable.sort();
    }

    private void setClearedButtonToSearchField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void initNewPaymentViewLoader() {
        try {
            newPaymentloader = new FXMLLoader(getClass().getResource(newPaymentLocation));
            newPaymentView = newPaymentloader.load();
            newPaymentScene = new Scene(newPaymentView);
            newPaymentController = newPaymentloader.getController();
            ControllerManager.setNewPaymentController(newPaymentController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPaymentInfoViewLoader() {
        try {
            paymentInfoloader = new FXMLLoader(getClass().getResource(paymentInfoLocation));
            paymentInfoView = paymentInfoloader.load();
            paymentInfoScene = new Scene(paymentInfoView);
            paymentInfoController = paymentInfoloader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showNewPaymentView(boolean isInCome, Window owner) {
        if (newPaymentStage == null) {
            newPaymentStage = new Stage();
            setStage(owner, newPaymentStage, "Новый платеж", newPaymentScene);
        }


        setNewPaymentController(isInCome);

        getStage(owner).hide();
        newPaymentShowAndWait(owner);
        getStage(owner).show();
    }

    public void openNewPaymentWindowFromClientINFO(boolean isInCome, Window owner, Client client) {
        if (newPaymentStage == null) {
            newPaymentStage = new Stage();
            setStage(owner, newPaymentStage, "Новый платеж", newPaymentScene);
        }
        setNewPaymentController(isInCome);
        newPaymentController.setFromClientINFO(client);

        newPaymentShowAndWait(owner);
    }

    private void setNewPaymentController(boolean isInCome) {
        newPaymentController.setClientCBoxDisable(false);
        newPaymentController.clearInfoTextFields();
        newPaymentController.setInComePayment(isInCome);
        newPaymentController.setStartBalance(getCalculateBalance().toString());
        newPaymentController.setNewPaymentStage(newPaymentStage);
        newPaymentController.amountTextField.requestFocus();
        newPaymentController.setNewPaymentAdded(false);
    }

    private void newPaymentShowAndWait(Window owner) {
        newPaymentStage.showAndWait();
        paymentsTable.sort();
    }

    private Stage getStage(Window window) {
        Stage stage = (Stage) window;
        return stage;
    }

    private void showPaymentInfoView(Payment selectedItem) {
        if (paymentInfoStage == null) {
            paymentInfoStage = new Stage();
            setStage(getThisWindow(), paymentInfoStage, "Данные платежа", paymentInfoScene);
        }
        paymentInfoController.setPayment(selectedItem);
        paymentInfoStage.showAndWait();
    }

    private void setStage(Window owner, Stage stage, String title, Scene scene) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    public void newInComePaymentButtonOnAction(ActionEvent actionEvent) {
        showNewPaymentView(true, getThisWindow());
    }

    public void newOutComePaymentButtonOnAction(ActionEvent actionEvent) {
        showNewPaymentView(false, getThisWindow());
    }

    public void pressedEnter(KeyEvent keyEvent) {
    }

    public void onMouseClickedTable(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            showPaymentInfoView(paymentsTable.getSelectionModel().getSelectedItem());
        }
    }

    public String getBalance() {
        return balance.get();
    }

    public SimpleStringProperty balanceProperty() {
        return balance;
    }
}
