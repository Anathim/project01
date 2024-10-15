package za.ac.mycput.musicalnote_backend.Domain;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Table(name = "Products")
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "category")
    private String category;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    private String imageUrl;

    public Product(String guitar, float v, String s) {}

    public Product(Builder builder){
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.category = builder.category;
        this.stock = builder.stock;
        this.orderItems = builder.orderItems;
        this.imageUrl= builder.imageUrl;
    }

    public Product() {

    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(price, product.price) == 0 && stock == product.stock && Objects.equals(productId, product.productId) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(orderItems, product.orderItems) && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, category, stock, orderItems, imageUrl);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", stock=" + stock +
                ", orderItems=" + orderItems +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public static class Builder {
        private Long productId;
        private String name;
        private String description;
        private float price;
        private String category;
        private int stock;
        private List<OrderItems> orderItems;
        private String imageUrl;

        public Builder setProductId(Long productId) {
            this.productId = productId;
            return this;

        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(float price) {
            this.price = price;
            return this;

        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setStock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder setOrderItems(List<OrderItems> orderItems) {
            this.orderItems = orderItems;
            return this;
        }
        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder copy(Product product) {
            this.productId = product.getProductId();
            this.name = product.getName();
            this.description = product.getDescription();
            this.price = product.getPrice();
            this.category = product.getCategory();
            this.stock = product.getStock();
            this.orderItems = product.getOrderItems();
            this.imageUrl = product.getImageUrl();
            return this;

        }

        public Product build() {
            return new Product(this);

        }
    }
}

