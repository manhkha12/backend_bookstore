package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import jakarta.transaction.Transactional;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
@Transactional
    public User register(String username, String email, String password, String name) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        String userId = UUID.randomUUID().toString();

        User user = new User(
                userId,
                username,
                email,
                passwordEncoder.encode(password),
                "user",
                name
        );

        return userRepository.save(user);
    }
}
