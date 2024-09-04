package za.ac.mycput.musicalnote_backend.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Repository.CartRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    private Cart testCart;
    private Product testProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a test product
        testProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        testProduct.setProductId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0f);

        // Setup a test cart
        testCart = new Cart();
        testCart.setCartId(1L);
        testCart.setProducts(new ArrayList<>());

        // Add product to cart
        testCart.getProducts().add(testProduct);
    }

    @Test
    public void testAddProductToCart_Success() {
        // Arrange
        Cart updatedCart = new Cart();
        updatedCart.setCartId(1L);
        updatedCart.setProducts(new ArrayList<>(List.of(testProduct)));

        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        when(productService.getProductById(1L)).thenReturn(testProduct);
        when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);

        // Act
        Cart result = cartService.addProductToCart(1L, 1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.getProducts().contains(testProduct));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void testRemoveProductFromCart_Success() {
        // Arrange
        testCart.getProducts().remove(testProduct);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(testCart);

        // Act
        Cart result = cartService.removeProductFromCart(1L, 1L);

        // Assert
        assertNotNull(result);
        assertFalse(result.getProducts().contains(testProduct));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void testGetCartById_Success() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));

        // Act
        Optional<Cart> result = cartService.getCartById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testCart.getCartId(), result.get().getCartId());
    }

    @Test
    public void testGetCartById_NotFound() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            cartService.getCartById(1L);
        });
        assertEquals("Cart with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testGetAllProductsInCart() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(testCart));

        // Act
        List<Product> result = cartService.getAllProductsInCart(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains(testProduct));
    }
}
