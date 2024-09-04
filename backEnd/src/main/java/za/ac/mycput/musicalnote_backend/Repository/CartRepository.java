package za.ac.mycput.musicalnote_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.User;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}

