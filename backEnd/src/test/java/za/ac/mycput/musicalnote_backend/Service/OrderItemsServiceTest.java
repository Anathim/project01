package za.ac.mycput.musicalnote_backend.Service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Repository.OrderItemsRepository;

public class OrderItemsServiceTest {

    @InjectMocks
    private OrderItemsService orderItemsService;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    private OrderItems testOrderItem;
    private Product testProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a test product
        testProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        testProduct.setProductId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0f);

        // Setup a test OrderItems
        testOrderItem = new OrderItems();
        testOrderItem.setOrderItems_id(1L);
        testOrderItem.setProduct(testProduct);
        testOrderItem.setQuantity(5);
    }

    @Test
    public void testCreateOrderItem_Success() {
        // Arrange
        when(orderItemsRepository.save(testOrderItem)).thenReturn(testOrderItem);

        // Act
        OrderItems result = orderItemsService.createOrderItem(testOrderItem);

        // Assert
        assertNotNull(result, "OrderItems should not be null");
        assertEquals(testOrderItem.getOrderItems_id(), result.getOrderItems_id(), "Item ID should match");
        assertEquals(testOrderItem.getProduct(), result.getProduct(), "Product should match");
        assertEquals(testOrderItem.getQuantity(), result.getQuantity(), "Quantity should match");
        verify(orderItemsRepository, times(1)).save(testOrderItem);
    }

    @Test
    public void testGetOrderItemById_Success() {
        // Arrange
        when(orderItemsRepository.findById(1L)).thenReturn(Optional.of(testOrderItem));

        // Act
        OrderItems result = orderItemsService.getOrderItemById(1L);

        // Assert
        assertNotNull(result, "OrderItems should not be null");
        assertEquals(testOrderItem.getOrderItems_id(), result.getOrderItems_id(), "Item ID should match");
    }

    @Test
    public void testGetOrderItemById_NotFound() {
        // Arrange
        when(orderItemsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            orderItemsService.getOrderItemById(1L);
        });
        assertEquals("Order item with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testUpdateOrderItem_Success() {
        // Arrange
        testOrderItem.setQuantity(10);
        when(orderItemsRepository.findById(1L)).thenReturn(Optional.of(testOrderItem));
        when(orderItemsRepository.save(testOrderItem)).thenReturn(testOrderItem);

        // Act
        OrderItems updatedOrderItem = new OrderItems();
        updatedOrderItem.setOrderItems_id(1L);
        updatedOrderItem.setProduct(testProduct);
        updatedOrderItem.setQuantity(10);

        OrderItems result = orderItemsService.updateOrderItem(1L, updatedOrderItem);

        // Assert
        assertNotNull(result, "OrderItems should not be null");
        assertEquals(10, result.getQuantity(), "Quantity should match");
        verify(orderItemsRepository, times(1)).save(updatedOrderItem);
    }

    @Test
    public void testDeleteOrderItem_Success() {
        // Arrange
        when(orderItemsRepository.findById(1L)).thenReturn(Optional.of(testOrderItem));

        // Act
        orderItemsService.deleteOrderItem(1L);

        // Assert
        verify(orderItemsRepository, times(1)).deleteById(1L);
    }
}
