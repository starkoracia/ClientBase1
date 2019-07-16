package application.sql.entitys.work;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "payments_articles")
public class PaymentArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "payment_article")
    private String paymentArticle;
    @Column(name = "is_in_come_payment")
    private boolean isInComePayment;

    @OneToMany(mappedBy = "paymentArticle", targetEntity = Payment.class, fetch = FetchType.LAZY)
    private List<Payment> paymentList;

    public PaymentArticle(boolean isInComePayment, String paymentArticle) {
        this.paymentArticle = paymentArticle;
        this.isInComePayment = isInComePayment;
    }

    public PaymentArticle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentArticle() {
        return paymentArticle;
    }

    public void setPaymentArticle(String paymentArticle) {
        this.paymentArticle = paymentArticle;
    }

    public boolean isInComePayment() {
        return isInComePayment;
    }

    public void setInComePayment(boolean inComePayment) {
        isInComePayment = inComePayment;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentArticle that = (PaymentArticle) o;
        return isInComePayment == that.isInComePayment &&
                Objects.equals(id, that.id) &&
                Objects.equals(paymentArticle, that.paymentArticle) &&
                Objects.equals(paymentList, that.paymentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentArticle, isInComePayment, paymentList);
    }

    @Override
    public String toString() {
        return "PaymentArticle{" +
                "id=" + id +
                ", paymentArticle='" + paymentArticle + '\'' +
                ", isInComePayment=" + isInComePayment +
                '}';
    }
}
