package application.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

import application.util.ControllerManager;
import application.util.FocusRepeater;
import application.util.tablemanagers.ClientTableManager;
import application.sql.entitys.work.Client;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

public class ClientsController implements Initializable {


    private final String clientInfoViewLocation = "../view/ClientINFO.fxml";
    private FXMLLoader loader;
    private ClientINFOController clientINFOController;
    private Stage clientInfoStage;
    private VBox clientInfoView;
    private ClientTableManager clientTableManager;
    private Scene clientInfoscene;
    private Window tempOwner;

    @FXML
    public FontAwesomeIconView searchIcon;
    @FXML
    public Button newClientButton;
    @FXML
    public CustomTextField searchTextField;
    @FXML
    private URL location;
    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> nameTableColumn;
    @FXML
    private TableColumn<Client, String> emailTableColumn;
    @FXML
    private TableColumn<Client, String> mobileNumberTableColumn;
    @FXML
    private TableColumn<Client, String> findRecommendationTableColumn;
    @FXML
    private TableColumn<Client, String> annotationTableColumn;

    public ClientsController() {
        this.clientTableManager = new ClientTableManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableItems();
        setCellDataValues();
        initClientInfoViewLoader();
        setSearchField();

        if(tempOwner == null) {
            Platform.runLater(() -> tempOwner = getThisWindow());
        }
    }

    private void setTableItems() {
        clientsTable.setItems(clientTableManager.getBackupObservableList());
    }

    private void setSearchField() {
        setChangeTextListenerToSearchField();
        setClearedButtonToSearchField(searchTextField);
    }

    private void setChangeTextListenerToSearchField() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> doSearch());
    }

    private void initClientInfoViewLoader() {
        try {
            loader = new FXMLLoader(getClass().getResource(clientInfoViewLocation));
            clientInfoView = loader.load();
            clientInfoscene = new Scene(clientInfoView);
            clientINFOController = loader.getController();
            ControllerManager.setClientINFOController(clientINFOController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCellDataValues() {
        nameTableColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mobileNumberTableColumn.setCellValueFactory(cellData -> cellData.getValue().mobileNumberProperty());
        emailTableColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        findRecommendationTableColumn.setCellValueFactory(cellData -> cellData.getValue().findRecommendationProperty());
        annotationTableColumn.setCellValueFactory(cellData -> cellData.getValue().annotationProperty());
    }

    public void pressedEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            showClientInfoView(false, getThisWindow());
        }
    }

    private Window getThisWindow() {
        return ControllerManager.getRootViewController().mainBorderPane.getScene().getWindow();
    }

    public void showClientInfoView(boolean isNewClient, Window owner) {
        if (clientInfoStage == null || tempOwner != owner) {
            clientInfoStage = new Stage();
            setStage(owner, clientInfoStage, "Данные клиента", clientInfoscene);
        }


        setClientINFOController(isNewClient);

        getStage(owner).hide();
        clientInfoShowAndWait(owner);
        getStage(owner).show();
    }

    public void openClientInfoWindowFromNewPayment(boolean isNewClient, Window owner) {
        if (clientInfoStage == null || tempOwner != owner) {
            clientInfoStage = new Stage();
            setStage(owner, clientInfoStage, "Данные клиента", clientInfoscene);
        }

        setClientINFOController(isNewClient);

        clientInfoShowAndWait(owner);
    }

    private Stage getStage(Window window) {
        Stage stage = (Stage) window;
        return stage;
    }

    private void setClientINFOController(boolean isNewClient) {
        clientINFOController.setStage(clientInfoStage);
        clientINFOController.setClientTableManager(clientTableManager);
        clientINFOController.setIsNewClient(isNewClient);
        if (!isNewClient) {
            Client client = clientsTable.getSelectionModel().getSelectedItem();
            clientINFOController.setClient(client);
        } else {
            clientINFOController.setClient(null);
        }
        FocusRepeater.repeat(clientINFOController.nameTextField);
    }

    private void setStage(Window owner, Stage stage, String title, Scene scene) {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    private void clientInfoShowAndWait(Window owner) {
        clientInfoStage.showAndWait();
        clientsTable.sort();
    }

    public void newClientButtonOnAction(ActionEvent actionEvent) {
        showClientInfoView(true, getThisWindow());
    }

    public void onMouseClickedTable(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            showClientInfoView(false, getThisWindow());
        }
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

    public void onPressedKeySearchField(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            doSearch();
        }
    }

    public void mouseClickedSearch(MouseEvent mouseEvent) {
        doSearch();
    }

    private void doSearch() {
        clientTableManager.getBackupObservableList().clear();
        for (Client client : clientTableManager.getObservableList()) {
            if (client.getName().toLowerCase().contains(searchTextField.getText().toLowerCase())
                    || client.getMobileNumber().toLowerCase().contains(searchTextField.getText().toLowerCase())) {
                clientTableManager.getBackupObservableList().add(client);
            }
        }
    }

}