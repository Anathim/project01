package za.ac.mycput.musicalnote_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Service.UserService;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

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
    public void testRegisterUser_Success() throws Exception {

        when(userService.registerUser(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void testRegisterUser_UsernameExists() throws Exception {

        when(userService.registerUser(any(User.class))).thenThrow(new IllegalArgumentException("username: testuser already exists. Cannot create user"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("username: testuser already exists. Cannot create user"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {

        when(userService.loginUser("testuser", "password123")).thenReturn(true);

        mockMvc.perform(post("/api/users/login")
                        .param("username", "testuser")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    public void testLoginUser_Failure() throws Exception {

        when(userService.loginUser("testuser", "wrongPassword")).thenReturn(false);

        mockMvc.perform(post("/api/users/login")
                        .param("username", "testuser")
                        .param("password", "wrongPassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {

        when(userService.getById(anyLong())).thenReturn(testUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {

        when(userService.getById(anyLong())).thenThrow(new NoSuchElementException("User not found"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUserProfile_Success() throws Exception {

        when(userService.updateUserProfile(anyLong(), any(User.class))).thenReturn(testUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));
    }

    @Test
    public void testUpdateUserProfile_UsernameExists() throws Exception {

        when(userService.updateUserProfile(anyLong(), any(User.class)))
                .thenThrow(new IllegalArgumentException("username: existinguser already exists. Cannot update user"));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("username: existinguser already exists. Cannot update user"));
    }

    @Test
    public void testGetAllOrders() throws Exception {

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(userService.getAllOrders(anyLong())).thenReturn(orders);

        mockMvc.perform(get("/api/users/1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orders.size()));
    }
}

