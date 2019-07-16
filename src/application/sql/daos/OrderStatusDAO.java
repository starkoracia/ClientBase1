package application.sql.daos;

import application.sql.connectors.SessionConnector;
import application.sql.entitys.work.OrderStatus;
import application.sql.interfaces.dao.DAO;
import org.hibernate.Transaction;

import java.util.List;

public class OrderStatusDAO extends SessionConnector implements DAO<OrderStatus, Integer> {
    @Override
    public void add(OrderStatus orderStatus) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(orderStatus);
        transaction.commit();
    }

    @Override
    public OrderStatus getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(OrderStatus.class, id);
    }

    @Override
    public List<OrderStatus> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<OrderStatus> orderStatusList = (List<OrderStatus>) session.createQuery("from OrderStatus ").list();
        return orderStatusList;
    }

    @Override
    public void update(OrderStatus orderStatus) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(orderStatus);
        transaction.commit();
    }

    @Override
    public void delete(OrderStatus orderStatus) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(orderStatus);
        transaction.commit();
    }
}
