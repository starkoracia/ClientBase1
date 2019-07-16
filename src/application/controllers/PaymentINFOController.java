package application.controllers;

import application.sql.daos.ClientDAO;
import application.sql.entitys.work.Client;
import application.sql.entitys.work.Payment;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentINFOController implements Initializable {
    public Label paymentTypeHeadLabel;
    public Label amountLabel;
    public Label clientLabel;
    public TextArea commentTextArea;
    public Label articleLabel;
    public Label employeeLabel;
    public Label headDateLabel;

    private Payment payment;

    private ClientDAO clientDAO;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientDAO = new ClientDAO();
        commentTextArea.setEditable(false);
    }

    private void setPaymentInfo() {
        setHeadLabel();
        amountLabel.setText(payment.getAmount());
        Client client = payment.getPaymentClient();
        if (client != null) {
            clientLabel.setText(String.format("%s  %s", client.getName(), client.getMobileNumber()));
        } else {
            clientLabel.setText("Клиент не найден");
        }
        commentTextArea.setText(payment.getComment());
        articleLabel.setText(payment.getPaymentArticle().getPaymentArticle());
        employeeLabel.setText(payment.getEmployee().getName());
    }

    private void setHeadLabel() {
        if (payment.isInComePayment()) {
            paymentTypeHeadLabel.setText("Приход денег");
        } else {
            paymentTypeHeadLabel.setText("Расход денег");
        }
        headDateLabel.setText(String.format(" от %s %s", payment.getPaymentDateTime().toLocalDate(), payment.getPaymentDateTime().toLocalTime()));
    }

    public void setPayment(Payment selectedItem) {
        this.payment = selectedItem;
        setPaymentInfo();
    }
}
