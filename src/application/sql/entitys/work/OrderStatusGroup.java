package application.sql.entitys.work;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_status_groups")
public class OrderStatusGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group",targetEntity = OrderStatus.class, fetch = FetchType.LAZY)
    private List<OrderStatus> orderStatusList;

    public OrderStatusGroup() {
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

    public List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<OrderStatus> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatusGroup that = (OrderStatusGroup) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(orderStatusList, that.orderStatusList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, orderStatusList);
    }

    @Override
    public String toString() {
        return "OrderStatusGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
