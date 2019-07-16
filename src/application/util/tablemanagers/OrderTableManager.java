package application.util.tablemanagers;

import application.interfaces.TableManager;
import application.sql.daos.OrderDAO;
import application.sql.entitys.work.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderTableManager implements TableManager<Order> {

    private static ObservableList<Order> orderList;
    private static ObservableList<Order> backupOrderList;
    private static OrderDAO orderDAO;

    public OrderTableManager() {
        orderList = FXCollections.observableArrayList();
        backupOrderList = FXCollections.observableArrayList();
        orderDAO = new OrderDAO();
        fillData();
    }

    @Override
    public void fillData() {
        updateAll();
        makeBackupList();
    }

    @Override
    public void makeBackupList() {
        backupOrderList.clear();
        backupOrderList.addAll(orderList);
    }

    @Override
    public void add(Order order) {
        orderList.add(order);
        backupOrderList.add(order);
        orderDAO.add(order);
    }

    @Override
    public void updateAll() {
        orderList = FXCollections.observableArrayList(orderDAO.getAll());
        makeBackupList();
    }

    @Override
    public void update(Order order) {
        orderDAO.update(order);
    }

    @Override
    public void delete(Order order) {
        orderList.remove(order);
        backupOrderList.remove(order);
        orderDAO.delete(order);
    }

    @Override
    public ObservableList<Order> getObservableList() {
        return orderList;
    }

    @Override
    public ObservableList<Order> getBackupObservableList() {
        return backupOrderList;
    }
}