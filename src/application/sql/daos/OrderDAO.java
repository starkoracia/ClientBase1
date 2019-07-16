package application.sql.daos;

import application.sql.connectors.SessionConnector;
import application.sql.entitys.work.Order;
import application.sql.interfaces.dao.DAO;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDAO extends SessionConnector implements DAO<Order, Integer> {

    @Override
    public void add(Order order) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
    }

    @Override
    public Order getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(Order.class, id);
    }

    @Override
    public List<Order> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<Order> orderList = (List<Order>)session.createQuery("from Order").list();
        return orderList;
    }

    @Override
    public void update(Order order) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
    }

    @Override
    public void delete(Order order) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(order);
        transaction.commit();
    }
}
