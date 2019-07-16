package application.sql.entitys.work;

import application.enums.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "login")
    private String login;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "post")
    @Enumerated(EnumType.ORDINAL)
    private Post post;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY, targetEntity = Payment.class)
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "doer", targetEntity = JobAndMaterials.class, fetch = FetchType.LAZY)
    private List<JobAndMaterials> jobAndMaterialsList;

    @OneToMany(mappedBy = "manager", targetEntity = Order.class, fetch = FetchType.LAZY)
    private List<Order> orderListByManager;

    @OneToMany(mappedBy = "doer", targetEntity = Order.class, fetch = FetchType.LAZY)
    private List<Order> orderListByDoer;

    public Employee(String name, String login, String email, String mobile, Post post) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.mobile = mobile;
        this.post = post;
        paymentList = new ArrayList<>();
    }

    public Employee() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<JobAndMaterials> getJobAndMaterialsList() {
        return jobAndMaterialsList;
    }

    public void setJobAndMaterialsList(List<JobAndMaterials> jobAndMaterialsList) {
        this.jobAndMaterialsList = jobAndMaterialsList;
    }

    public List<Order> getOrderListByManager() {
        return orderListByManager;
    }

    public void setOrderListByManager(List<Order> orderListByManager) {
        this.orderListByManager = orderListByManager;
    }

    public List<Order> getOrderListByDoer() {
        return orderListByDoer;
    }

    public void setOrderListByDoer(List<Order> orderListByDoer) {
        this.orderListByDoer = orderListByDoer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(login, employee.login) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(mobile, employee.mobile) &&
                post == employee.post &&
                Objects.equals(paymentList, employee.paymentList) &&
                Objects.equals(jobAndMaterialsList, employee.jobAndMaterialsList) &&
                Objects.equals(orderListByManager, employee.orderListByManager) &&
                Objects.equals(orderListByDoer, employee.orderListByDoer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, mobile, post, paymentList, jobAndMaterialsList, orderListByManager, orderListByDoer);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", post=" + post +
                '}';
    }
}

