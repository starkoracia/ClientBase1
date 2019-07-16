package application.sql;

import application.sql.entitys.work.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;
    private static String connectionUrl;

    private HibernateSessionFactory() {
    }

    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null || sessionFactory.isClosed()) {

                Configuration configuration = new Configuration();

                configuration.configure("hibernate.cfg.xml");

                configuration.setProperty("hibernate.connection.url", connectionUrl);

                configuration.addAnnotatedClass(Client.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Payment.class);
                configuration.addAnnotatedClass(PaymentArticle.class);
                configuration.addAnnotatedClass(OrderStatus.class);
                configuration.addAnnotatedClass(OrderStatusGroup.class);
                configuration.addAnnotatedClass(Job.class);
                configuration.addAnnotatedClass(JobAndMaterials.class);
                configuration.addAnnotatedClass(Order.class);

                Properties properties = configuration.getProperties();

                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
                serviceRegistryBuilder.applySettings(properties);

                StandardServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        } catch (Exception e) {
            System.out.println("ERROR! Создание sessionFactory!" + e);
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void setConnectionUrlLogin(String userLogin) {
        HibernateSessionFactory.connectionUrl = String.format("jdbc:mysql://localhost:3306/%s?serverTimezone=UTC", userLogin);
    }
}
