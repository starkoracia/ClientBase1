import application.sql.HibernateSessionFactory;
import application.sql.daos.*;
import application.sql.entitys.work.*;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SimpleMain {
    public static void main(String[] args) {


        HibernateSessionFactory.setConnectionUrlLogin("paparacia1");
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

        OrderDAO orderDAO = new OrderDAO();
        OrderStatusDAO orderStatusDAO = new OrderStatusDAO();
        ClientDAO clientDAO = new ClientDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        JobAndMaterialsDAO jobAndMaterialsDAO = new JobAndMaterialsDAO();

//        Order order = new Order(status, true, clientDAO.getByKey(6), LocalDateTime.now());

        for(JobAndMaterials jobAndMaterials : orderDAO.getByKey(1).getJobAndMaterialsList()) {
            System.out.println("1 =" + jobAndMaterials);
        }



        sessionFactory.close();

    }
}
