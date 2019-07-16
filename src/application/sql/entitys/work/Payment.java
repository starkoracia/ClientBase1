package application.sql.entitys.work;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payment_date_time")
    private LocalDateTime paymentDateTime;

    @Column(name = "amount")
    private String amount;

    @Column(name = "final_balance")
    private String finalBalance;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_client")
    private Client paymentClient;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_article")
    private PaymentArticle paymentArticle;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_employee")
    private Employee employee;

    @Column(name = "is_in_come_payment")
    private boolean isInComePayment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_order")
    private Order order;

    public Payment(boolean isInComePayment, LocalDateTime paymentDateTime, String amount, String finalBalance, String comment, PaymentArticle paymentArticle, Employee employee) {
        this.paymentDateTime = paymentDateTime;
        this.amount = amount;
        this.finalBalance = finalBalance;
        this.comment = comment;
        this.paymentArticle = paymentArticle;
        this.employee = employee;
        this.isInComePayment = isInComePayment;
    }

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(String finalBalance) {
        this.finalBalance = finalBalance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Client getPaymentClient() {
        return paymentClient;
    }

    public void setPaymentClient(Client paymentClient) {
        this.paymentClient = paymentClient;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PaymentArticle getPaymentArticle() {
        return paymentArticle;
    }

    public void setPaymentArticle(PaymentArticle paymentArticle) {
        this.paymentArticle = paymentArticle;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isInComePayment() {
        return isInComePayment;
    }

    public void setInComePayment(boolean inComePayment) {
        isInComePayment = inComePayment;
    }

    public SimpleStringProperty inOutValueProperty() {
        return new SimpleStringProperty(getInOutValue());
    }

    public SimpleIntegerProperty amountProperty() {
        return new SimpleIntegerProperty(getBigDecimalAmount().intValue());
    }

    public SimpleStringProperty amountGRNProperty() {
        return new SimpleStringProperty(String.format("%s грн", getBigDecimalAmount().toString()));
    }

    public SimpleStringProperty finalBalanceProperty() {
        return new SimpleStringProperty(getFinalBalance());
    }

    public SimpleStringProperty commentProperty() {
        return new SimpleStringProperty(getComment());
    }

    public SimpleStringProperty employeeProperty() {
        return new SimpleStringProperty(getEmployee().getName());
    }

    public SimpleStringProperty dateTimeProperty() {
        return new SimpleStringProperty(
                getPaymentDateTime().toLocalDate().toString() +
                        "  " +
                        getPaymentDateTime().toLocalTime().toString());
    }


    public String getInOutValue() {
        if (isInComePayment) {
            return "+";
        } else {
            return "-";
        }
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getBigDecimalAmount() {
        return new BigDecimal(getAmount());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return isInComePayment == payment.isInComePayment &&
                Objects.equals(id, payment.id) &&
                Objects.equals(paymentDateTime, payment.paymentDateTime) &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(finalBalance, payment.finalBalance) &&
                Objects.equals(paymentClient, payment.paymentClient) &&
                Objects.equals(comment, payment.comment) &&
                Objects.equals(paymentArticle, payment.paymentArticle) &&
                Objects.equals(employee, payment.employee) &&
                Objects.equals(order, payment.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentDateTime, amount, finalBalance, paymentClient, comment, paymentArticle, employee, isInComePayment, order);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentDateTime=" + paymentDateTime +
                ", amount='" + amount + '\'' +
                ", finalBalance='" + finalBalance + '\'' +
                ", paymentClient=" + paymentClient +
                ", comment='" + comment + '\'' +
                ", paymentArticle=" + paymentArticle +
                ", employee=" + employee +
                ", isInComePayment=" + isInComePayment +
                ", order=" + order +
                '}';
    }
}