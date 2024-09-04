package za.ac.mycput.musicalnote_backend.Domain;

import jakarta.persistence.*;
import org.springframework.data.domain.Sort;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "First Name", nullable = false)
    private String first_name;

    @Column(name = "Last Name", nullable = false)
    private String last_name;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders;

    public User() {
    }

    public User(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
        this.role = builder.role;
        this.orders = new HashSet<>();
       // this.orders = new ArrayList<>();


    }

    public Long getUserId() {
        return userId;
    }



    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRole() {
        return role;
    }
    public Set<Order> getOrders() {
        return orders;
    }


    /*public List<Order> getOrders() {
        return orders;
    }*/

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void resetId() {
        this.userId = null; // or any other logic to reset the ID
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

   /* public void setOrders(List<Order> orders) {
        this.orders = orders;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(first_name, user.first_name) && Objects.equals(last_name, user.last_name) && Objects.equals(role, user.role) && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, email, first_name, last_name, role, orders);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", role='" + role + '\'' +
                ", orders=" + orders +
                '}';
    }

    public static class Builder {
        private Long userId;
        private String username;
        private String password;
        private String email;
        private String first_name;
        private String last_name;
        private String role;
        private Set<Order> orders;
        //private List<Order> orders;

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;

        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;

        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;

        }

        public Builder setFirst_name(String first_name) {
            this.first_name = first_name;
            return this;

        }

        public Builder setLast_name(String last_name) {
            this.last_name = last_name;
            return this;

        }
        public Builder setRole(String role) {
            this.role = role;
            return this;
        }
        public Builder setOrders(Set<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Builder copy(User user){
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.email = user.getEmail();
            this.first_name = user.getFirst_name();
            this.last_name = user.getLast_name();
            this.role = user.getRole();
            this.orders = user.getOrders();
            return this;

        }

        public User build() {
            return new User(this);
        }

    }
}



