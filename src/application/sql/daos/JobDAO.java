package application.sql.daos;

import application.sql.connectors.SessionConnector;
import application.sql.entitys.work.Job;
import application.sql.interfaces.dao.DAO;
import org.hibernate.Transaction;

import java.util.List;

public class JobDAO extends SessionConnector implements DAO<Job, Integer> {

    @Override
    public void add(Job job) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(job);
        transaction.commit();
    }

    @Override
    public Job getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(Job.class, id);
    }

    @Override
    public List<Job> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<Job> jobList = (List<Job>) session.createQuery("from Job").list();
        return jobList;
    }

    @Override
    public void update(Job job) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(job);
        transaction.commit();
    }

    @Override
    public void delete(Job job) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(job);
        transaction.commit();
    }
}
