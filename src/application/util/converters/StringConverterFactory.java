package application.util.converters;

import application.sql.daos.ClientDAO;
import application.sql.daos.EmployeeDAO;
import application.sql.daos.PaymentArticleDAO;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.PaymentArticle;
import javafx.util.StringConverter;

public class StringConverterFactory {

    private static ClientStringConverter clientStringConverter;
    private static PaymentArticleDAO articleDAO;
    private static EmployeeDAO employeeDAO;

    private StringConverterFactory() {
    }

    public static ClientStringConverter getClientStringConverter() {
        if (clientStringConverter == null) {
            clientStringConverter = new ClientStringConverter();
        }
        return clientStringConverter;
    }

    public static StringConverter<PaymentArticle> getPaymentArticleConverter() {
        if(articleDAO == null) {
            articleDAO = new PaymentArticleDAO();
        }
        return new StringConverter<PaymentArticle>() {

            private Integer articleId;

            @Override
            public String toString(PaymentArticle article) {
                if (article == null) {
                    return null;
                }
                articleId = article.getId();
                return article.getPaymentArticle();
            }

            @Override
            public PaymentArticle fromString(String string) {
                return articleDAO.getByKey(articleId);
            }
        };
    }

    public static StringConverter<Employee> getEmployeeConverter() {
        if(employeeDAO == null) {
            employeeDAO = new EmployeeDAO();
        }
        return new StringConverter<Employee>() {

            private Integer employeeId;

            @Override
            public String toString(Employee employee) {
                if (employee == null) {
                    return null;
                }
                employeeId = employee.getId();
                return employee.getName();
            }

            @Override
            public Employee fromString(String string) {
                return employeeDAO.getByKey(employeeId);
            }
        };
    }

}