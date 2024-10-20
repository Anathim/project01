package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Domain.UserAuthentication;
import za.ac.mycput.musicalnote_backend.Repository.UserRepository;
import za.ac.mycput.musicalnote_backend.util.jwt;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private jwt jwt;

    public UserService( UserRepository userRepository, OrderService orderService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public boolean registerUser(User userEntity) {
        boolean existsEmail = userRepository.existsByEmail(userEntity.getEmail());
        boolean existsUsername = userRepository.existsByUsername(userEntity.getUsername());

        if (existsUsername) {
            throw new IllegalArgumentException(
                    "username: " + userEntity.getUsername() + " already exists. Cannot create user");
        }

        if (existsEmail) {
            throw new IllegalArgumentException(
                    "Email address: " + userEntity.getEmail() + " already exists. Cannot create user");
        }

        // Set default role to 'customer' for public registration
        userEntity.setRole("customer");

        // Encode password
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.resetId();
        userRepository.save(userEntity);

        return true;
    }

    public boolean registerAdmin(User userEntity) {
        boolean existsEmail = userRepository.existsByEmail(userEntity.getEmail());
        boolean existsUsername = userRepository.existsByUsername(userEntity.getUsername());

        if (existsUsername) {
            throw new IllegalArgumentException(
                    "username: " + userEntity.getUsername() + " already exists. Cannot create user");
        }

        if (existsEmail) {
            throw new IllegalArgumentException(
                    "Email address: " + userEntity.getEmail() + " already exists. Cannot create user");
        }

        // Set role to 'admin' for admin registration
        userEntity.setRole("admin");

        // Encode password
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.resetId();
        userRepository.save(userEntity);

        return true;
    }

    public boolean loginUser(String username, String password) {

        // Checking if user exists
        User existingUser = findExistingUsername(username);


        if (passwordEncoder.matches(password, existingUser.getPassword())) {
            return true;
        }
        return false;
    }

    public User findExistingUsername(String username) {
        // Checking if username matches existing users
        Optional<User> existingUserUsername = userRepository.findByUsername(username);
        if (existingUserUsername.isPresent()) {
            return existingUserUsername.get();
        }

        throw new NoSuchElementException("username does not exist");
    }

    public User findExistingEmail(String email) {
        // Checking if email matches existing users
        Optional<User> existingUserEmail = userRepository.findByEmail(email);
        if (existingUserEmail.isPresent()) {
            return existingUserEmail.get();
        }

        throw new NoSuchElementException("User with email " + email + " does not exist");
    }

    public User getCurrentUserFromToken(String token) {
        // Extract the username from the token
        String username = jwt.extractUsername(token);

        // Find the user in the repository by username
        return findExistingUsername(username);
    }

    public User updateUserProfile(Long id, User userEntity) {

        User existingUser = getById(id);
        boolean existsEmail = userRepository.existsByEmail(userEntity.getEmail());
        boolean existsUsername = userRepository.existsByUsername(userEntity.getUsername());

        if (userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }

        // checking if username exists on other users
        if (existsUsername && !existingUser.getUsername().equals(userEntity.getUsername())) {
            throw new IllegalArgumentException(
                    "username: " + userEntity.getUsername() + " already exists. Cannot update user");
        }

        // checking if email exists on other users
        if (existsEmail && !existingUser.getEmail().equals(userEntity.getEmail())) {
            throw new IllegalArgumentException(
                    "Email address: " + userEntity.getEmail() + " already exists. Cannot update user");
        }

        // update user data
        existingUser.setUsername(userEntity.getUsername());
        existingUser.setEmail(userEntity.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(existingUser);
    }

    public List<Order> getAllOrders(Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    public String authenticateUser(String username, String password) {
        User existingUser = findExistingUsername(username);

        if (passwordEncoder.matches(password, existingUser.getPassword())) {
            UserDetails userDetails = new UserAuthentication(existingUser);
            String token = jwt.generateToken(userDetails);
            return token;
        }

        throw new IllegalArgumentException("Invalid username or password");
    }
}

