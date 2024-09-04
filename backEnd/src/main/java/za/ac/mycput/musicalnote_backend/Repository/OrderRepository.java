package za.ac.mycput.musicalnote_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.mycput.musicalnote_backend.Domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long orderId);

    List<Order>findAll();


    List<Order> findByUserId(long userId);

    List<Order> findByProductId(long productId);

}
