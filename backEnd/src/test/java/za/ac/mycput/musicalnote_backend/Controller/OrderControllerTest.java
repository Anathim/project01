package za.ac.mycput.musicalnote_backend.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Service.OrderService;

import java.util.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order testOrder;
    private User testUser;
    private OrderItems testOrderItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUsername("testuser");

        testOrderItem = new OrderItems();
        testOrderItem.setOrderItems_id(1L);
        testOrderItem.setQuantity(1);

        testOrder = new Order();
        testOrder.setOrderId(1L);
        testOrder.setUserId(testUser);
        testOrder.setOrderDate(new Date());
        testOrder.setStatus("Pending");
        testOrder.setOrderItems(new ArrayList<>(List.of(testOrderItem)));
    }

    @Test
    public void testCreateOrder_Success() throws Exception {

        when(orderService.createOrder(any(Order.class))).thenReturn(testOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(testOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(testOrder.getOrderId()))
                .andExpect(jsonPath("$.status").value(testOrder.getStatus()));
    }

    @Test
    public void testGetOrderById_Success() throws Exception {

        when(orderService.getOrderById(1L)).thenReturn(testOrder);

        mockMvc.perform(get("/orders/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(testOrder.getOrderId()))
                .andExpect(jsonPath("$.status").value(testOrder.getStatus()));
    }

    @Test
    public void testGetOrderById_NotFound() throws Exception {

        when(orderService.getOrderById(1L)).thenThrow(new NoSuchElementException("Order with ID 1 not found"));

        mockMvc.perform(get("/orders/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Order with ID 1 not found"));
    }

    @Test
    public void testUpdateOrder_Success() throws Exception {

        Order updatedOrder = new Order();
        updatedOrder.setOrderId(1L);
        updatedOrder.setUserId(testUser);
        updatedOrder.setOrderDate(new Date());
        updatedOrder.setStatus("Shipped");
        updatedOrder.setOrderItems(new ArrayList<>(List.of(testOrderItem)));

        when(orderService.updateOrder(any(Order.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(updatedOrder.getStatus()));
    }

    @Test
    public void testDeleteOrder_Success() throws Exception {

        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
