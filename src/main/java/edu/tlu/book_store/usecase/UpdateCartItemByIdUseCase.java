
package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCartItemByIdUseCase {
    
    private final CartItemRepository cartItemRepository;
    
    @Transactional
    public void execute(String cartItemId, int quantity) {
        if (quantity < 1) {
            throw new RuntimeException("Quantity must be at least 1");
        }
        
        System.out.println("🔄 Updating cartItemId: " + cartItemId + " to quantity: " + quantity);
        
        cartItemRepository.updateQuantity(cartItemId, quantity);
        
        System.out.println("✅ Updated successfully");
    }
}