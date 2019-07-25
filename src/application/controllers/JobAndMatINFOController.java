package application.controllers;

import application.sql.daos.EmployeeDAO;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.JobAndMaterials;
import application.util.converters.StringConverterFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
        this.jobAndMat = jobAndMat;
        doerChoiceBox.setValue(jobAndMat.getDoer());
        headNameLabel.setText(jobAndMat.getJob().getName());
        numberOfTextField.setText(jobAndMat.getNumberOf().toString());
        priceTextField.setText(jobAndMat.getJob().getAmount());
        costPriceTextField.setText(jobAndMat.getCostPrice());
        commentTextArea.setText(jobAndMat.getComment());
        discountTextField.setText(jobAndMat.getDiscount());
        warrantyTextField.setText(jobAndMat.getJob().getWarranty().toString());
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        jobAndMat.setDoer(doerChoiceBox.getValue());
        jobAndMat.setNumberOf(Integer.parseInt(numberOfTextField.getText()));
    }
}
