package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveCartItemUseCase {
    
    private final CartItemRepository cartItemRepository;
    
    @Transactional
    public void execute(String cartItemId) {
        cartItemRepository.delete(cartItemId);  // ✅ Dùng delete() thay vì deleteById()
 
    }
}
