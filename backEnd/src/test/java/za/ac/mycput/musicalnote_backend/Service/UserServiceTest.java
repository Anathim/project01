package za.ac.mycput.musicalnote_backend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a test user
        testUser = new User.Builder()
                .setUserId(1L)
                .setUsername("testuser")
                .setPassword("password123")
                .setEmail("test@example.com")
                .setFirst_name("John")
                .setLast_name("Doe")
                .setRole("USER")
                .setOrders(new HashSet<>())
                .build();
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");

        // Act
        boolean result = userService.registerUser(testUser);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testRegisterUser_UsernameExists() {
        // Arrange
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(testUser);
        });
        assertEquals("username: testuser already exists. Cannot create user", thrown.getMessage());
    }

    @Test
    public void testRegisterUser_EmailExists() {
        // Arrange
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(testUser);
        });
        assertEquals("Email address: test@example.com already exists. Cannot create user", thrown.getMessage());
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", testUser.getPassword())).thenReturn(true);

        // Act
        boolean result = userService.loginUser(testUser.getUsername(), "password123");

        // Assert
        assertTrue(result);
    }

    @Test
    public void testLoginUser_Failure() {
        // Arrange
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongPassword", testUser.getPassword())).thenReturn(false);

        // Act
        boolean result = userService.loginUser(testUser.getUsername(), "wrongPassword");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testFindExistingUsername_Success() {
        // Arrange
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.findExistingusername(testUser.getUsername());

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
    }

    @Test
    public void testFindExistingUsername_NotFound() {
        // Arrange
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            userService.findExistingusername(testUser.getUsername());
        });
        assertEquals("username does not exist", thrown.getMessage());
    }

    @Test
    public void testFindExistingEmail_Success() {
        // Arrange
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.findExistingEmail(testUser.getEmail());

        // Assert
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    public void testFindExistingEmail_NotFound() {
        // Arrange
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            userService.findExistingEmail(testUser.getEmail());
        });
        assertEquals("User with email test@example.com does not exist", thrown.getMessage());
    }

    @Test
    public void testUpdateUserProfile_Success() {
        // Arrange
        User updatedUser = new User.Builder()
                .setUserId(1L)
                .setUsername("updateduser")
                .setPassword("newpassword")
                .setEmail("updated@example.com")
                .setFirst_name("Jane")
                .setLast_name("Doe")
                .setRole("USER")
                .setOrders(new HashSet<>())
                .build();

        when(userRepository.getReferenceById(1L)).thenReturn(testUser);
        when(userRepository.existsByEmail(updatedUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(updatedUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(updatedUser.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUserProfile(1L, updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());
    }

    @Test
    public void testUpdateUserProfile_UsernameExists() {
        // Arrange
        User updatedUser = new User.Builder()
                .setUserId(1L)
                .setUsername("existinguser")
                .setPassword("newpassword")
                .setEmail("updated@example.com")
                .setFirst_name("Jane")
                .setLast_name("Doe")
                .setRole("USER")
                .setOrders(new HashSet<>())
                .build();

        when(userRepository.getReferenceById(1L)).thenReturn(testUser);
        when(userRepository.existsByEmail(updatedUser.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(updatedUser.getUsername())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserProfile(1L, updatedUser);
        });
        assertEquals("username: existinguser already exists. Cannot update user", thrown.getMessage());
    }

    @Test
    public void testUpdateUserProfile_EmailExists() {
        // Arrange
        User updatedUser = new User.Builder()
                .setUserId(1L)
                .setUsername("updateduser")
                .setPassword("newpassword")
                .setEmail("existing@example.com")
                .setFirst_name("Jane")
                .setLast_name("Doe")
                .setRole("USER")
                .setOrders(new HashSet<>())
                .build();

        when(userRepository.getReferenceById(1L)).thenReturn(testUser);
        when(userRepository.existsByEmail(updatedUser.getEmail())).thenReturn(true);
        when(userRepository.existsByUsername(updatedUser.getUsername())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserProfile(1L, updatedUser);
        });
        assertEquals("Email address: existing@example.com already exists. Cannot update user", thrown.getMessage());
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        orders.add(new Order()); // Add some orders for testing
        when(orderService.getOrdersByUserId(1L)).thenReturn(orders);

        // Act
        List<Order> result = userService.getAllOrders(1L);

        // Assert
        assertNotNull(result);
        assertEquals(orders.size(), result.size());
    }
}

