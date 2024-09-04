package za.ac.mycput.musicalnote_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Service.ProductService;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Product testProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();

        testProduct = new Product.Builder()
                .setProductId(1L)
                .setName("Test Product")
                .setDescription("Test Description")
                .setPrice(100.0f)
                .setCategory("Test Category")
                .setStock(10)
                .build();
    }

    @Test
    public void testGetAllProducts() throws Exception {

        when(productService.getAllProducts()).thenReturn(Arrays.asList(testProduct));

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(testProduct.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(testProduct.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(testProduct.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(testProduct.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(testProduct.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stock").value(testProduct.getStock()));
    }

    @Test
    public void testGetProductById_Success() throws Exception {

        when(productService.getProductById(1L)).thenReturn(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(testProduct.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testProduct.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testProduct.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testProduct.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(testProduct.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(testProduct.getStock()));
    }

    @Test
    public void testCreateProduct_Success() throws Exception {

        when(productService.createProduct(anyLong(), anyString(), anyString(), anyFloat(), anyString(), anyInt()))
                .thenReturn(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .param("productId", "1")
                        .param("name", "Test Product")
                        .param("description", "Test Description")
                        .param("price", "100.0")
                        .param("category", "Test Category")
                        .param("stock", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(testProduct.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testProduct.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testProduct.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testProduct.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(testProduct.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(testProduct.getStock()));
    }

    @Test
    public void testUpdateProduct_Success() throws Exception {

        when(productService.updateProduct(anyLong(), anyString(), anyString(), anyFloat(), anyString(), anyInt()))
                .thenReturn(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .param("name", "Updated Product")
                        .param("description", "Updated Description")
                        .param("price", "150.0")
                        .param("category", "Updated Category")
                        .param("stock", "15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(testProduct.getProductId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testProduct.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testProduct.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(testProduct.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(testProduct.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(testProduct.getStock()));
    }

    @Test
    public void testDeleteProduct_Success() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}


