package edu.tlu.book_store.domain.repository;

import java.util.List;
import java.util.Optional;

import edu.tlu.book_store.domain.model.User;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
    List<User> findAll();
    User findById(String id);
    void delete(String id);
    boolean existsById(String id);
    
    // ✅ Thêm luôn method kiểm tra email (nếu chưa có)
    default boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}
