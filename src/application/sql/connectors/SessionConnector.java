package application.sql.connectors;

import application.sql.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class SessionConnector {

    public static Session session;

    public void openSession() {
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
            System.out.println("Session is Open!");
        } catch (HibernateException e) {
            System.out.println("Open session ERROR!");
            e.printStackTrace();
        }
    }

    public void closeSession() {
        if(isOpen()){
            try {
                session.close();
                System.out.println("Session is Close!");
            } catch (HibernateException e) {
                System.out.println("Close session ERROR!");
                e.printStackTrace();
            }
        }
    }

    public Session getSession() {
        if(isOpen()) {
            return session;
        } else {
            return null;
        }
    }

    public boolean isOpen() {
        if (session == null) {
            return false;
        } else {
            if (session.isOpen()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
