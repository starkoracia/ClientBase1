package application.sql.daos;

import application.sql.entitys.work.Client;
import application.sql.interfaces.dao.DAO;
import application.sql.connectors.SessionConnector;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDAO extends SessionConnector implements DAO<Client, Integer> {

    @Override
    public void add(Client client) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(client);
        transaction.commit();
    }

    @Override
    public Client getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(Client.class, id);
    }

    @Override
    public List<Client> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<Client> clientList = (List<Client>) session.createQuery("from Client").list();
        return clientList;
    }

    @Override
    public void update(Client client) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(client);
        transaction.commit();
    }

    @Override
    public void delete(Client client) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(client);
        transaction.commit();
    }

}
