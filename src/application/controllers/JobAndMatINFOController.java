package application.controllers;

import application.sql.daos.EmployeeDAO;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.JobAndMaterials;
import application.util.AlertCaster;
import application.util.converters.StringConverterFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class JobAndMatINFOController implements Initializable {

    public ChoiceBox<Employee> doerChoiceBox;
    public TextField numberOfTextField;
    public TextField priceTextField;
    public TextField costPriceTextField;
    public TextArea commentTextArea;
    public TextField discountTextField;
    public TextField warrantyTextField;
    public Button saveButton;
    public Label headNameLabel;
    private EmployeeDAO employeeDAO;
    private JobAndMaterials jobAndMat;
    private Stage jobAndMatINFOStage;
    private boolean saveButtonPressed;

    public JobAndMatINFOController() {
        employeeDAO = new EmployeeDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBox();
    }

    private void setChoiceBox() {
        doerChoiceBox.setConverter(StringConverterFactory.getEmployeeConverter());
        doerChoiceBox.setItems(FXCollections.observableArrayList(employeeDAO.getAll()));
    }

    public void setJobAndMatToJobAndMatINFO(JobAndMaterials jobAndMat) {
        saveButtonPressed = false;
        this.jobAndMat = jobAndMat;
        if(jobAndMat.getDoer() != null) {
            doerChoiceBox.setValue(jobAndMat.getDoer());
        } else {
            doerChoiceBox.setValue(null);
        }
        headNameLabel.setText(jobAndMat.getName());
        numberOfTextField.setText(jobAndMat.getNumberOf().toString());
        priceTextField.setText(jobAndMat.getPrice());
        costPriceTextField.setText(jobAndMat.getCostPrice());
        commentTextArea.setText(jobAndMat.getComment());
        discountTextField.setText(jobAndMat.getDiscount());
        warrantyTextField.setText(jobAndMat.getWarranty().toString());
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (isAllFieldsAreFiled()) {
            updateJobAndMat();
            saveButtonPressed = true;
            jobAndMatINFOStage.hide();
        } else {
            AlertCaster.castInfoAlert("Заполните все поля со звездочкой *");
        }
    }

    private boolean isAllFieldsAreFiled() {
        if (!numberOfTextField.getText().equals("") && !priceTextField.getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void updateJobAndMat() {
        jobAndMat.setDoer(doerChoiceBox.getValue());
        jobAndMat.setNumberOf(Integer.parseInt(numberOfTextField.getText()));
        jobAndMat.setPrice(priceTextField.getText());
        jobAndMat.setCostPrice(costPriceTextField.getText());
        jobAndMat.setComment(commentTextArea.getText());
        jobAndMat.setDiscount(discountTextField.getText());
        jobAndMat.setWarranty(Integer.parseInt(warrantyTextField.getText()));
    }

    public void setJobAndMatINFOStage(Stage jobAndMatINFOStage) {
        this.jobAndMatINFOStage = jobAndMatINFOStage;
    }

    public boolean isSaveButtonPressed() {
        return saveButtonPressed;
    }

    public JobAndMaterials getJobAndMat() {
        return jobAndMat;
    }
}
