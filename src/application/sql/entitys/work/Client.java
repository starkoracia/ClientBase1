package application.sql.entitys.work;

import javafx.beans.property.SimpleStringProperty;
import jdk.nashorn.internal.ir.LiteralNode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "find_recommendation")
    private String findRecommendation;
    @Column(name = "annotation")
    private String annotation;

    @OneToMany(mappedBy = "paymentClient",targetEntity = Payment.class, fetch = FetchType.LAZY)
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "client", targetEntity = Order.class, fetch = FetchType.LAZY)
    private List<Order> orderList;

    public Client(String name, String mobileNumber, String email, String findRecommendation, String annotation) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.findRecommendation = findRecommendation;
        this.annotation = annotation;
        paymentList = new ArrayList<>();
        orderList = new ArrayList<>();
    }

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFindRecommendation() {
        return findRecommendation;
    }

    public void setFindRecommendation(String findRecommendation) {
        this.findRecommendation = findRecommendation;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public void addPayment(Payment payment) {
        this.paymentList.add(payment);
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public SimpleStringProperty mobileNumberProperty() {
        return new SimpleStringProperty(mobileNumber);
    }

    public SimpleStringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }

    public SimpleStringProperty findRecommendationProperty() {
        return new SimpleStringProperty(findRecommendation);
    }

    public SimpleStringProperty annotationProperty() {
        return new SimpleStringProperty(annotation);
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(name, client.name) &&
                Objects.equals(mobileNumber, client.mobileNumber) &&
                Objects.equals(email, client.email) &&
                Objects.equals(findRecommendation, client.findRecommendation) &&
                Objects.equals(annotation, client.annotation) &&
                Objects.equals(paymentList, client.paymentList) &&
                Objects.equals(orderList, client.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mobileNumber, email, findRecommendation, annotation, paymentList, orderList);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", findRecommendation='" + findRecommendation + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}