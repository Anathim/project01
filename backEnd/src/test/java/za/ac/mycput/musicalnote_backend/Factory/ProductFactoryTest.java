package za.ac.mycput.musicalnote_backend.Factory;

import org.junit.jupiter.api.Test;
import za.ac.mycput.musicalnote_backend.Domain.Product;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

public class ProductFactoryTest {

    @Test
    public void testBuildProduct_Success() {

        Long productId = 1L;
        String name = "Guitar";
        String description = "Electric guitar";
        float price = 299.99f;
        String category = "Instruments";
        int stock = 10;


        Product product = ProductFactory.buildproduct(productId, name, description, price, category, stock);


        assertNotNull(product, "Product should not be null");
        assertEquals(productId, product.getProductId(), "Product ID should match");
        assertEquals(name, product.getName(), "Product name should match");
        assertEquals(description, product.getDescription(), "Product description should match");
        assertEquals(price, product.getPrice(), "Product price should match");
        assertEquals(category, product.getCategory(), "Product category should match");
        assertEquals(stock, product.getStock(), "Product stock should match");
    }

    @Test
    public void testBuildProduct_InvalidData() {

        Long productId = null;
        String name = "";
        String description = "";
        float price = -10.0f;
        String category = "";
        int stock = -5;


        Product product = ProductFactory.buildproduct(productId, name, description, price, category, stock);


        assertNull(product, "Product should be null due to invalid input data");
    }
}

