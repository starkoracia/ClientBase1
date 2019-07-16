package application.sql.daos;

import application.sql.entitys.work.Employee;
import application.sql.interfaces.dao.DAO;
import application.sql.connectors.SessionConnector;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeeDAO extends SessionConnector implements DAO<Employee,Integer> {

    @Override
    public void add(Employee employee) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
    }

    @Override
    public Employee getByKey(Integer id) {
        if (!isOpen()) {
            openSession();
        }
        return session.get(Employee.class, id);
    }

    @Override
    public List<Employee> getAll() {
        if (!isOpen()) {
            openSession();
        }
        List<Employee> employeeList = (List<Employee>) session.createQuery("from Employee").list();
        return employeeList;
    }

    @Override
    public void update(Employee employee) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();
    }

    @Override
    public void delete(Employee employee) {
        if (!isOpen()) {
            openSession();
        }
        Transaction transaction = session.beginTransaction();
        session.delete(employee);
        transaction.commit();
    }
}
