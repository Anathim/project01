package za.ac.mycput.musicalnote_backend.Domain;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    public Order() {
    }

    public Order(Builder builder){
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.orderItems = builder.orderItems;

    }

    public Long getOrderId() {
        return orderId;
    }


    public User getUserId() {
        return userId;

    }


    public Date getOrderDate() {
        return orderDate;
    }
    public String getStatus() {
        return status;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(userId, order.userId) && Objects.equals(orderDate, order.orderDate) && Objects.equals(status, order.status) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, orderDate, status, orderItems);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }


    public static class Builder {
        private Long orderId;
        private User userId;
        private Date orderDate;
        private String status;
        private List<OrderItems> orderItems;

        public Builder setOrderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setUserId(User userId) {
            this.userId = userId;
            return this;
        }

        public Builder setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;

        }

        public Builder setOrderItems(List<OrderItems> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder copy(Order order){
            this.orderId = order.getOrderId();
            this.userId = order.getUserId();
            this.orderDate = order.getOrderDate();
            this.status = order.getStatus();
            this.orderItems = order.getOrderItems();
            return this;

        }

        public Order build() {
            return new Order(this);

        }
    }
}

