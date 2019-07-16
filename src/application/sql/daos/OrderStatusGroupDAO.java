package application.sql.daos;

import application.sql.connectors.SessionConnector;
import application.sql.entitys.work.OrderStatusGroup;
import application.sql.interfaces.dao.DAO;
import org.hibernate.Transaction;

import java.util.List;

public class OrderStatusGroupDAO extends SessionConnector implements DAO<OrderStatusGroup, Integer> {

    @Override
    public void add(OrderStatusGroup orderStatusGroup) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(orderStatusGroup);
        transaction.commit();

    }

    @Override
    public OrderStatusGroup getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(OrderStatusGroup.class, id);
    }

    @Override
    public List<OrderStatusGroup> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<OrderStatusGroup> orderStatusGroupsList = (List<OrderStatusGroup>)session.createQuery("from OrderStatusGroup").list();
        return orderStatusGroupsList;
    }

    @Override
    public void update(OrderStatusGroup orderStatusGroup) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(orderStatusGroup);
        transaction.commit();
    }

    @Override
    public void delete(OrderStatusGroup orderStatusGroup) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(orderStatusGroup);
        transaction.commit();
    }
}
