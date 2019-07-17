package application.controllers;

import application.sql.daos.ClientDAO;
import application.sql.daos.EmployeeDAO;
import application.sql.daos.PaymentArticleDAO;
import application.sql.entitys.work.Client;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Payment;
import application.sql.entitys.work.PaymentArticle;
import application.util.AlertCaster;
import application.util.comparators.ClientComparator;
import application.util.combobox.ComboBoxAutoCompletioner;
import application.util.textfield.AutoCompletionTFBindingImpl;
import application.util.ControllerManager;
import application.util.converters.StringConverterFactory;
import application.util.tablemanagers.PaymentTableManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class NewPaymentController implements Initializable {

    public Label paymentTypeHeadLabel;
    public DatePicker paymentDatePicker;
    public TextField amountTextField;
    public TextArea commentTextArea;
    public ComboBox<PaymentArticle> paymentArticlesComboBox;
    public ComboBox<Employee> paymentEmployeesComboBox;
    public Button deleteButton;
    public Button saveButton;
    public Button newClientButton;
    public ComboBox<Client> clientComboBox;

    private boolean isInComePayment;
    private String startBalance;

    private PaymentTableManager paymentTableManager;

    private ClientDAO clientDAO;
    private PaymentArticleDAO articleDAO;
    private EmployeeDAO employeeDAO;

    private Stage newPaymentStage;
    private static AutoCompletionTFBindingImpl<Client> autoCompletionBinding;
    private Client client;
    private boolean isNewPaymentAdded;
    private Payment newPayment;
    private List<Client> textFieldClientList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFields();
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

    public void setClientCBoxDisable(boolean value) {
        clientComboBox.setDisable(value);
        newClientButton.setDisable(value);
    }

    private void initFields() {
        clientDAO = new ClientDAO();
        articleDAO = new PaymentArticleDAO();
        employeeDAO = new EmployeeDAO();
        paymentTableManager = new PaymentTableManager();
    }

    private void setComboBoxItems() {
        clientComboBox.setItems(FXCollections.observableArrayList(clientDAO.getAll()));
        paymentArticlesComboBox.setItems(FXCollections.observableArrayList(articleDAO.getAll()));
        paymentEmployeesComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        newPaymentStage.hide();
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (isAllFieldsAreFilled()) {
            addNewPayment();
            newPaymentStage.hide();
        } else {
            AlertCaster.castInfoAlert("Заполните все поля со звездочкой *");
        }
    }

    private void addNewPayment() {
        paymentTableManager.add(createPaymentFromFields());
        isNewPaymentAdded = true;
    }

    private boolean isAllFieldsAreFilled() {
        if (!amountTextField.getText().equals("")
                && !commentTextArea.getText().equals("")
                && paymentArticlesComboBox.getSelectionModel().getSelectedItem() != null
                && paymentEmployeesComboBox.getSelectionModel().getSelectedItem() != null) {
            return true;
        } else {
            return false;
        }
    }

    private Payment createPaymentFromFields() {
        newPayment = new Payment(
                isInComePayment(),
                LocalDateTime.of(paymentDatePicker.getValue(), LocalTime.now()),
                getAmountValue(),
                getNewBalance(),
                commentTextArea.getText(),
                paymentArticlesComboBox.getSelectionModel().getSelectedItem(),
                paymentEmployeesComboBox.getSelectionModel().getSelectedItem());
        client = ComboBoxAutoCompletioner.getComboBoxValue(clientComboBox);
        if (client != null) {
            newPayment.setPaymentClient(client);
            client.getPaymentList().add(newPayment);
        }
        return newPayment;
    }

    private String getNewBalance() {
        BigDecimal oldBalance = new BigDecimal(getStartBalance());
        BigDecimal amount = oldBalance.add(new BigDecimal(getAmountValue()));
        return amount.toString();
    }

    private String getAmountValue() {
        if (isInComePayment()) {
            return amountTextField.getText();
        } else {
            return String.format("-%s", amountTextField.getText());
        }
    }

    public boolean isInComePayment() {
        return isInComePayment;
    }

    public void setInComePayment(boolean inComePayment) {
        isInComePayment = inComePayment;
        if (inComePayment) {
            paymentTypeHeadLabel.setText("Приход денег");
        } else {
            paymentTypeHeadLabel.setText("Расход денег");
        }
    }

    public void clearInfoTextFields() {
        paymentDatePicker.setValue(LocalDate.now());
        amountTextField.setText("");
        commentTextArea.setText("");
        clientComboBox.getEditor().setText("");
        paymentArticlesComboBox.setValue(paymentArticlesComboBox.getItems().get(0));
        paymentEmployeesComboBox.setValue(paymentEmployeesComboBox.getItems().get(0));
        setClient(null);
    }

    private void setComboBoxConvertors() {
        clientComboBox.setConverter(StringConverterFactory.getClientStringConverter());
        paymentArticlesComboBox.setConverter(StringConverterFactory.getPaymentArticleConverter());
        paymentEmployeesComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
    }

    public String getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(String startBalance) {
        this.startBalance = startBalance;
    }

    public void setPaymentTableManager(PaymentTableManager paymentTableManager) {
        this.paymentTableManager = paymentTableManager;
    }

    public void setNewPaymentStage(Stage newPaymentStage) {
        this.newPaymentStage = newPaymentStage;
    }

    public boolean isNewPaymentAdded() {
        return isNewPaymentAdded;
    }

    public void setNewPaymentAdded(boolean newPaymentAdded) {
        isNewPaymentAdded = newPaymentAdded;
    }

    public Payment getNewPayment() {
        return newPayment;
    }

    private Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void onActionNewClientButton(ActionEvent actionEvent) {
        openNewClientWindow();
    }

    private void openNewClientWindow() {
        ControllerManager.getClientsController().openClientInfoWindowFromNewPayment(true, getThisWindow());
        ClientINFOController clientINFOController = ControllerManager.getClientINFOController();

        if(clientINFOController.isNewClientAdded()) {
            client = clientINFOController.getClient();
            clientComboBox.getItems().add(client);
            clientComboBox.setValue(client);
        }
    }

    private Window getThisWindow() {
        return commentTextArea.getScene().getWindow();
    }

    public void setFromClientINFO(Client client) {
        setClient(client);
        clientComboBox.getEditor().setText(client.getName());
        setClientCBoxDisable(true);
    }

}
