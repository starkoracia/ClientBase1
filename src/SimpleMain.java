import application.sql.HibernateSessionFactory;
import org.hibernate.SessionFactory;

import java.util.regex.Pattern;

public class SimpleMain {
    public static void main(String[] args) {


//        HibernateSessionFactory.setConnectionUrlLogin("paparacia1");
//        SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
//        sessionFactory.close();
        String s1 = "ffffffff";
        String s2 = "ffffffffaaaaaaa";
        String s3 = "ffffffffdd";
        int v1 = 30 - s1.length();
        System.out.println(String.format("|%s%" + v1 + "s|", s1, "150 uhy."));
        int v2 = 30 - s2.length();
        System.out.println(String.format("|%s%" + v2 + "s|", s2, "150 uhy."));
        System.out.println(String.format("|%s%30s|", s3, "150 uhy."));

    }
}
