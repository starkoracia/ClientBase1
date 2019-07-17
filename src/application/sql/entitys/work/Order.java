package application.sql.entitys.work;

import application.sql.daos.OrderStatusDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "model")
    private String model;

    @Column(name = "malfunction")
    private String malfunction;

    @Column(name = "appearance")
    private String appearance;

    @Column(name = "equipment")
    private String equipment;

    @Column(name = "acceptor_note")
    private String acceptorNote;

    @Column(name = "estimated_price")
    private String estimatedPrice;

    @Column(name = "quickly")
    private Boolean quickly;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "prepayment")
    private String prepayment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doer_id")
    private Employee doer;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "orders_job_and_materials",
            joinColumns = { @JoinColumn(name = "orders_id")},
            inverseJoinColumns = { @JoinColumn(name = "job_and_materials_id")}
    )
    private List<JobAndMaterials> jobAndMaterialsList;

    @Column(name = "doer_note")
    private String doerNote;

    @Column(name = "recommendation")
    private String recommendation;

    @OneToMany(mappedBy = "order", targetEntity = Payment.class, fetch = FetchType.LAZY)
    private List<Payment> paymentList;

    public Order() {
        initFields();
    }

    public Order(OrderStatus status, Boolean paid, Client client, LocalDateTime deadline) {
        this();
        this.status = status;
        this.paid = paid;
        this.client = client;
        this.deadline = deadline;
    }

    private void initFields() {
        if(this.estimatedPrice == null) {
            this.estimatedPrice = "0";
        }
        if(this.quickly == null) {
            this.quickly = false;
        }
        if(this.prepayment == null) {
            this.prepayment = "0";
        }
        if(jobAndMaterialsList == null) {
            jobAndMaterialsList = new ArrayList<>();
        }
        if(paymentList == null) {
            paymentList = new ArrayList<>();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMalfunction() {
        return malfunction;
    }

    public void setMalfunction(String malfunction) {
        this.malfunction = malfunction;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getAcceptorNote() {
        return acceptorNote;
    }

    public void setAcceptorNote(String acceptorNote) {
        this.acceptorNote = acceptorNote;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Boolean getQuickly() {
        return quickly;
    }

    public void setQuickly(Boolean quickly) {
        this.quickly = quickly;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Employee getDoer() {
        return doer;
    }

    public void setDoer(Employee doer) {
        this.doer = doer;
    }

    public List<JobAndMaterials> getJobAndMaterialsList() {
        return jobAndMaterialsList;
    }

    public void setJobAndMaterialsList(List<JobAndMaterials> jobAndMaterialsList) {
        this.jobAndMaterialsList = jobAndMaterialsList;
    }

    public String getDoerNote() {
        return doerNote;
    }

    public void setDoerNote(String doerNote) {
        this.doerNote = doerNote;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public String getOrderNumber() {
        return "A" + id.toString();
    }

    public BigDecimal getBigDecimalAmount() {
        BigDecimal tempBalance = new BigDecimal("0");
        for(Payment payment : getPaymentList()) {
            tempBalance = tempBalance.add(payment.getBigDecimalAmount());
        }
        return tempBalance;
    }

    public String getAmount() {
        return getBigDecimalAmount().toString();
    }

    public SimpleStringProperty orderNumberProperty() {
        return new SimpleStringProperty(getOrderNumber());
    }

    public SimpleStringProperty statusProperty() {
        return new SimpleStringProperty(status.getName());
    }

    public SimpleStringProperty deadlineProperty() {
        return new SimpleStringProperty(String.format("%s%n%s",
                        getDeadline().toLocalDate().toString(),
                        getDeadline().toLocalTime().toString()));
    }

    public SimpleStringProperty productTypeProperty() {
        return new SimpleStringProperty(getProductType());
    }

    public SimpleStringProperty modelProperty() {
        return new SimpleStringProperty(getModel());
    }

    public SimpleStringProperty malfunctionProperty() {
        return new SimpleStringProperty(getMalfunction());
    }

    public SimpleStringProperty clientProperty() {
        return new SimpleStringProperty(String.format("%s%n%s",
                getClient().getName(),
                getClient().getMobileNumber()));
    }

    public SimpleStringProperty amountProperty() {
        return new SimpleStringProperty(getAmount());
    }

    public SimpleStringProperty doerNoteProperty() {
        return new SimpleStringProperty(getDoerNote());
    }

    public SimpleStringProperty deadlineDifferenceProperty() {
        return new SimpleStringProperty(Long.toString(DAYS.between(LocalDateTime.now(), getDeadline())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(status, order.status) &&
                Objects.equals(paid, order.paid) &&
                Objects.equals(client, order.client) &&
                Objects.equals(productType, order.productType) &&
                Objects.equals(brandName, order.brandName) &&
                Objects.equals(model, order.model) &&
                Objects.equals(malfunction, order.malfunction) &&
                Objects.equals(appearance, order.appearance) &&
                Objects.equals(equipment, order.equipment) &&
                Objects.equals(acceptorNote, order.acceptorNote) &&
                Objects.equals(estimatedPrice, order.estimatedPrice) &&
                Objects.equals(quickly, order.quickly) &&
                Objects.equals(deadline, order.deadline) &&
                Objects.equals(prepayment, order.prepayment) &&
                Objects.equals(manager, order.manager) &&
                Objects.equals(doer, order.doer) &&
                Objects.equals(jobAndMaterialsList, order.jobAndMaterialsList) &&
                Objects.equals(doerNote, order.doerNote) &&
                Objects.equals(recommendation, order.recommendation) &&
                Objects.equals(paymentList, order.paymentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, paid, client, productType, brandName, model, malfunction, appearance, equipment, acceptorNote, estimatedPrice, quickly, deadline, prepayment, manager, doer, jobAndMaterialsList, doerNote, recommendation, paymentList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber=" + getOrderNumber() +
                ", status=" + status +
                ", paid=" + paid +
                ", client=" + client +
                ", productType='" + productType + '\'' +
                ", brandName='" + brandName + '\'' +
                ", model='" + model + '\'' +
                ", malfunction='" + malfunction + '\'' +
                ", appearance='" + appearance + '\'' +
                ", equipment='" + equipment + '\'' +
                ", acceptorNote='" + acceptorNote + '\'' +
                ", estimatedPrice='" + estimatedPrice + '\'' +
                ", quickly=" + quickly +
                ", deadline=" + deadline +
                ", prepayment='" + prepayment + '\'' +
                ", manager=" + manager +
                ", doer=" + doer +
                ", doerNote='" + doerNote + '\'' +
                ", recommendation='" + recommendation + '\'' +
                '}';
    }

}
