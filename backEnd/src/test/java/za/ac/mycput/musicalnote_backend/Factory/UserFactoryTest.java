package za.ac.mycput.musicalnote_backend.Factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserFactoryTest {

    private UserFactory userFactory;

    @BeforeEach
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void testBuildUser_ValidInputs() {

        Long userId = 1L;
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String role = "USER";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role,  orders);


        assertNotNull(user);
        assertEquals(userId, user.getUserId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirst_name());
        assertEquals(lastName, user.getLast_name());
        assertEquals(role, user.getRole());
        assertEquals(orders, user.getOrders());
    }

    @Test
    public void testBuildUser_InvalidUserId() {

        Long userId = null;
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String role = "USER";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }

    @Test
    public void testBuildUser_InvalidUsername() {

        Long userId = 1L;
        String username = "";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String role = "USER";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }

    @Test
    public void testBuildUser_InvalidEmail() {

        Long userId = 1L;
        String username = "testuser";
        String password = "password123";
        String email = "";
        String firstName = "John";
        String lastName = "Doe";
        String role = "USER";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }

    @Test
    public void testBuildUser_InvalidFirstName() {

        Long userId = 1L;
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "";
        String lastName = "Doe";
        String role = "USER";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }

    @Test
    public void testBuildUser_InvalidLastName() {

        Long userId = 1L;
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "";
        String role = "USER";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }

    @Test
    public void testBuildUser_InvalidRole() {

        Long userId = 1L;
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String role = "";
        Set<Order> orders = new HashSet<>();


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }

    @Test
    public void testBuildUser_InvalidOrders() {

        Long userId = 1L;
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String role = "USER";
        Set<Order> orders = null;


        User user = UserFactory.buildUser(userId, username, password, email, firstName, lastName, role, orders);


        assertNull(user);
    }
}
