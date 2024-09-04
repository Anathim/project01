package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.util.Helper;

public class OrderItemsFactory {
    public static OrderItems buildOrderItems(Long orderItems_id, Order order, Product product, int quantity , float price) {
        if(Helper.isNullOrEmpty(orderItems_id) ||
                Helper.isNullOrEmpty(order)||
                Helper.isNullOrEmpty(product) ||
                quantity <= 0 ||
                price < 0.0f
        )
            return null;
        return new OrderItems.Builder()
                .orderItems_id(orderItems_id)
                .order(order)
                .product(product)
                .quantity(quantity)
                .price(price)
                .build();

    }
}
