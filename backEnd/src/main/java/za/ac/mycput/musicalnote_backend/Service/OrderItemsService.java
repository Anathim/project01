package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Repository.OrderItemsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderItemsService {

    @Autowired
    private final OrderItemsRepository orderItemsRepository;
    private final UserService userService;

    public OrderItems createOrderItem(OrderItems orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("OrderItem cannot be null");
        }
        return orderItemsRepository.save(orderItem);
    }

    public OrderItemsService(OrderItemsRepository orderItemsRepository, UserService userService) {
        this.orderItemsRepository = orderItemsRepository;
        this.userService = userService;
    }

    public List<OrderItems> getAllOrderItemsByOrderId(Long orderId) {
        return orderItemsRepository.findByOrderId(orderId);
    }


    public OrderItems getByOrderId(Long orderId) {
        return orderItemsRepository.findByOrderId(orderId).stream()
                .findFirst() // Gets the first item from the list
                .orElseThrow(() -> new RuntimeException("Order item not found with order id: " + orderId));
    }

    public OrderItems addOrderItem(OrderItems orderItem) {
        return orderItemsRepository.save(orderItem);
    }
    public OrderItems updateOrderItem(long l, OrderItems orderItem) {
        OrderItems existingOrderItem = orderItemsRepository.findById(orderItem.getOrderItems_id())
                .orElseThrow(() -> new RuntimeException("Order item not found with id: " + orderItem.getOrderItems_id()));

        // Update fields accordingly
        existingOrderItem.setOrder(orderItem.getOrder()); // Update based on the correct field names
        existingOrderItem.setProduct(orderItem.getProduct()); // Assuming OrderItems has a Product field
        existingOrderItem.setQuantity(orderItem.getQuantity()); // Assuming OrderItems has a Quantity field

        return orderItemsRepository.save(existingOrderItem);
    }

    public OrderItems getOrderItemById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<OrderItems> orderItemOptional = orderItemsRepository.findById(id);
        if (orderItemOptional.isPresent()) {
            return orderItemOptional.get();
        } else {
            throw new NoSuchElementException("Order item with ID " + id + " not found");
        }
    }

    public Boolean deleteOrderItem(Long id) {
        orderItemsRepository.deleteById(id);
        return true;
    }
}