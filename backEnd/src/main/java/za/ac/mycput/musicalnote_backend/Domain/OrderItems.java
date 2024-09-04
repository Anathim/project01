package za.ac.mycput.musicalnote_backend.Domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Order_Items")
public class OrderItems implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderItems_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private float price;

    public OrderItems() {}

    public OrderItems(Builder builder){

        this.orderItems_id = builder.orderItems_id;
        this.order = builder.order;
        this.product = builder.product;
        this.cart = builder.cart;
        this.quantity = builder.quantity;
        this.price = builder.price;


    }

    public Long getOrderItems_id() {
        return orderItems_id;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }
    public Cart getCart() {
        return cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setOrderItems_id(Long orderItems_id) {
        this.orderItems_id = orderItems_id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItems that = (OrderItems) o;
        return quantity == that.quantity && Float.compare(price, that.price) == 0 && Objects.equals(orderItems_id, that.orderItems_id) && Objects.equals(order, that.order) && Objects.equals(product, that.product) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItems_id, order, product, cart, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItems_id=" + orderItems_id +
                ", order=" + order +
                ", product=" + product +
                ", cart=" + cart +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private Long orderItems_id;
        private Order order;
        private Product product;
        private Cart cart;
        private int quantity;
        private float price;

        public Builder orderItems_id(Long orderItems_id) {
            this.orderItems_id = orderItems_id;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }
        public Builder cart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;

        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder copy(OrderItems orderItems){
            this.orderItems_id = orderItems.orderItems_id;
            this.order = orderItems.order;
            this.product = orderItems.product;
            this.cart = orderItems.cart;
            this.quantity = orderItems.quantity;
            this.price = orderItems.price;
            return this;

        }


        public OrderItems build() {
            return new OrderItems(this);


        }


    }
}

