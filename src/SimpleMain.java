import application.sql.HibernateSessionFactory;
import application.sql.daos.OrderDAO;
import application.sql.entitys.work.JobAndMaterials;
import application.sql.entitys.work.Order;
import application.util.ListCloner;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SimpleMain {
    public static void main(String[] args) {



        HibernateSessionFactory.setConnectionUrlLogin("paparacia1");
        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.getByKey(8);

        for(JobAndMaterials jobAndMaterials : order.getJobAndMaterialsList()) {
            System.out.println(jobAndMaterials);
        }

        List<JobAndMaterials> newList = ListCloner.cloneSerializeList(order.getJobAndMaterialsList());

        order.getJobAndMaterialsList().get(0).setPrice("175");
        for(JobAndMaterials jobAndMaterials : newList) {
            System.out.println(jobAndMaterials);
        }

        sessionFactory.close();

    }

}
