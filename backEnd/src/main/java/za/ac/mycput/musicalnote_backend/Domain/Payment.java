package za.ac.mycput.musicalnote_backend.Domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "status", nullable = false)
    private String status;

    public Payment() {}

    public Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.order = builder.order;
        this.paymentDate = builder.paymentDate;
        this.amount = builder.amount;
        this.paymentMethod = builder.paymentMethod;
        this.status = builder.status;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public float getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Float.compare(payment.amount, amount) == 0 && Objects.equals(paymentId, payment.paymentId) && Objects.equals(order, payment.order) && Objects.equals(paymentDate, payment.paymentDate) && Objects.equals(paymentMethod, payment.paymentMethod) && Objects.equals(status, payment.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, order, paymentDate, amount, paymentMethod, status);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", order=" + order +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {
        private Long paymentId;
        private Order order;
        private Date paymentDate;
        private float amount;
        private String paymentMethod;
        private String status;

        public Builder setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setPaymentDate(Date paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setAmount(float amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentId = payment.getPaymentId();
            this.order = payment.getOrder();
            this.paymentDate = payment.getPaymentDate();
            this.amount = payment.getAmount();
            this.paymentMethod = payment.getPaymentMethod();
            this.status = payment.getStatus();
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}

