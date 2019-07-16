package application.controllers;

import application.sql.entitys.work.Client;
import application.sql.entitys.work.Payment;
import application.util.AlertCaster;
import application.util.ControllerManager;
import application.util.tablemanagers.ClientTableManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;

public class ClientINFOController {

    @FXML
    public TextField mobileTextField;
    public Button deleteButton;
    public Button saveButton;
    public TextField emailTextField;
    public TextField findRecommendationTextField;
    public TextField annotationTextField;
    public TableView<Payment> paymentsTableView;
    public TableColumn<Payment, String> dateTableColumn;
    public TableColumn<Payment, String> commentTableColumn;
    public TableColumn<Payment, String> amountTableColumn;
    public Button newInComePaymentButton;
    public Button newOutComePaymentButton;
    public Tab generalTab;
    public Tab paymentsTab;
    public Tab ordersTab;
    public TabPane tabPane;

    private Client client;
    private Stage stage;
    private ClientTableManager clientTableManager;
    private boolean isNewClient;

    @FXML
    private URL location;

    @FXML
    private Label clientNameHeadLabel;

    @FXML
    private TextField nameTextField;
    private boolean isNewClientAdded;

    @FXML
    void initialize() {
    }

    public void setClient(Client client) {
        this.client = client;
        setFieldsForClient();
    }

    public void setFieldsForClient() {
        if (client != null) {
            setTabsDisable(false);
            setClientInfoInTextFields(client);
            initPaymentsTable(client);
        } else {
            clearInfoTextFields();
            setTabsDisable(true);
        }
    }

    private void setTabsDisable(boolean value) {
        paymentsTab.setDisable(value);
        ordersTab.setDisable(value);
        tabPane.getSelectionModel().select(generalTab);
    }

    private void initPaymentsTable(Client client) {
        ObservableList<Payment> paymentObservableList = FXCollections.observableArrayList(client.getPaymentList());
        paymentsTableView.setItems(paymentObservableList);
        initPaymentsColumn();

        paymentsTableView.getSortOrder().add(dateTableColumn);
        dateTableColumn.setSortType(TableColumn.SortType.DESCENDING);
        paymentsTableView.sort();
    }

    private void initPaymentsColumn() {
        dateTableColumn.setCellValueFactory(param -> param.getValue().dateTimeProperty());
        commentTableColumn.setCellValueFactory(param -> param.getValue().commentProperty());
        amountTableColumn.setCellValueFactory(param -> param.getValue().amountGRNProperty());

        amountTableColumn.setStyle("-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-alignment: CENTER;");
    }

    private void clearInfoTextFields() {
        isNewClientAdded = false;
        clientNameHeadLabel.setText("Новый клиент");
        nameTextField.setText("");
        mobileTextField.setText("");
        emailTextField.setText("");
        findRecommendationTextField.setText("");
        annotationTextField.setText("");
    }

    private void setClientInfoInTextFields(Client client) {
        clientNameHeadLabel.setText(client.getName());
        nameTextField.setText(client.getName());
        mobileTextField.setText(client.getMobileNumber());
        emailTextField.setText(client.getEmail());
        findRecommendationTextField.setText(client.getFindRecommendation());
        annotationTextField.setText(client.getAnnotation());
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        stage.hide();
//        try {
//            if (!isNewClient) {
//                if (AlertCaster.castConfirmAlert("Вы уверены что хотите удалить клиента?").get()
//                        == ButtonType.OK) {
//                    clientTableManager.delete(client);
//                    stage.hide();
//                }
//            } else {
//                stage.hide();
//            }
//        } catch (PersistenceException e) {
//            AlertCaster.castInfoAlert("Нельзя удалить клиента, без удаления всех операций свянных с клиентом!");
//            e.printStackTrace();
//        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (isAallFieldsAreFielled()) {
            if (!isNewClient) {
                updateClientFromTextFields(client);
            } else {
                client = createClientFromTextFields();
                clientTableManager.add(client);
                isNewClientAdded = true;
            }
            stage.hide();
        } else {
            AlertCaster.castInfoAlert("Заполните все поля со звездочкой *");
        }
    }

    private boolean isAallFieldsAreFielled() {
        if (!nameTextField.getText().equals("")
                && !mobileTextField.getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void updateClientFromTextFields(Client client) {
        client.setName(nameTextField.getText());
        client.setMobileNumber(mobileTextField.getText());
        client.setEmail(emailTextField.getText());
        client.setFindRecommendation(findRecommendationTextField.getText());
        client.setAnnotation(annotationTextField.getText());
        System.out.println(client.getId());
        clientTableManager.update(client);
    }

    private Client createClientFromTextFields() {
        client = new Client(
                nameTextField.getText(),
                mobileTextField.getText(),
                emailTextField.getText(),
                findRecommendationTextField.getText(),
                annotationTextField.getText()
        );
        return client;
    }

    public void setClientTableManager(ClientTableManager clientTableManager) {
        this.clientTableManager = clientTableManager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setIsNewClient(boolean isNewClient) {
        this.isNewClient = isNewClient;
    }

    public void newInComePaymentButtonOnAction(ActionEvent actionEvent) {
        openNewPaymentWindow(true);
    }

    public void newOutComePaymentButtonOnAction(ActionEvent actionEvent) {
        openNewPaymentWindow(false);
    }

    private void openNewPaymentWindow(boolean isInCome) {
        ControllerManager.getPaymentsController().openNewPaymentWindowFromClientINFO(isInCome, getThisWindow(), client);

        NewPaymentController newPaymentController = ControllerManager.getNewPaymentController();
        if (newPaymentController.isNewPaymentAdded()) {
            client.getPaymentList().add(newPaymentController.getNewPayment());
            initPaymentsTable(client);
        }
    }

    public Client getClient() {
        return client;
    }

    private Window getThisWindow() {
        return tabPane.getScene().getWindow();
    }

    public boolean isNewClientAdded() {
        return isNewClientAdded;
    }
}