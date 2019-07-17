package application.controllers;

import application.sql.daos.ClientDAO;
import application.sql.daos.EmployeeDAO;
import application.sql.daos.OrderStatusDAO;
import application.sql.entitys.work.Client;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Order;
import application.util.AlertCaster;
import application.util.ControllerManager;
import application.util.combobox.ComboBoxAutoCompletioner;
import application.util.comparators.ClientComparator;
import application.util.converters.StringConverterFactory;
import application.util.tablemanagers.OrderTableManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class NewOrderController implements Initializable {

    public Button deleteButton;
    public Button saveButton;
    public ChoiceBox<String> isPaidChoiceBox;
    public ComboBox<Client> clientComboBox;
    public Button newClientButton;
    public TextField productTypeTextField;
    public TextField brandTextField;
    public TextField modelTextField;
    public TextArea malfunctionTextArea;
    public TextField appearanceTextField;
    public TextArea equipmentTextArea;
    public TextArea acceptorTextArea;
    public TextField estimatedPriceTextField;
    public CheckBox quicklyCheckBox;
    public DatePicker deadlineDatePicker;
    public TextField prepaymentTextField;
    public ComboBox<Employee> managerComboBox;
    public ComboBox<Employee> doerComboBox;
    public AnchorPane mainAnchorPane;

    private ObservableList<String> isPaidList;
    private ClientDAO clientDAO;
    private EmployeeDAO employeeDAO;
    private Client client;
    private OrderTableManager orderTableManager;
    private Order order;
    private OrderStatusDAO orderStatusDAO;
    private Stage newOrederStage;
    private boolean malfunctionAreaPressTab;
    private boolean equipmentAreaPressTab;
    private boolean acceptorAreaPressTab;

    public NewOrderController() {
        clientDAO = new ClientDAO();
        employeeDAO = new EmployeeDAO();
        orderStatusDAO = new OrderStatusDAO();
        orderTableManager = new OrderTableManager();
        malfunctionAreaPressTab = false;
        equipmentAreaPressTab = false;
        acceptorAreaPressTab = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPaidChoiceBox();

        setComboBoxes();
    }

    private void setComboBoxes() {
        setComboBoxItems();
        setComboBoxConvertors();
        setClientComboBoxAutoCompletion();
    }

    private void setClientComboBoxAutoCompletion() {
        ComboBoxAutoCompletioner.autoCompleteComboBoxPlus(clientComboBox, ClientComparator.getComparator());
    }

    private void setComboBoxItems() {
        clientComboBox.setItems(FXCollections.observableArrayList(clientDAO.getAll()));
        managerComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
        doerComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
    }

    private void setComboBoxConvertors() {
        clientComboBox.setConverter(StringConverterFactory.getClientStringConverter());
        managerComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
        doerComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
    }

    private void setPaidChoiceBox() {
        isPaidList = FXCollections.observableArrayList("Платный", "По гарантии");
        isPaidChoiceBox.setItems(isPaidList);
    }

    public void clearInfoTextFields() {
        isPaidChoiceBox.setValue("Платный");
        clientComboBox.setValue(null);
        productTypeTextField.setText("");
        brandTextField.setText("");
        modelTextField.setText("");
        malfunctionTextArea.setText("");
        appearanceTextField.setText("");
        equipmentTextArea.setText("");
        acceptorTextArea.setText("");
        estimatedPriceTextField.setText("0");
        quicklyCheckBox.setSelected(false);
        deadlineDatePicker.setValue(LocalDate.now().plusDays(4));
        prepaymentTextField.setText("0");
        managerComboBox.setValue(managerComboBox.getItems().get(0));
        doerComboBox.setValue(null);
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        newOrederStage.hide();
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (isAllFieldsAreFilled()) {
            addNewOrder();
            newOrederStage.hide();
        } else {
            AlertCaster.castInfoAlert("Заполните все поля со звездочкой *");
        }
    }

    private void addNewOrder() {
        orderTableManager.add(createOrderFromFields());
    }

    private Order createOrderFromFields() {
        order = new Order(
                orderStatusDAO.getByKey(1),
                getIsPaidBool(),
                clientComboBox.getValue(),
                LocalDateTime.of(deadlineDatePicker.getValue(), LocalTime.now()));
        order.setProductType(productTypeTextField.getText());
        order.setBrandName(brandTextField.getText());
        order.setModel(modelTextField.getText());
        order.setMalfunction(malfunctionTextArea.getText());
        order.setAppearance(appearanceTextField.getText());
        order.setEquipment(equipmentTextArea.getText());
        order.setAcceptorNote(acceptorTextArea.getText());
        order.setEstimatedPrice(estimatedPriceTextField.getText());
        order.setQuickly(quicklyCheckBox.isSelected());
        order.setPrepayment(prepaymentTextField.getText());
        order.setManager(managerComboBox.getValue());
        order.setDoer(doerComboBox.getValue());

        return order;
    }

    private boolean isAllFieldsAreFilled() {
        if (clientComboBox.getSelectionModel().getSelectedItem() != null
                && deadlineDatePicker.getValue() != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getIsPaidBool() {
        if (isPaidChoiceBox.getSelectionModel().getSelectedItem().equals("Платный")) {
            return true;
        } else {
            return false;
        }
    }

    private Window getThisWindow() {
        return mainAnchorPane.getScene().getWindow();
    }

    public void onActionNewClientButton(ActionEvent actionEvent) {
        openNewClientWindow();
    }

    private void openNewClientWindow() {
        ControllerManager.getClientsController().openClientInfoWindowFromNewPayment(true, getThisWindow());

        ClientINFOController clientINFOController = ControllerManager.getClientINFOController();

        if (clientINFOController.isNewClientAdded()) {
            client = clientINFOController.getClient();
            clientComboBox.getItems().add(client);
            clientComboBox.setValue(client);
            productTypeTextField.requestFocus();
        }
    }

    public Stage getNewOrederStage() {
        return newOrederStage;
    }

    public void setNewOrederStage(Stage newOrederStage) {
        this.newOrederStage = newOrederStage;
    }

    public void malfunctionOnKPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            malfunctionAreaPressTab = true;
        }
    }

    public void malfunctionOnKReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB && malfunctionAreaPressTab) {
            malfunctionTextArea.deleteText(
                    malfunctionTextArea.getCaretPosition() - 1, malfunctionTextArea.getCaretPosition());
            appearanceTextField.requestFocus();
            malfunctionAreaPressTab = false;
        }
    }

    public void equipmentTAreaOnKPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            equipmentAreaPressTab = true;
        }
    }

    public void equipmentTAreaOnKReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB && equipmentAreaPressTab) {
            equipmentTextArea.deleteText(
                    equipmentTextArea.getCaretPosition() - 1, equipmentTextArea.getCaretPosition());
            acceptorTextArea.requestFocus();
            equipmentAreaPressTab = false;
        }
    }

    public void acceptorTAreaOnKPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB) {
            acceptorAreaPressTab = true;
        }
    }

    public void acceptorTAreaOnKReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.TAB && acceptorAreaPressTab) {
            acceptorTextArea.deleteText(
                    acceptorTextArea.getCaretPosition() - 1, acceptorTextArea.getCaretPosition());
            estimatedPriceTextField.requestFocus();
            acceptorAreaPressTab = false;
        }
    }

}
