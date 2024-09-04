package za.ac.mycput.musicalnote_backend.Domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Carts")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    @Column(name = "total_price", nullable = false)
    private float totalPrice;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "status", nullable = false)
    private String status;

    public Cart() {}

    public Cart(Builder builder) {
        this.cartId = builder.cartId;
        this.user = builder.user;
        this.orderItems = builder.orderItems;
        this.totalPrice = builder.totalPrice;
        this.createdDate = builder.createdDate;
        this.status = builder.status;
    }

    public Long getCartId() {
        return cartId;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Float.compare(cart.totalPrice, totalPrice) == 0 && Objects.equals(cartId, cart.cartId) && Objects.equals(user, cart.user) && Objects.equals(orderItems, cart.orderItems) && Objects.equals(createdDate, cart.createdDate) && Objects.equals(status, cart.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, user, orderItems, totalPrice, createdDate, status);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user +
                ", orderItems=" + orderItems +
                ", totalPrice=" + totalPrice +
                ", createdDate=" + createdDate +
                ", status='" + status + '\'' +
                '}';
    }

    public List<Product> getProducts() {
        return null;
    }

    public void setProducts(ArrayList<Product> products) {
    }

    public static class Builder {
        private Long cartId;
        private User user;
        private List<OrderItems> orderItems;
        private float totalPrice;
        private Date createdDate;
        private String status;

        public Builder setCartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setOrderItems(List<OrderItems> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder setTotalPrice(float totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Cart cart) {
            this.cartId = cart.getCartId();
            this.user = cart.getUser();
            this.orderItems = cart.getOrderItems();
            this.totalPrice = cart.getTotalPrice();
            this.createdDate = cart.getCreatedDate();
            this.status = cart.getStatus();
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }
}
