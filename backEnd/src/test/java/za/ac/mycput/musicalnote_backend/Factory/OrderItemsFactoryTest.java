package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Domain.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

public class OrderItemsFactoryTest {

    @Test
    public void testBuildOrderItems_ValidData() {
        Order order = new Order.Builder()
                .setOrderId(1L)
                .setUserId(new User())
                .setOrderDate(new Date())
                .setStatus("Pending")
                .setOrderItems(List.of())
                .build();

        Product product = new Product.Builder()
                .setProductId(1L)
                .setName("Guitar")
                .setPrice(100.0f)
                .build();

        OrderItems orderItems = OrderItemsFactory.buildOrderItems(
                1L,
                order,
                product,
                2,
                200.0f
        );

        assertNotNull(orderItems);
        assertEquals(1L, orderItems.getOrderItems_id());
        assertEquals(order, orderItems.getOrder());
        assertEquals(product, orderItems.getProduct());
        assertEquals(2, orderItems.getQuantity());
        assertEquals(200.0f, orderItems.getPrice());
    }

    @Test
    public void testBuildOrderItems_InvalidData() {
        assertNull(OrderItemsFactory.buildOrderItems(
                null,
                new Order(),
                new Product("Guitar", 299.00f, "/images/guitar.webp"),
                2,
                200.0f
        ));

        assertNull(OrderItemsFactory.buildOrderItems(
                1L,
                null,
                new Product("Guitar", 299.00f, "/images/guitar.webp"),
                2,
                200.0f
        ));

        assertNull(OrderItemsFactory.buildOrderItems(
                1L,
                new Order(),
                null,
                2,
                200.0f
        ));

        assertNull(OrderItemsFactory.buildOrderItems(
                1L,
                new Order(),
                new Product("Guitar", 299.00f, "/images/guitar.webp"),
                -1,
                200.0f
        ));

        assertNull(OrderItemsFactory.buildOrderItems(
                1L,
                new Order(),
                new Product("Guitar", 299.00f, "/images/guitar.webp"),
                2,
                -200.0f
        ));
    }
}

