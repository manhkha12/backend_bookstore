package edu.tlu.book_store.usecase;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;

@Service
public class CreateUserUseCase {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public CreateUserUseCase(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User execute(Map<String, String> body) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(body.get("username"));
        user.setEmail(body.get("email"));
        user.setRole(body.get("role"));
        user.setName(body.get("name"));
        user.setPassword(encoder.encode(body.get("password")));

        return repo.save(user);
    }
}
