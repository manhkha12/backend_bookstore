package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.model.CartItem;
import edu.tlu.book_store.domain.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCartItemUseCase {
    
    private final CartItemRepository cartItemRepository;
    
    @Transactional
    public CartItem execute(String cartId, String bookId, String sellerId, int quantity) {
        var item = cartItemRepository.findByCartIdAndBookIdAndSellerId(cartId, bookId, sellerId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            cartItemRepository.delete(item.getCartItemId());
            return null;
        }
        
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }
}


