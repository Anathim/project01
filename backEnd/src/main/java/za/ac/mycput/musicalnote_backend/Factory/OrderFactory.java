package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.util.Helper;

import java.util.Date;
import java.util.List;

public class OrderFactory {
    public static Order buildOrder(Long orderId, User userId, Date orderDate, String status, List<OrderItems> orderItems ) {
        if(Helper.isNullOrEmpty(orderId) ||
           Helper.isNullOrEmpty(userId) ||
                   Helper.isNullOrEmpty(orderDate) ||
                   Helper.isNullOrEmpty(status) ||
                   Helper.isNullOrEmpty(orderItems)
                   ) return null;

        return new Order.Builder()
                .setOrderId(orderId)
                .setUserId(userId)
                .setOrderDate(orderDate)
                .setStatus(status)
                .setOrderItems(orderItems)
                .build();
    }

    }
