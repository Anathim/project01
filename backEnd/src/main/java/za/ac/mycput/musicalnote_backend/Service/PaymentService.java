package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.Payment;
import za.ac.mycput.musicalnote_backend.Repository.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {

        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }


        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }


        if (payment.getPaymentDate() == null) {
            throw new IllegalArgumentException("Payment date cannot be null");
        }


        if (payment.getOrder() == null || payment.getOrder().getOrderId() == null) {
            throw new IllegalArgumentException("Payment must be associated with a valid order");
        }

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrder_OrderId(orderId);
    }

    public List<Payment> getPaymentsByPaymentMethod(String paymentMethod) {
        return paymentRepository.findByPaymentMethod(paymentMethod);
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Payment payment) {
        if (!paymentRepository.existsById(payment.getPaymentId())) {
            throw new RuntimeException("Payment not found with id: " + payment.getPaymentId());
        }
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}

