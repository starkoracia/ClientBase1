package application.util.converters;

import application.sql.daos.ClientDAO;
import application.sql.entitys.work.Client;
import javafx.util.StringConverter;

public class ClientStringConverter extends StringConverter<Client> {

    private Integer clientId;

    public ClientStringConverter() {
    }

    @Override
    public String toString(Client client) {
        if (client == null) {
            return null;
        }
        clientId = client.getId();
        return client.getName() + "  " + client.getMobileNumber();
    }

    @Override
    public Client fromString(String string) {
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getByKey(clientId);
        return client;
    }

}
