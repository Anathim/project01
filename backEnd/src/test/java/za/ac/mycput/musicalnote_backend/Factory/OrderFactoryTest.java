package za.ac.mycput.musicalnote_backend.Factory;

import org.junit.jupiter.api.Test;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Factory.OrderFactory;
import za.ac.mycput.musicalnote_backend.util.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderFactoryTest {

    @Test
    public void testBuildOrderSuccess() {

        Long orderId = 1L;
        User user = new User();
        Date orderDate = new Date();
        String status = "Pending";
        List<OrderItems> orderItems = new ArrayList<>();

        Order order = OrderFactory.buildOrder(orderId, user, orderDate, status, orderItems);

        assertNotNull(order, "Order should not be null");
        assertEquals(orderId, order.getOrderId(), "Order ID should match");
        assertEquals(user, order.getUserId(), "User should match");
        assertEquals(orderDate, order.getOrderDate(), "Order date should match");
        assertEquals(status, order.getStatus(), "Status should match");
        assertEquals(orderItems, order.getOrderItems(), "Order items should match");
    }

    @Test
    public void testBuildOrderFailure() {
        Order order = OrderFactory.buildOrder(null, null, null, null, null);

        assertNull(order, "Order should be null due to invalid parameters");
    }
}

