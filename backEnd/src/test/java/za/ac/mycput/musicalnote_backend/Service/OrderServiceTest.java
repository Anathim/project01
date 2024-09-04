package za.ac.mycput.musicalnote_backend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Repository.OrderRepository;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private Order testOrder;
    private User testUser;
    private OrderItems testOrderItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a test user
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testuser");

        // Setup a test order item
        testOrderItem = new OrderItems();
        testOrderItem.setOrderItems_id(1L);
        testOrderItem.setQuantity(1);

        // Setup a test order
        testOrder = new Order();
        testOrder.setOrderId(1L);
        testOrder.setUserId(testUser);
        testOrder.setOrderDate(new Date());
        testOrder.setStatus("Pending");
        testOrder.setOrderItems(new ArrayList<>(List.of(testOrderItem)));
    }

    @Test
    public void testCreateOrder_Success() {
        // Arrange
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        // Act
        Order result = orderService.createOrder(testOrder);

        // Assert
        assertNotNull(result);
        assertEquals(testOrder.getOrderId(), result.getOrderId());
        assertEquals(testOrder.getUserId(), result.getUserId());
        assertEquals(testOrder.getOrderDate(), result.getOrderDate());
        assertEquals(testOrder.getStatus(), result.getStatus());
        assertEquals(testOrder.getOrderItems(), result.getOrderItems());
        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    public void testGetOrderById_Success() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act
        Order result = orderService.getOrderById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testOrder.getOrderId(), result.getOrderId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrderById_NotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            orderService.getOrderById(1L);
        });
        assertEquals("Order with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testUpdateOrder_Success() {
        // Arrange
        Order updatedOrder = new Order();
        updatedOrder.setOrderId(1L);
        updatedOrder.setUserId(testUser);
        updatedOrder.setOrderDate(new Date());
        updatedOrder.setStatus("Shipped");
        updatedOrder.setOrderItems(new ArrayList<>(List.of(testOrderItem)));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        // Act
        Order result = orderService.updateOrder(updatedOrder);

        // Assert
        assertNotNull(result);
        assertEquals("Shipped", result.getStatus());
        verify(orderRepository, times(1)).save(updatedOrder);
    }

    @Test
    public void testUpdateOrder_NotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            orderService.getOrderById(1L);
        });
        assertEquals("Order with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testDeleteOrder_Success() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        doNothing().when(orderRepository).deleteById(1L);

        // Act
        orderService.deleteOrder(1L);

        // Assert
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            orderService.deleteOrder(1L);
        });
        assertEquals("Order with ID 1 not found", thrown.getMessage());
    }
}
