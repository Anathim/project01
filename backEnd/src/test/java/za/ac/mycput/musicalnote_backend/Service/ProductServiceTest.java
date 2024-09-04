package za.ac.mycput.musicalnote_backend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    public void setUp() {
        // Setup a test product
        Product testProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        testProduct.setProductId(1L);
        testProduct.setName("Test Product");
        testProduct.setPrice(100.0f);
    }

    @Test
    public void testCreateProduct_Success() {
        // Arrange
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        // Act
        Product result = productService.createProduct(1L, testProduct.getName(), testProduct.getDescription(), testProduct.getPrice(), testProduct.getCategory(), testProduct.getStock());

        // Assert
        assertNotNull(result, "Product should not be null");
        assertEquals(testProduct.getProductId(), result.getProductId(), "Product ID should match");
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    public void testGetProductById_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Act
        Product result = productService.getProductById(1L);

        // Assert
        assertNotNull(result, "Product should not be null");
        assertEquals(testProduct.getProductId(), result.getProductId(), "Product ID should match");
    }

    @Test
    public void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            productService.getProductById(1L);
        });
        assertEquals("Product with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testUpdateProduct_Success() {
        // Arrange
        Product updatedProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        updatedProduct.setProductId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(150.0f);

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        // Act
        Product result = productService.updateProduct(1L, updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(), updatedProduct.getCategory(), updatedProduct.getStock() );

        // Assert
        assertNotNull(result, "Product should not be null");
        assertEquals("Updated Product", result.getName(), "Product name should match");
        assertEquals(150.0f, result.getPrice(), "Product price should match");
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    public void testUpdateProduct_NotFound() {
        // Arrange
        Product updateProduct = new Product("Guitar", 299.00f, "/images/guitar.webp");
        updateProduct.setProductId(1L);
        updateProduct.setName("Updated Product");
        updateProduct.setPrice(150.0f);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            productService.updateProduct(1L, updateProduct.getName(), updateProduct.getDescription(), updateProduct.getPrice(), updateProduct.getCategory(), updateProduct.getStock());
        });
        assertEquals("Product with ID 1 not found", thrown.getMessage());
    }

    @Test
    public void testDeleteProduct_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        doNothing().when(productRepository).deleteById(1L);

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProduct_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            productService.deleteProduct(1L);
        });
        assertEquals("Product with ID 1 not found", thrown.getMessage());
    }
}

