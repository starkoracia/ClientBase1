package application.sql.entitys.work;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "warranty")
    private Integer warranty;

    public Job() {
        initFields();
    }

    public Job(String name, String price) {
        this();
        this.name = name;
        this.price = price;
    }

    private void initFields() {
        if (this.warranty == null) {
            this.warranty = 0;
        }
        if(this.price == null) {
            this.price = "0";
        }
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

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }

    public SimpleStringProperty priceProperty() {
        return new SimpleStringProperty(getPrice());
    }

    public SimpleStringProperty nameAndAmountProperty() {
        Double distance = (100 - getName().length() * 1.9);
        String value = String.format("%s%" + distance.intValue() + "s грн.", getName(), getPrice());
        return new SimpleStringProperty(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id) &&
                Objects.equals(name, job.name) &&
                Objects.equals(price, job.price) &&
                Objects.equals(warranty, job.warranty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, warranty);
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount='" + price + '\'' +
                ", warranty=" + warranty +
                '}';
    }
}
