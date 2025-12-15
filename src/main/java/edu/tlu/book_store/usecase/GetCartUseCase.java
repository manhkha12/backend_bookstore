// package edu.tlu.book_store.usecase;

// import edu.tlu.book_store.domain.model.Cart;
// import edu.tlu.book_store.domain.repository.CartItemRepository;
// import edu.tlu.book_store.domain.repository.CartRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;


// import java.util.ArrayList;

// @Service
// @RequiredArgsConstructor
// public class GetCartUseCase {
    
//     private final CartRepository cartRepository;
//     private final CartItemRepository cartItemRepository;
    
//     @Transactional(readOnly = true)
//     public Cart execute(String userId) {
//         return cartRepository.findByUserId(userId)
//             .map(cart -> {
//                 var items = cartItemRepository.findByCartId(cart.getCartId());
//                 cart.setItems(items);
//                 return cart;
//             })
//             .orElseGet(() -> Cart.builder()
//                 .userId(userId)
//                 .items(new ArrayList<>())  // Trả về empty list thay vì null
//                 .build()
//             );
//     }
// }


package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.model.Cart;
import edu.tlu.book_store.domain.repository.CartItemRepository;
import edu.tlu.book_store.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GetCartUseCase {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    
    @Transactional(readOnly = true)
    public Cart execute(String userId) {
        System.out.println("🔍 Getting cart for userId: " + userId);
        
        return cartRepository.findByUserId(userId)
            .map(cart -> {
                System.out.println("✅ Cart found: cartId=" + cart.getCartId());
                
                // Fetch items với JOIN query (có bookName, bookImage)
                var items = cartItemRepository.findByCartId(cart.getCartId());
                System.out.println("📦 Found " + items.size() + " items in cart");
                
                cart.setItems(items);
                return cart;
            })
            .orElseGet(() -> {
                System.out.println("🆕 No cart found, creating empty cart");
                return Cart.builder()
                    .userId(userId)
                    .items(new ArrayList<>())
                    .build();
            });
    }
}