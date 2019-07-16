package application.util.tablemanagers;

import application.interfaces.TableManager;
import application.sql.daos.ClientDAO;
import application.sql.entitys.work.Client;
import application.sql.services.work.ClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientTableManager implements TableManager<Client> {

    private static ObservableList<Client> clientsList;
    private static ObservableList<Client> backupClientsList;
    private static ClientDAO clientDAO;

    public ClientTableManager() {
        clientsList = FXCollections.observableArrayList();
        backupClientsList = FXCollections.observableArrayList();
        clientDAO = new ClientDAO();
        fillData();
    }

    @Override
    public void fillData() {
        updateAll();
        makeBackupList();
    }

    @Override
    public void makeBackupList() {
        backupClientsList.clear();
        backupClientsList.addAll(clientsList);
    }

    @Override
    public void add(Client client) {
        clientDAO.add(client);
        clientsList.add(client);
        backupClientsList.add(client);
    }

    @Override
    public void updateAll() {
        clientsList = FXCollections.observableArrayList(clientDAO.getAll());
        makeBackupList();
    }

    @Override
    public void update(Client client) {
        clientDAO.update(client);
    }

    @Override
    public void delete(Client client) {
        clientDAO.delete(client);
        clientsList.remove(client);
        backupClientsList.remove(client);
    }

    @Override
    public ObservableList<Client> getObservableList() {
        return clientsList;
    }

    @Override
    public ObservableList<Client> getBackupObservableList() {
        return backupClientsList;
    }
}
