package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.util.Helper;

import java.util.List;
import java.util.Set;

public class UserFactory {
    public static User buildUser(Long userId, String username, String password, String email, String first_name, String last_name, String role, Set<Order> orders) {
        if (
                Helper.isNullOrEmpty(userId) ||
                        Helper.isNullOrEmpty(username) ||
                        Helper.isNullOrEmpty(password) ||
                        Helper.isNullOrEmpty(email) ||
                        Helper.isNullOrEmpty(first_name) ||
                        Helper.isNullOrEmpty(last_name) ||
                        Helper.isNullOrEmpty(role) ||
                        Helper.isNullOrEmpty(orders)) {
            return null;
        }
        return new User.Builder()
                .setUserId(userId)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setFirst_name(first_name)
                .setLast_name(last_name)
                .setRole(role)
                .setOrders(orders)
                .build();

    }
}
