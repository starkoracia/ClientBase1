package application.sql.daos;

import application.sql.connectors.SessionConnector;
import application.sql.entitys.work.JobAndMaterials;
import application.sql.interfaces.dao.DAO;
import org.hibernate.Transaction;

import java.util.List;

public class JobAndMaterialsDAO extends SessionConnector implements DAO<JobAndMaterials, Integer> {

    @Override
    public void add(JobAndMaterials jobAndMaterials) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(jobAndMaterials);
        transaction.commit();
    }

    @Override
    public JobAndMaterials getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(JobAndMaterials.class, id);
    }

    @Override
    public List<JobAndMaterials> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<JobAndMaterials> jobAndMaterialsList = (List<JobAndMaterials>) session.createQuery("from JobAndMaterials").list();
        return jobAndMaterialsList;
    }

    @Override
    public void update(JobAndMaterials jobAndMaterials) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(jobAndMaterials);
        transaction.commit();
    }

    @Override
    public void delete(JobAndMaterials jobAndMaterials) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(jobAndMaterials);
        transaction.commit();
    }
}
