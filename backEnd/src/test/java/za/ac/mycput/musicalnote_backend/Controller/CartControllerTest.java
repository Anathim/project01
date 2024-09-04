package za.ac.mycput.musicalnote_backend.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Service.CartService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    private Cart testCart;
    private User testUser;
    private Product testProduct;
    private List<OrderItems> testOrderItems;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        testUser = new User();
        testUser.setUserId(1L);

        testProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        testProduct.setProductId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0f);

        testOrderItems = Collections.singletonList(new OrderItems());

        testCart = new Cart();
        testCart.setCartId(1L);
        testCart.setUser(testUser);
        testCart.setOrderItems(testOrderItems);
        testCart.setTotalPrice(100.0f);
        testCart.setCreatedDate(new Date());
        testCart.setStatus("ACTIVE");
        testCart.setProducts(new ArrayList<>(List.of(testProduct)));
    }

    @Test
    public void testCreateCart_Success() {

        when(cartService.createCart(any(), any(), any(), anyFloat(), any(), any())).thenReturn(testCart);


        ResponseEntity<Cart> response = cartController.createCart(testCart);


        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testCart, response.getBody());
    }

    @Test
    public void testCreateCart_InvalidInput() {

        when(cartService.createCart(any(), any(), any(), anyFloat(), any(), any())).thenThrow(IllegalArgumentException.class);


        ResponseEntity<Cart> response = cartController.createCart(testCart);


        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testAddProductToCart_Success() {

        when(cartService.addProductToCart(1L, 1L)).thenReturn(testCart);


        ResponseEntity<Cart> response = cartController.addProductToCart(1L, 1L);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getProducts().contains(testProduct));
    }

    @Test
    public void testAddProductToCart_NotFound() {
        // Arrange
        when(cartService.addProductToCart(1L, 1L)).thenThrow(NoSuchElementException.class);


        ResponseEntity<Cart> response = cartController.addProductToCart(1L, 1L);


        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCart_Success() {

        testCart.getProducts().remove(testProduct);
        when(cartService.removeProductFromCart(1L, 1L)).thenReturn(testCart);


        ResponseEntity<Cart> response = cartController.removeProductFromCart(1L, 1L);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().getProducts().contains(testProduct));
    }

    @Test
    public void testRemoveProductFromCart_NotFound() {

        when(cartService.removeProductFromCart(1L, 1L)).thenThrow(NoSuchElementException.class);


        ResponseEntity<Cart> response = cartController.removeProductFromCart(1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCartById_Success() {

        when(cartService.getCartById(1L)).thenReturn(Optional.of(testCart));


        ResponseEntity<Cart> response = cartController.getCartById(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCart, response.getBody());
    }

    @Test
    public void testGetCartById_NotFound() {

        when(cartService.getCartById(1L)).thenReturn(Optional.empty());


        ResponseEntity<Cart> response = cartController.getCartById(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCartByUser_Success() {

        when(cartService.getCartByUser(testUser)).thenReturn(Optional.of(testCart));


        ResponseEntity<Cart> response = cartController.getCartByUser(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCart, response.getBody());
    }

    @Test
    public void testGetCartByUser_NotFound() {

        when(cartService.getCartByUser(testUser)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.getCartByUser(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllProductsInCart_Success() {

        when(cartService.getAllProductsInCart(1L)).thenReturn(List.of(testProduct));


        ResponseEntity<List<Product>> response = cartController.getAllProductsInCart(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(testProduct));
    }

    @Test
    public void testGetAllProductsInCart_NotFound() {
        when(cartService.getAllProductsInCart(1L)).thenThrow(NoSuchElementException.class);

        ResponseEntity<List<Product>> response = cartController.getAllProductsInCart(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCart_Success() {

        when(cartService.updateCart(anyLong(), any(), any(), anyFloat(), any(), any())).thenReturn(testCart);


        ResponseEntity<Cart> response = cartController.updateCart(1L, testCart);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCart, response.getBody());
    }

    @Test
    public void testUpdateCart_NotFound() {

        when(cartService.updateCart(anyLong(), any(), any(), anyFloat(), any(), any())).thenThrow(IllegalArgumentException.class);


        ResponseEntity<Cart> response = cartController.updateCart(1L, testCart);


        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCart_Success() {

        doNothing().when(cartService).deleteCart(1L);


        ResponseEntity<Void> response = cartController.deleteCart(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCart_NotFound() {

        doThrow(IllegalArgumentException.class).when(cartService).deleteCart(1L);


        ResponseEntity<Void> response = cartController.deleteCart(1L);


        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

