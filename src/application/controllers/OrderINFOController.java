package application.controllers;

import application.sql.daos.*;
import application.sql.entitys.work.*;
import application.util.AlertCaster;
import application.util.ControllerManager;
import application.util.JobAndMatCreater;
import application.util.combobox.AutoCompletionJobCBox;
import application.util.combobox.ComboBoxAutoCompletioner;
import application.util.comparators.ClientComparator;
import application.util.comparators.JobComparator;
import application.util.converters.IsPaidItems;
import application.util.converters.StringConverterFactory;
import application.util.tablemanagers.ClientTableManager;
import application.util.tablemanagers.JobAndMatTableManager;
import application.util.tablemanagers.JobTableManager;
import application.util.tablemanagers.OrderTableManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.math.BigDecimal;
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
    public ScrollPane jobAndMatScrollPane;
    public ComboBox<Employee> doerInJobAndMComboBox;
    public ComboBox<Job> jobComboBox;
    public Button newJobAndMatButton;
    public TableView<JobAndMaterials> jobAndMatTableView;
    public TableColumn<JobAndMaterials, String> nameTableColumn;
    public TableColumn<JobAndMaterials, Integer> numberOfTableColumn;
    public TableColumn<JobAndMaterials, String> priceTableColumn;
    public TableColumn<JobAndMaterials, String> amountTableColumn;
    public TextArea doerNoteTextArea;
    public TextArea recommendationTextArea;
    public Button addJobToTableButton;
    public Label discountLabel;
    public Label amountLabel;

    private OrderStatusDAO orderStatusDAO;
    private EmployeeDAO employeeDAO;
    private JobDAO jobDAO;
    private JobAndMaterialsDAO jobAndMaterialsDAO;
    private JobAndMatTableManager jobAndMatTableManager;
    private JobTableManager jobTableManager;
    private ClientTableManager clientTableManager;
    private OrderTableManager orderTableManager;
    private Client client;
    private Order order;
    private Stage orderInfoStage;
    private FXMLLoader newJobAndMatLoader;
    private FXMLLoader jobAndMatINFOLoader;
    private String newJobAndMatViewLocation = "../view/NewJobAndMat.fxml";
    private String jobAndMatINFOViewLocation = "../view/JobAndMatINFO.fxml";
    private AnchorPane newJobAndMatView;
    private NewJobAndMatController newJobAndMatController;
    private Stage newJobAndMatStage;
    private JobAndMaterials jobAndMat;
    private OrderDAO orderDao;
    private SimpleStringProperty amountProperty;
    private SimpleStringProperty discountProperty;
    private AnchorPane jobAndMatINFOView;
    private JobAndMatINFOController jobAndMatINFOController;
    private Stage jobAndMatINFOStage;

    public OrderINFOController() {
        orderStatusDAO = new OrderStatusDAO();
        employeeDAO = new EmployeeDAO();
        jobDAO = new JobDAO();
        orderDao = new OrderDAO();
        jobAndMaterialsDAO = new JobAndMaterialsDAO();
        clientTableManager = new ClientTableManager();
        orderTableManager = new OrderTableManager();
        jobTableManager = new JobTableManager();
        jobAndMatTableManager = new JobAndMatTableManager();
        amountProperty = new SimpleStringProperty();
        discountProperty = new SimpleStringProperty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBoxes();
        setComboBoxes();
        setClientNameLable();

        setJAMTableCellDataValues();

        initNewJobAndMatLoader();
        initJobAndMatINFOLoader();

        setAmountDiscountLabel();

//        jobComboBox.setPlaceholder(newClientButton);
    }

    private void setAmountDiscountLabel() {
        amountLabel.textProperty().bind(amountProperty);
        discountLabel.textProperty().bind(discountProperty);
    }

    private void setComboBoxes() {
        setClientComboBoxAutoDisable();
        setComboBoxesItems();
        setComboBoxesConvertors();
        setComboBoxesAutoCompletion();
    }

    private void setClientNameLable() {
        clientNameLable.textProperty().bind(clientComboBox.getEditor().textProperty());
    }

    private BigDecimal getCalculateAmount() {
        BigDecimal tempAmount = new BigDecimal("0");
        for (JobAndMaterials jobAndMaterials : jobAndMatTableView.getItems()) {
            tempAmount = tempAmount.add(jobAndMaterials.getBigDecimalAmount());
        }
        tempAmount = tempAmount.subtract(getCalculateDiscount());
        return tempAmount;
    }

    private BigDecimal getCalculateDiscount() {
        BigDecimal tempAmount = new BigDecimal("0");
        for (JobAndMaterials jobAndMaterials : jobAndMatTableView.getItems()) {
            tempAmount = tempAmount.add(jobAndMaterials.getBigDecimalDiscount());
        }
        return tempAmount;
    }

    private void setAmountDiscountProperty() {
        amountProperty.setValue(getCalculateAmount().toString());
        discountProperty.setValue(getCalculateDiscount().toString());
    }

    private void setClientComboBoxAutoDisable() {
        clientComboBox.selectionModelProperty().get().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                clientComboBox.setDisable(true);
            }
        });
    }

    private void setComboBoxesAutoCompletion() {
        ComboBoxAutoCompletioner.autoCompleteComboBoxPlus(clientComboBox, ClientComparator.getComparator());
        AutoCompletionJobCBox.autoCompleteComboBoxPlus(jobComboBox, JobComparator.getComparator());
        }

    private void setComboBoxesItems() {
        clientComboBox.setItems(clientTableManager.getObservableList());
        jobComboBox.setItems(jobTableManager.getObservableList());
        managerComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
        doerComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
        doerInJobAndMComboBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
    }

    private void setComboBoxesConvertors() {
        clientComboBox.setConverter(StringConverterFactory.getClientStringConverter());
        jobComboBox.setConverter(StringConverterFactory.getJobConverter());
        managerComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
        doerComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
        doerInJobAndMComboBox.setConverter(StringConverterFactory.getEmployeeConverter());
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

    private void setJobAndMatTableItems(Order order) {
        jobAndMatTableView.setItems(FXCollections.observableArrayList(order.getJobAndMaterialsList()));
    }

    private void setJAMTableCellDataValues() {
        nameTableColumn.setCellValueFactory(param -> param.getValue().getJob().nameProperty());
        numberOfTableColumn.setCellValueFactory(param -> param.getValue().numberOfProperty().asObject());
        priceTableColumn.setCellValueFactory(param -> param.getValue().getJob().priceProperty());
        amountTableColumn.setCellValueFactory(param -> param.getValue().amountProperty());
    }

    public void setOrderToOrderInfo(Order order) {
        this.order = order;
        headOrderNumberLabel.setText(String.format("Заказ№ %s", order.getOrderNumber()));
        headOrderStatusChoiceBox.setValue(order.getStatus());
        headOrderDeadlineLabel.setText(String.format(order.deadlineDifferenceProperty().getValue()));
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

        if (doerComboBox.getValue() != null) {
            doerInJobAndMComboBox.setValue(doerComboBox.getValue());
        } else {
            doerInJobAndMComboBox.setValue(doerInJobAndMComboBox.getItems().get(0));
        }
        doerNoteTextArea.setText(order.getDoerNote());
        recommendationTextArea.setText(order.getRecommendation());
        setJobAndMatTableItems(order);
        setAmountDiscountProperty();
        jobComboBoxUpdate();
    }

    private void initNewJobAndMatLoader() {
        try {
            newJobAndMatLoader = new FXMLLoader(getClass().getResource(newJobAndMatViewLocation));
            newJobAndMatView = newJobAndMatLoader.load();
            newJobAndMatController = newJobAndMatLoader.getController();
            ControllerManager.setNewJobAndMatController(newJobAndMatController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initJobAndMatINFOLoader() {
        try {
            jobAndMatINFOLoader = new FXMLLoader(getClass().getResource(jobAndMatINFOViewLocation));
            jobAndMatINFOView = jobAndMatINFOLoader.load();
            jobAndMatINFOController = jobAndMatINFOLoader.getController();
            ControllerManager.setJobAndMatINFOController(jobAndMatINFOController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showJobAndMatINFOWindow(JobAndMaterials jobAndMat) {
        if(jobAndMatINFOStage == null) {
            jobAndMatINFOStage = new Stage();
            setStage(getThisWindow(), jobAndMatINFOStage, "Работа и материалы", jobAndMatINFOView);
        }
        setJobAndMatINFOController(jobAndMat);
        jobAndMatINFOShowAndWait();
    }

    private void setJobAndMatINFOController(JobAndMaterials jobAndMat) {
        jobAndMatINFOController.setJobAndMatToJobAndMatINFO(jobAndMat);
    }

    private void jobAndMatINFOShowAndWait() {
        jobAndMatINFOStage.showAndWait();
    }

    public void showNewJobAndMatWindow(String jobName) {
        if (newJobAndMatStage == null) {
            newJobAndMatStage = new Stage();
            setStage(getThisWindow(), newJobAndMatStage, "Добавление работы", newJobAndMatView);
        }
        setNewJobAndMatController(jobName);
        newJobAndMatShowAndWait();
    }

    private void setNewJobAndMatController(String jobName) {
        newJobAndMatController.setNewJobAndMatStage(newJobAndMatStage);
        newJobAndMatController.clearInfoTextFields(jobName);
        newJobAndMatController.setDoer(doerInJobAndMComboBox.getValue());
        newJobAndMatController.setOrder(order);
    }

    private void newJobAndMatShowAndWait() {
        newJobAndMatStage.showAndWait();
    }

    private void setStage(Window owner, Stage stage, String title, Pane view) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle(title);
        stage.setResizable(false);
        Scene scene = new Scene(view);
        stage.setScene(scene);
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

        order.setDoerNote(doerNoteTextArea.getText());
        order.setRecommendation(recommendationTextArea.getText());
        setJobAndMatFromTable(order);

        orderTableManager.update(order);
    }

    private void setJobAndMatFromTable(Order order) {
        order.getJobAndMaterialsList().clear();
        order.getJobAndMaterialsList().addAll(jobAndMatTableView.getItems());
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

    public void onActionNewJobAndMatButton(ActionEvent actionEvent) {
        openNewJobAndMatWindow("");
    }

    public void openNewJobAndMatWindow(String name) {
        showNewJobAndMatWindow(name);
        if (newJobAndMatController.isNewJobAndMatAdded()) {
            jobAndMat = newJobAndMatController.getJobAndMaterials();
            addJobAndMatToTable(jobAndMat);
        }
    }

    private void jobComboBoxUpdate() {
        jobComboBox.setItems(jobTableManager.getObservableList());
        jobComboBox.setValue(null);
    }

    public void addJobToTableButtonOnAction(ActionEvent actionEvent) {
        addJobToTable(jobComboBox.getValue(), doerInJobAndMComboBox.getValue());
    }

    private void addJobToTable(Job job, Employee doer) {
        addJobAndMatToTable(JobAndMatCreater.create(job, doer));
    }

    private void addJobAndMatToTable(JobAndMaterials jobAndMaterials) {
        jobComboBoxUpdate();
        jobAndMatTableView.getItems().add(jobAndMaterials);
        setAmountDiscountProperty();
    }

    public void onMouseClickedOnTable(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            showJobAndMatINFOWindow(jobAndMatTableView.getSelectionModel().getSelectedItem());
        }
    }
}
