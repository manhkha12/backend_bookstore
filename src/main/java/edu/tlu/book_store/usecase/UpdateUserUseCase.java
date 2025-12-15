package edu.tlu.book_store.usecase;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;

@Service
public class UpdateUserUseCase {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UpdateUserUseCase(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User execute(String id, Map<String, String> body) {
        User user = repo.findById(id);
        if (user == null) return null;

        user.setUsername(body.get("username"));
        user.setEmail(body.get("email"));
        user.setRole(body.get("role"));
        user.setName(body.get("name"));

        if (body.containsKey("password") && !body.get("password").isBlank()) {
            user.setPassword(encoder.encode(body.get("password")));
        }

        return repo.save(user);
    }
}
