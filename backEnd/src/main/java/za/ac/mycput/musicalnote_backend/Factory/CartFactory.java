package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.util.Helper;

import java.util.Date;
import java.util.List;

public class CartFactory {

    public static Cart buildCart(
            Long cartId,
            User user,
            List<OrderItems> orderItems,
            float totalPrice,
            Date createdDate,
            String status) {

        if (Helper.isNullOrEmpty(cartId) ||
                Helper.isNullOrEmpty(user) ||
                Helper.isNullOrEmpty(orderItems) ||
                totalPrice < 0.0f ||
                Helper.isNullOrEmpty(createdDate) ||
                Helper.isNullOrEmpty(status)) {
            return null;
        }

        return new Cart.Builder()
                .setCartId(cartId)
                .setUser(user)
                .setOrderItems(orderItems)
                .setTotalPrice(totalPrice)
                .setCreatedDate(createdDate)
                .setStatus(status)
                .build();
    }
}

