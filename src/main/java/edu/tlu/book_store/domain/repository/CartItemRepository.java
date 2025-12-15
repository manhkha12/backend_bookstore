package edu.tlu.book_store.domain.repository;

import edu.tlu.book_store.domain.model.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    CartItem save(CartItem cartItem);
    Optional<CartItem> findByCartIdAndBookIdAndSellerId(String cartId, String bookId, String sellerId);
    List<CartItem> findByCartId(String cartId);
    void delete(String cartItemId);  // ✅ Sửa tên method
    void deleteByCartId(String cartId);
    int countByCartId(String cartId);
    void updateQuantity(String cartItemId, int quantity);
}