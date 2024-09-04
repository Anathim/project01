package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Factory.ProductFactory;
import za.ac.mycput.musicalnote_backend.Repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public Product createProduct(Long productId, String name, String description, float price, String category, int stock) {
        Product product = ProductFactory.buildproduct(productId, name, description, price, category, stock);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product details provided.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, String name, String description, float price, String category, int stock) {
        Product existingProduct = getProductById(productId);

        // Update the fields accordingly
        existingProduct = new Product.Builder()
                .setProductId(productId)
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setCategory(category)
                .setStock(stock)
                .copy(existingProduct)
                .build();

        return productRepository.save(existingProduct);
    }

    public Boolean deleteProduct(Long productId) {
        Product existingProduct = getProductById(productId);
        productRepository.delete(existingProduct);
        return true;
    }

    public Product saveProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        return productRepository.save(product);
    }
}

