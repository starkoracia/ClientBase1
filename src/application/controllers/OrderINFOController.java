package application.controllers;

import application.sql.daos.EmployeeDAO;
import application.sql.daos.OrderStatusDAO;
import application.sql.entitys.work.Client;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Order;
import application.sql.entitys.work.OrderStatus;
import application.util.AlertCaster;
import application.util.ControllerManager;
import application.util.combobox.ComboBoxAutoCompletioner;
import application.util.comparators.ClientComparator;
import application.util.converters.IsPaidItems;
import application.util.converters.StringConverterFactory;
import application.util.tablemanagers.ClientTableManager;
import application.util.tablemanagers.OrderTableManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class OrderINFOController implements Initializable {


    public AnchorPane mainAnchorPane;
    public ScrollPane scrollPane;
    public Label headOrderNumberLabel;
    public ChoiceBox<OrderStatus> headOrderStatusChoiceBox;
    public Label headOrderDeadlineLabel;
    public ChoiceBox<Boolean> isPaidChoiceBox;
    public ComboBox<Client> clientComboBox;
    public Button newClientButton;
    public TextField productTypeTextField;
    public TextField brandTextField;
    public TextField modelTextField;
    public TextArea malfunctionTextArea;
    public TextField appearanceTextField;
    public TextArea equipmentTextArea;
    public TextArea acceptorNoteTextArea;
    public TextField estimatedPriceTextField;
    public CheckBox quicklyCheckBox;
    public DatePicker deadlineDatePicker;
    public TextField prepaymentTextField;
    public ComboBox<Employee> managerComboBox;
    public ComboBox<Employee> doerComboBox;
    public Button deleteButton;
    public Button saveButton;
    public Button resetClientButton;
    public Label clientNameLable;

    private OrderStatusDAO orderStatusDAO;
    private ClientTableManager clientTableManager;
    private OrderTableManager orderTableManager;
    private EmployeeDAO employeeDAO;
    private Client client;
    private Order order;
    private Stage orderInfoStage;

    public OrderINFOController() {
        orderStatusDAO = new OrderStatusDAO();
        employeeDAO = new EmployeeDAO();
        clientTableManager = new ClientTableManager();
        orderTableManager = new OrderTableManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBoxes();
        setComboBoxes();

        clientNameLable.textProperty().bind(clientComboBox.getEditor().textProperty());
    }

    private void setComboBoxes() {
        setClientComboBoxAutoDisable();
        setComboBoxesItems();
        setComboBoxesConvertors();
        setClientComboBoxAutoCompletion();
    }

    private void setClientComboBoxAutoDisable() {
        clientComboBox.selectionModelProperty().get().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                clientComboBox.setDisable(true);
            }
            ;
        });
    }

    private void setClientComboBoxAutoCompletion() {
        ComboBoxAutoCompletioner.autoCompleteComboBoxPlus(clientComboBox, ClientComparator.getComparator());
    }

    private void setComboBoxesItems() {
        clientComboBox.setItems(clientTableManager.getObservableList());
        managerComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
        doerComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
    }

    private void setComboBoxesConvertors() {
        clientComboBox.setConverter(StringConverterFactory.getClientStringConverter());
        managerComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
        doerComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
    }

    private void setChoiceBoxes() {
        setChoiceBoxesItems();
        setChoiceBoxesConvertors();
    }

    private void setChoiceBoxesConvertors() {
        headOrderStatusChoiceBox.setConverter(StringConverterFactory.getOrderStatusConverter());
        isPaidChoiceBox.setConverter(new IsPaidItems());
    }

    private void setChoiceBoxesItems() {
        headOrderStatusChoiceBox.setItems(FXCollections.observableArrayList(orderStatusDAO.getAll()));
        isPaidChoiceBox.setItems(IsPaidItems.getItems());
    }

    public void setOrderToOrderInfo(Order order) {
        this.order = order;
        headOrderNumberLabel.setText(String.format("Заказ№ %s", order.getOrderNumber()));
        headOrderStatusChoiceBox.setValue(order.getStatus());
        headOrderDeadlineLabel.setText(String.format("%sд.", order.deadlineDifferenceProperty().getValue()));
        isPaidChoiceBox.setValue(order.getPaid());
        clientComboBox.setValue(order.getClient());
        productTypeTextField.setText(order.getProductType());
        brandTextField.setText(order.getBrandName());
        modelTextField.setText(order.getModel());
        malfunctionTextArea.setText(order.getMalfunction());
        appearanceTextField.setText(order.getAppearance());
        equipmentTextArea.setText(order.getEquipment());
        acceptorNoteTextArea.setText(order.getAcceptorNote());
        estimatedPriceTextField.setText(order.getEstimatedPrice());
        quicklyCheckBox.setSelected(order.getQuickly());
        deadlineDatePicker.setValue(order.getDeadline().toLocalDate());
        prepaymentTextField.setText(order.getPrepayment());
        managerComboBox.setValue(order.getManager());
        doerComboBox.setValue(order.getDoer());
    }

    public void onActionNewClientButton(ActionEvent actionEvent) {
        openNewClientWindow();
    }

    private void openNewClientWindow() {
        ControllerManager.getClientsController()
                .openClientInfoWindowFromNewPayment(true, getThisWindow());
        ClientINFOController clientINFOController = ControllerManager.getClientINFOController();
        if (clientINFOController.isNewClientAdded()) {
            client = clientINFOController.getClient();
            clientComboBox.setValue(client);
        }
    }

    public void malfunctionOnKPressed(KeyEvent keyEvent) {
    }

    public void malfunctionOnKReleased(KeyEvent keyEvent) {
    }

    public void equipmentTAreaOnKPressed(KeyEvent keyEvent) {
    }

    public void equipmentTAreaOnKReleased(KeyEvent keyEvent) {
    }

    public void acceptorTAreaOnKPressed(KeyEvent keyEvent) {
    }

    public void acceptorTAreaOnKReleased(KeyEvent keyEvent) {
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (isAllFieldsAreFilled()) {
            updateOrder();
            orderInfoStage.hide();
        } else {
            AlertCaster.castInfoAlert("Заполните все поля со звездочкой *");
        }
    }

    private void updateOrder() {
        order.setStatus(headOrderStatusChoiceBox.getValue());
        order.setPaid(isPaidChoiceBox.getValue());
        order.setClient(clientComboBox.getValue());
        order.setProductType(productTypeTextField.getText());
        order.setBrandName(brandTextField.getText());
        order.setModel(modelTextField.getText());
        order.setMalfunction(malfunctionTextArea.getText());
        order.setAppearance(appearanceTextField.getText());
        order.setEquipment(equipmentTextArea.getText());
        order.setAcceptorNote(acceptorNoteTextArea.getText());
        order.setEstimatedPrice(estimatedPriceTextField.getText());
        order.setQuickly(quicklyCheckBox.isSelected());
        order.setDeadline(LocalDateTime.of(deadlineDatePicker.getValue(), LocalTime.now()));
        order.setPrepayment(prepaymentTextField.getText());
        order.setManager(managerComboBox.getValue());
        order.setDoer(doerComboBox.getValue());
        orderTableManager.update(order);
    }

    private boolean isAllFieldsAreFilled() {
        if (clientComboBox.getSelectionModel().getSelectedItem() != null
                && deadlineDatePicker.getValue() != null) {
            return true;
        } else {
            return false;
        }
    }

    private Window getThisWindow() {
        return mainAnchorPane.getScene().getWindow();
    }

    public void resetClientButtonOnAction(ActionEvent actionEvent) {
        clientComboBox.setDisable(false);
        clientComboBox.setValue(null);
    }

    public void setOrderInfoStage(Stage orderInfoStage) {
        this.orderInfoStage = orderInfoStage;
    }
}
