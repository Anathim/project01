package za.ac.mycput.musicalnote_backend.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Service.ProductService;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing data...");

        // Add products to the database
        productService.saveProduct(new Product("Guitar", 299.00f, "/images/guitar.webp"));
        productService.saveProduct(new Product("Piano", 499.00f, "/images/piano.webp"));
        productService.saveProduct(new Product("Drum Kit", 399.00f, "/images/drumkit.webp"));
        productService.saveProduct(new Product("Violin", 249.00f, "/images/violin.jpg"));
        productService.saveProduct(new Product("Trumpet", 349.00f, "/images/trumpet.jpg"));
        productService.saveProduct(new Product("Saxophone", 279.00f, "/images/saxophone.webp"));
        productService.saveProduct(new Product("Flute", 189.00f, "/images/flute.webp"));
        productService.saveProduct(new Product("Clarinet", 220.00f, "/images/clarinet.jpg"));
        productService.saveProduct(new Product("Trombone", 350.00f, "/images/trombone.jpg"));
        productService.saveProduct(new Product("Cello", 499.00f, "/images/cello.webp"));
        productService.saveProduct(new Product("Harp", 799.00f, "/images/harp.webp"));
        productService.saveProduct(new Product("Accordion", 299.00f, "/images/accordion.jpg"));

        System.out.println("Data initialization complete.");
    }
}

