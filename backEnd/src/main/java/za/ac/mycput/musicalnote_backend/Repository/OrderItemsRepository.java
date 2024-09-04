package za.ac.mycput.musicalnote_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;

import java.util.List;
import java.util.Optional;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderId(Long orderId);



    List<OrderItems> findByOrderItemsId(Long orderItems_Id);

    List<OrderItems>findAllByOrderId(Long orderId);
}
