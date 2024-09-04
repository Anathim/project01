package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.OrderItems;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Factory.CartFactory;
import za.ac.mycput.musicalnote_backend.Repository.CartRepository;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    private ProductService productService;

    public Cart createCart(Long cartId, User user, List<OrderItems> orderItems, float totalPrice, Date createdDate, String status) {
        Cart cart = CartFactory.buildCart(cartId, user, orderItems, totalPrice, createdDate, status);
        if (cart == null) {
            throw new IllegalArgumentException("Invalid Cart parameters");
        }
        return cartRepository.save(cart);
    }

    public Cart addProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NoSuchElementException("Cart with ID " + cartId + " not found"));
        Product product = productService.getProductById(productId);
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public Optional<Cart> getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public List<Product> getAllProductsInCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NoSuchElementException("Cart with ID " + cartId + " not found"));
        return cart.getProducts();
    }

    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NoSuchElementException("Cart with ID " + cartId + " not found"));
        Product product = productService.getProductById(productId);
        cart.getProducts().remove(product);
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long cartId, User user, List<OrderItems> orderItems, float totalPrice, Date createdDate, String status) {
        Optional<Cart> existingCartOpt = cartRepository.findById(cartId);
        if (existingCartOpt.isEmpty()) {
            throw new IllegalArgumentException("Cart not found");
        }

        Cart existingCart = existingCartOpt.get();
        existingCart.setUser(user);
        existingCart.setOrderItems(orderItems);
        existingCart.setTotalPrice(totalPrice);
        existingCart.setCreatedDate(createdDate);
        existingCart.setStatus(status);

        return cartRepository.save(existingCart);
    }

    public void deleteCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new IllegalArgumentException("Cart not found");
        }
        cartRepository.deleteById(cartId);
    }
}

