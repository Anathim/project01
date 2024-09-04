package za.ac.mycput.musicalnote_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.mycput.musicalnote_backend.Domain.Cart;
import za.ac.mycput.musicalnote_backend.Domain.Product;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Service.CartService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        try {
            Cart newCart = cartService.createCart(
                    cart.getCartId(),
                    cart.getUser(),
                    cart.getOrderItems(),
                    cart.getTotalPrice(),
                    cart.getCreatedDate(),
                    cart.getStatus()
            );
            return new ResponseEntity<>(newCart, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{cartId}/addProduct/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            Cart updatedCart = cartService.addProductToCart(cartId, productId);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cartId}/removeProduct/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            Cart updatedCart = cartService.removeProductFromCart(cartId, productId);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Optional<Cart> cart = cartService.getCartById(cartId);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable Long userId) {
        User user = new User(); // Assuming User object creation, this might require more detailed logic
        user.setUserId(userId); // Assuming setId is the setter method in the User class
        Optional<Cart> cart = cartService.getCartByUser(user);
        return cart.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{cartId}/products")
    public ResponseEntity<List<Product>> getAllProductsInCart(@PathVariable Long cartId) {
        try {
            List<Product> products = cartService.getAllProductsInCart(cartId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, @RequestBody Cart cart) {
        try {
            Cart updatedCart = cartService.updateCart(
                    cartId,
                    cart.getUser(),
                    cart.getOrderItems(),
                    cart.getTotalPrice(),
                    cart.getCreatedDate(),
                    cart.getStatus()
            );
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        try {
            cartService.deleteCart(cartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

