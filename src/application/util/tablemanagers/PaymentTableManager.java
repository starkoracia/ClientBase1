package application.util.tablemanagers;

import application.interfaces.TableManager;
import application.sql.daos.PaymentDAO;
import application.sql.entitys.work.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PaymentTableManager implements TableManager<Payment> {

    private static ObservableList<Payment> paymentList;
    private static ObservableList<Payment> backupPaymentList;
    private static PaymentDAO paymentDAO;

    public PaymentTableManager() {
        if(paymentList == null) {
            paymentList = FXCollections.observableArrayList();
        }
        if(backupPaymentList == null) {
            backupPaymentList = FXCollections.observableArrayList();
        }
        if(paymentDAO == null) {
            paymentDAO = new PaymentDAO();
        }
        fillData();
    }

    @Override
    public void fillData() {
        updateAll();
        makeBackupList();
    }

    @Override
    public void makeBackupList() {
        backupPaymentList.clear();
        backupPaymentList.addAll(paymentList);
    }

    @Override
    public void add(Payment payment) {
        paymentList.add(payment);
        backupPaymentList.add(payment);
        paymentDAO.add(payment);
    }

    @Override
    public void updateAll() {
        paymentList = FXCollections.observableArrayList(paymentDAO.getAll());
        makeBackupList();
    }

    @Override
    public void update(Payment payment) {
        paymentDAO.update(payment);
    }

    @Override
    public void delete(Payment payment) {
        paymentList.remove(payment);
        backupPaymentList.remove(payment);
        paymentDAO.delete(payment);
    }

    @Override
    public ObservableList<Payment> getObservableList() {
        return paymentList;
    }

    @Override
    public ObservableList<Payment> getBackupObservableList() {
        return backupPaymentList;
    }
}
