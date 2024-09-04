package za.ac.mycput.musicalnote_backend.Controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Service.OrderItemsService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderItemsController.class)
public class OrderItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemsService orderItemsService;

    private OrderItems testOrderItem;
    private Product testProduct;

    @BeforeEach
    public void setUp() {
        testProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        testProduct.setProductId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0f);

        testOrderItem = new OrderItems();
        testOrderItem.setOrderItems_id(1L);
        testOrderItem.setProduct(testProduct);
        testOrderItem.setQuantity(5);
    }

    @Test
    public void testCreateOrderItem_Success() throws Exception {
        when(orderItemsService.createOrderItem(any(OrderItems.class))).thenReturn(testOrderItem);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderItems_id\":1,\"product\":{\"productId\":1,\"name\":\"Test Product\",\"price\":100.0},\"quantity\":5,\"price\":200.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderItems_id").value(1L))
                .andExpect(jsonPath("$.product.name").value("Test Product"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testGetOrderItemById_Success() throws Exception {
        when(orderItemsService.getOrderItemById(1L)).thenReturn(testOrderItem);

        mockMvc.perform(get("/api/order-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItems_id").value(1L))
                .andExpect(jsonPath("$.product.name").value("Test Product"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testGetOrderItemById_NotFound() throws Exception {
        when(orderItemsService.getOrderItemById(1L)).thenThrow(new NoSuchElementException("Order item with ID 1 not found"));

        mockMvc.perform(get("/api/order-items/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllOrderItemsByOrderId_Success() throws Exception {
        List<OrderItems> orderItemsList = List.of(testOrderItem);
        when(orderItemsService.getAllOrderItemsByOrderId(1L)).thenReturn(orderItemsList);

        mockMvc.perform(get("/api/order-items/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderItems_id").value(1L))
                .andExpect(jsonPath("$[0].product.name").value("Test Product"))
                .andExpect(jsonPath("$[0].quantity").value(5));
    }

    @Test
    public void testUpdateOrderItem_Success() throws Exception {
        when(orderItemsService.updateOrderItem(eq(1L), any(OrderItems.class))).thenReturn(testOrderItem);

        mockMvc.perform(put("/api/order-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderItems_id\":1,\"product\":{\"productId\":1,\"name\":\"Test Product\",\"price\":100.0},\"quantity\":10,\"price\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testDeleteOrderItem_Success() throws Exception {
        when(orderItemsService.deleteOrderItem(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/order-items/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteOrderItem_NotFound() throws Exception {
        when(orderItemsService.deleteOrderItem(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/order-items/1"))
                .andExpect(status().isNotFound());
    }
}

