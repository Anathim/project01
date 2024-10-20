package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Repository.OrderItemsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;
    private final UserService userService;

    @Autowired
    public OrderItemsService(OrderItemsRepository orderItemsRepository, UserService userService) {
        this.orderItemsRepository = orderItemsRepository;
        this.userService = userService;
    }

    public OrderItems createOrderItem(OrderItems orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("OrderItem cannot be null");
        }
        return orderItemsRepository.save(orderItem);
    }

    public List<OrderItems> getAllOrderItemsByOrder(Order order) {
        return orderItemsRepository.findByOrder(order);
    }

    public OrderItems getByOrder(Order order) {
        return orderItemsRepository.findByOrder(order).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order item not found with order id: " + order));
    }

    public OrderItems addOrderItem(OrderItems orderItem) {
        return orderItemsRepository.save(orderItem);
    }

    public OrderItems updateOrderItem(long id, OrderItems orderItem) {
        OrderItems existingOrderItem = orderItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found with id: " + id));

        // Update fields accordingly
        existingOrderItem.setOrder(orderItem.getOrder());
        existingOrderItem.setProduct(orderItem.getProduct());
        existingOrderItem.setQuantity(orderItem.getQuantity());

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
        if (!orderItemsRepository.existsById(id)) {
            throw new RuntimeException("Order item not found with id: " + id);
        }
        orderItemsRepository.deleteById(id);
        return true;
    }
}