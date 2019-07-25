package application.sql.entitys.work;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job_and_materials")
public class JobAndMaterials implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "warranty")
    private Integer warranty;

    @Column(name = "cost_price")
    private String costPrice;

    @Column(name = "discount")
    private String discount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doer")
    private Employee doer;

    @Column(name = "comment")
    private String comment;

    @Column(name = "number_of")
    private Integer numberOf;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "jobAndMaterialsList")
    private List<Order> orderList;

    public JobAndMaterials() {
        initFields();
    }

    public JobAndMaterials(Job job, Employee doer) {
        this();
        this.name = job.getName();
        this.price = job.getPrice();
        this.warranty = job.getWarranty();
        this.doer = doer;
    }

    public JobAndMaterials(String name, String price, Employee doer) {
        this();
        this.name = name;
        this.price = price;
        this.doer = doer;
    }

    private void initFields() {
        if(this.costPrice == null) {
            this.costPrice = "0";
        }
        if(this.discount == null) {
            this.discount = "0";
        }
        if(this.numberOf == null) {
            this.numberOf = 1;
        }
        if(this.orderList == null) {
            this.orderList = new ArrayList<>();
        }
        if(this.warranty == null) {
            this.warranty = 0;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Employee getDoer() {
        return doer;
    }

    public void setDoer(Employee doer) {
        this.doer = doer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(Integer numberOf) {
        this.numberOf = numberOf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getWarranty() {
        return warranty;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public BigDecimal getBigDecimalAmount() {
        return new BigDecimal(amountProperty().getValue());
    }

    public BigDecimal getBigDecimalDiscount() {
        return new BigDecimal(discount);
    }

    public SimpleIntegerProperty numberOfProperty() {
        return new SimpleIntegerProperty(getNumberOf());
    }

    public SimpleStringProperty amountProperty() {
        BigDecimal price = new BigDecimal(getPrice());
        BigDecimal amount = price.multiply(new BigDecimal(getNumberOf()));
        return new SimpleStringProperty(amount.toString());
    }

    public SimpleStringProperty discountProperty() {
        return new SimpleStringProperty(getDiscount());
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }

    public SimpleStringProperty priceProperty() {
        return new SimpleStringProperty(getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobAndMaterials that = (JobAndMaterials) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(warranty, that.warranty) &&
                Objects.equals(costPrice, that.costPrice) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(doer, that.doer) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(numberOf, that.numberOf) &&
                Objects.equals(orderList, that.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, warranty, costPrice, discount, doer, comment, numberOf, orderList);
    }

    @Override
    public String toString() {
        return "JobAndMaterials{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", warranty=" + warranty +
                ", costPrice='" + costPrice + '\'' +
                ", discount='" + discount + '\'' +
                ", doer=" + doer +
                ", comment='" + comment + '\'' +
                ", numberOf=" + numberOf +
                ", orderList=" + orderList +
                '}';
    }
}
