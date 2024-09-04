package za.ac.mycput.musicalnote_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.mycput.musicalnote_backend.Domain.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find a Payment by its ID
    Optional<Payment> findById(Long paymentId);

    // Find Payments by Order ID
    List<Payment> findByOrder_OrderId(Long orderId);

    // Find Payments by Payment Method
    List<Payment> findByPaymentMethod(String paymentMethod);

    // Find Payments by Status
    List<Payment> findByStatus(String status);

}

