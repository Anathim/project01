package za.ac.mycput.musicalnote_backend.Service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Domain.UserAuthentication;
import za.ac.mycput.musicalnote_backend.Repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    public UserService( UserRepository userRepository, OrderService orderService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(Long id) {
        return userRepository.getReferenceById(id);
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


        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.resetId();
        userRepository.save(userEntity);

        return true;
    }

    public boolean loginUser(String username, String password) {

        // Checkinhg if user exists
        User existingUser = findExistingusername(username);


        if (passwordEncoder.matches(password, existingUser.getPassword())) {
            return true;
        }
        return false;
    }

    public User findExistingusername(String username) {
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

    public User updateUserProfile(Long id, User userEntity) {

        User existingUser = getById(id);
        boolean existsEmail = userRepository.existsByEmail(userEntity.getEmail());
        boolean existsUsername = userRepository.existsByUsername(userEntity.getUsername());

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

    public List<Order> getAllOrders(Long id) {
        // returns list of all orders by user
        return orderService.getOrdersByUserId(id);
    }
}

