package application.sql.daos;

import application.sql.entitys.work.PaymentArticle;
import application.sql.interfaces.dao.DAO;
import application.sql.connectors.SessionConnector;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentArticleDAO extends SessionConnector implements DAO<PaymentArticle,Integer> {
    @Override
    public void add(PaymentArticle paymentArticle) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(paymentArticle);
        transaction.commit();
    }

    @Override
    public PaymentArticle getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(PaymentArticle.class, id);
    }

    @Override
    public List<PaymentArticle> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<PaymentArticle> paymentArticleList = (List<PaymentArticle>) session.createQuery("from PaymentArticle").list();
        return paymentArticleList;
    }

    @Override
    public void update(PaymentArticle paymentArticle) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(paymentArticle);
        transaction.commit();
    }

    @Override
    public void delete(PaymentArticle paymentArticle) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(paymentArticle);
        transaction.commit();
    }
}
