package edu.tlu.book_store.usecase;


import edu.tlu.book_store.domain.repository.CartItemRepository;
import edu.tlu.book_store.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClearCartUseCase {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    
    @Transactional
    public void execute(String userId) {
        var cart = cartRepository.findByUserId(userId);
        
        if (cart.isPresent()) {
            // Xóa tất cả items trong cart
            cartItemRepository.deleteByCartId(cart.get().getCartId());
      
        } 
    }
}