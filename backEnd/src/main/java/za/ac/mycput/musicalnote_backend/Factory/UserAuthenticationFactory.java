package za.ac.mycput.musicalnote_backend.Factory;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.ac.mycput.musicalnote_backend.Domain.User;
import za.ac.mycput.musicalnote_backend.Domain.UserAuthentication;

public class UserAuthenticationFactory {


    public static UserAuthentication builderUserAuthentication(User user) {

        return new UserAuthentication(user);
    }
}
