package application.sql.daos;

import application.sql.entitys.work.Payment;
import application.sql.interfaces.dao.DAO;
import application.sql.connectors.SessionConnector;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDAO extends SessionConnector implements DAO<Payment,Integer> {
    @Override
    public void add(Payment payment) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(payment);
        transaction.commit();
    }

    @Override
    public Payment getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(Payment.class, id);
    }

    @Override
    public List<Payment> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<Payment> paymentList = (List<Payment>) session.createQuery("from Payment").list();
        return paymentList;
    }

    @Override
    public void update(Payment payment) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(payment);
        transaction.commit();
    }

    @Override
    public void delete(Payment payment) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(payment);
        transaction.commit();
    }
}
