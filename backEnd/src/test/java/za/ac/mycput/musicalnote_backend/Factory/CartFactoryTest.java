package za.ac.mycput.musicalnote_backend.Factory;

import org.junit.jupiter.api.Test;
import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartFactoryTest {

    @Test
    void testBuildCart_ValidInput() {

        Long cartId = 1L;
        User user = new User();
        List<OrderItems> orderItems = Collections.singletonList(new OrderItems());
        float totalPrice = 100.0f;
        Date createdDate = new Date();
        String status = "ACTIVE";

        Cart cart = CartFactory.buildCart(cartId, user, orderItems, totalPrice, createdDate, status);

        assertNotNull(cart, "Cart should not be null");
        assertEquals(cartId, cart.getCartId(), "Cart ID should match");
        assertEquals(user, cart.getUser(), "User should match");
        assertEquals(orderItems, cart.getOrderItems(), "Order items should match");
        assertEquals(totalPrice, cart.getTotalPrice(), "Total price should match");
        assertEquals(createdDate, cart.getCreatedDate(), "Created date should match");
        assertEquals(status, cart.getStatus(), "Status should match");
    }

    @Test
    void testBuildCart_InvalidInput() {

        Long cartId = null;
        User user = null;
        List<OrderItems> orderItems = null;
        float totalPrice = -1.0f;
        Date createdDate = null;
        String status = null;

        Cart cart = CartFactory.buildCart(cartId, user, orderItems, totalPrice, createdDate, status);

        assertNull(cart, "Cart should be null for invalid input");
    }
}
