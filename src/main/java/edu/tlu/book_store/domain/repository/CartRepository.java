package edu.tlu.book_store.domain.repository;

import edu.tlu.book_store.domain.model.Cart;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUserId(String userId);
    Cart save(Cart cart);
    void deleteByUserId(String userId);
    boolean existsByUserId(String userId);
}