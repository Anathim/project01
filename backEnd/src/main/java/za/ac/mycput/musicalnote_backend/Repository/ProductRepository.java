package za.ac.mycput.musicalnote_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.mycput.musicalnote_backend.Domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    Optional<Product> findById(Long productId);


}
