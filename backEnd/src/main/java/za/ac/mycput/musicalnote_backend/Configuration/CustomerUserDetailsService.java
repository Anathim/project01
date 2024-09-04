package za.ac.mycput.musicalnote_backend.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.ac.mycput.musicalnote_backend.Domain.UserAuthentication;
import za.ac.mycput.musicalnote_backend.Repository.UserRepository;

import java.util.NoSuchElementException;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return new UserAuthentication(userEntity.get());
        }

        throw new UsernameNotFoundException("User does not exist.");
    }
}

