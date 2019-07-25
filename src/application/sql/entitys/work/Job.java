package application.sql.entitys.work;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private String amount;

    @Column(name = "warranty")
    private Integer warranty;

    @OneToMany(mappedBy = "job", targetEntity = JobAndMaterials.class, fetch = FetchType.LAZY)
    private List<JobAndMaterials> jobAndMaterialsList;

    public Job() {
        initFields();
    }

    public Job(String name, String amount) {
        this();
        this.name = name;
        this.amount = amount;
    }

    private void initFields() {
        if (this.warranty == null) {
            this.warranty = 0;
        }
        if(this.amount == null) {
            this.amount = "0";
        }
        if (jobAndMaterialsList == null) {
            jobAndMaterialsList = new ArrayList<>();
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getWarranty() {
        return warranty;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    public List<JobAndMaterials> getJobAndMaterialsList() {
        return jobAndMaterialsList;
    }

    public void setJobAndMaterialsList(List<JobAndMaterials> jobAndMaterialsList) {
        this.jobAndMaterialsList = jobAndMaterialsList;
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(getName());
    }

    public SimpleStringProperty priceProperty() {
        return new SimpleStringProperty(getAmount());
    }

    public SimpleStringProperty nameAndAmountProperty() {
        Double distance = (100 - getName().length() * 1.9);
        String value = String.format("%s%" + distance.intValue() + "s грн.", getName(), getAmount());
        return new SimpleStringProperty(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id) &&
                Objects.equals(name, job.name) &&
                Objects.equals(amount, job.amount) &&
                Objects.equals(warranty, job.warranty) &&
                Objects.equals(jobAndMaterialsList, job.jobAndMaterialsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, warranty, jobAndMaterialsList);
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", warranty=" + warranty +
                '}';
    }
}
