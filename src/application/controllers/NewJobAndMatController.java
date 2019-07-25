package application.controllers;

import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Job;
import application.sql.entitys.work.JobAndMaterials;
import application.sql.entitys.work.Order;
import application.util.AlertCaster;
import application.util.tablemanagers.JobAndMatTableManager;
import application.util.tablemanagers.JobTableManager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class NewJobAndMatController implements Initializable {

    public TextField nameTextField;
    public TextField numberOfTextField;
    public TextField priceTextField;
    public TextField costPriceTextField;
    public TextArea commentTextArea;
    public TextField discountTextField;
    public TextField warrantyTextField;
    public Button saveButton;
    public CheckBox saveToJobsCheckBox;
    private Employee doer;
    private JobAndMaterials jobAndMaterials;
    private JobAndMatTableManager jobAndMatTableManager;
    private JobTableManager jobTableManager;
    private boolean isNewJobAndMatAdded;
    private Order order;
    private Stage newJobAndMatStage;

    public NewJobAndMatController() {
        jobAndMatTableManager = new JobAndMatTableManager();
        jobTableManager = new JobTableManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void clearInfoTextFields(String name) {
        isNewJobAndMatAdded = false;
        nameTextField.setText(name);
        numberOfTextField.setText("1");
        priceTextField.setText("0");
        costPriceTextField.setText("0");
        commentTextArea.setText("");
        discountTextField.setText("0");
        warrantyTextField.setText("0");
        saveToJobsCheckBox.setSelected(false);
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        if(isAllFieldsAreFild()){
            createAndAddNewJobAndMat(saveToJobsCheckBox.isSelected());
            isNewJobAndMatAdded = true;
            newJobAndMatStage.hide();
        } else {
            AlertCaster.castInfoAlert("Заполните все поля со звездочкой *");
        }
    }

    private void createAndAddNewJobAndMat(boolean saveToJobs) {
        if(saveToJobs) {
            Job job = new Job(
                    nameTextField.getText(),
                    priceTextField.getText()
            );
            job.setWarranty(Integer.parseInt(warrantyTextField.getText()));
            jobTableManager.add(job);

            jobAndMaterials = new JobAndMaterials(
                    job,
                    doer
            );
        } else {
            jobAndMaterials = new JobAndMaterials(
                    nameTextField.getText(),
                    priceTextField.getText(),
                    doer
            );
        }
        jobAndMaterials.setNumberOf(Integer.parseInt(numberOfTextField.getText()));
        jobAndMaterials.setCostPrice(costPriceTextField.getText());
        jobAndMaterials.setComment(commentTextArea.getText());
        jobAndMaterials.setDiscount(discountTextField.getText());
    }

    private boolean isAllFieldsAreFild() {
        if(!nameTextField.getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void setDoer(Employee doer) {
        this.doer = doer;
    }

    public boolean isNewJobAndMatAdded() {
        return isNewJobAndMatAdded;
    }

    public JobAndMaterials getJobAndMaterials() {
        return jobAndMaterials;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setNewJobAndMatStage(Stage newJobAndMatStage) {
        this.newJobAndMatStage = newJobAndMatStage;
    }
}
