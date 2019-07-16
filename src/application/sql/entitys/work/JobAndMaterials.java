package application.sql.entitys.work;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job_and_materials")
public class JobAndMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "job_id")
    private Job job;

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

    @ManyToMany(mappedBy = "jobAndMaterialsList")
    private List<Order> orderList;

    public JobAndMaterials() {
        if(this.costPrice == null) {
            this.costPrice = "0";
        }
        if(this.discount == null) {
            this.discount = "0";
        }
        if(this.numberOf == null) {
            this.numberOf = 1;
        }
    }

    public JobAndMaterials(Job job, Employee doer) {
        this();
        this.job = job;
        this.doer = doer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
        JobAndMaterials that = (JobAndMaterials) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(job, that.job) &&
                Objects.equals(costPrice, that.costPrice) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(doer, that.doer) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(numberOf, that.numberOf) &&
                Objects.equals(orderList, that.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, job, costPrice, discount, doer, comment, numberOf, orderList);
    }

    @Override
    public String toString() {
        return "JobAndMaterials{" +
                "id=" + id +
                ", job=" + job +
                ", costPrice='" + costPrice + '\'' +
                ", discount='" + discount + '\'' +
                ", doer=" + doer +
                ", comment='" + comment + '\'' +
                ", numberOf=" + numberOf +
                '}';
    }
}
