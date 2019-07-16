package application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewOrderController implements Initializable {

    public TextField amountTextField;
    public CustomTextField clientTextField;
    public TextArea commentTextArea;
    public ComboBox paymentArticlesComboBox;
    public ComboBox paymentEmployeesComboBox;
    public Button deleteButton;
    public Button saveButton;
    public ChoiceBox<String> isPaidChoiceBox;
    public ComboBox clientComboBox;
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
    public ComboBox managerComboBox;
    public ComboBox doerComboBox;

    private ObservableList<String> isPaidList;

    public NewOrderController() {
        isPaidList = FXCollections.observableArrayList("Платный", "По гарантии");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPaidChoiceBox();
    }

    private void setPaidChoiceBox() {
        isPaidChoiceBox.setItems(isPaidList);
        isPaidChoiceBox.setValue("Платный");
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {

    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

    }

    private Window getThisWindow() {
        return isPaidChoiceBox.getScene().getWindow();
    }

    public void onActionNewClientButton(ActionEvent actionEvent) {
    }
}
