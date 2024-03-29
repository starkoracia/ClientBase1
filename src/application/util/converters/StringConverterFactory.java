package application.util.converters;

import application.sql.daos.*;
import application.sql.entitys.work.Employee;
import application.sql.entitys.work.Job;
import application.sql.entitys.work.OrderStatus;
import application.sql.entitys.work.PaymentArticle;
import javafx.util.StringConverter;

public class StringConverterFactory {

    private static ClientStringConverter clientStringConverter;
    private static PaymentArticleDAO articleDAO;
    private static EmployeeDAO employeeDAO;
    private static OrderStatusDAO orderStatusDAO;
    private static JobDAO jobDAO;

    private StringConverterFactory() {
    }

    public static ClientStringConverter getClientStringConverter() {
        if (clientStringConverter == null) {
            clientStringConverter = new ClientStringConverter();
        }
        return clientStringConverter;
    }

    public static StringConverter<PaymentArticle> getPaymentArticleConverter() {
        if (articleDAO == null) {
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
        if (employeeDAO == null) {
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

    public static StringConverter<OrderStatus> getOrderStatusConverter() {
        if (orderStatusDAO == null) {
            orderStatusDAO = new OrderStatusDAO();
        }
        return new StringConverter<OrderStatus>() {
            private Integer orderStatusId;

            @Override
            public String toString(OrderStatus status) {
                if (status == null) {
                    return null;
                }
                orderStatusId = status.getId();
                return status.getName();
            }

            @Override
            public OrderStatus fromString(String string) {
                return orderStatusDAO.getByKey(orderStatusId);
            }
        };
    }

    public static StringConverter<Job> getJobConverter() {
        if (jobDAO == null) {
            jobDAO = new JobDAO();
        }
        return new StringConverter<Job>() {
            private Integer jobId;

            @Override
            public String toString(Job job) {
                if (job == null) {
                    return null;
                }
                jobId = job.getId();
                return job.nameAndAmountProperty().getValue();
            }

            @Override
            public Job fromString(String string) {
                return jobDAO.getByKey(jobId);
            }
        };
    }

}