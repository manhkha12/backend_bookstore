package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.model.Cart;
import edu.tlu.book_store.domain.model.CartItem;
import edu.tlu.book_store.domain.repository.CartItemRepository;
import edu.tlu.book_store.domain.repository.CartRepository;
import edu.tlu.book_store.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddToCartUseCase {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    
@Transactional
public CartItem execute(String userId, String bookId, String sellerId, int quantity, int price) {
    System.out.println("👤 userid: " + userId);
    
    // 1. Kiểm tra user có tồn tại không
    boolean userExists = userRepository.existsById(userId);
    if (!userExists) {
        throw new RuntimeException("User not found: " + userId);
    }
    
    // 2. Tìm hoặc tạo cart
    Cart cart = cartRepository.findByUserId(userId)
        .orElseGet(() -> {
            System.out.println("🆕 Creating new cart for userId: " + userId);
            Cart newCart = Cart.builder()
                .cartId(UUID.randomUUID().toString())
                .userId(userId)
                .build();
            return cartRepository.save(newCart);
        });
    
    System.out.println("🛒 Cart ID: " + cart.getCartId());
    
    // 3. Kiểm tra item đã tồn tại chưa
    var existingItem = cartItemRepository.findByCartIdAndBookIdAndSellerId(
        cart.getCartId(), bookId, sellerId
    );
    
    if (existingItem.isPresent()) {
        // 4a. Nếu đã có -> cập nhật số lượng
        CartItem item = existingItem.get();
        int oldQuantity = item.getQuantity();
        item.setQuantity(oldQuantity + quantity);
        item.setPrice(price);
        
        CartItem saved = cartItemRepository.save(item);
        System.out.println("🔄 Updated item: " + saved.getCartItemId());
        
        return saved;
    } else {
        // 4b. Nếu chưa có -> thêm mới
        CartItem newItem = CartItem.builder()
            .cartItemId(UUID.randomUUID().toString())
            .cartId(cart.getCartId())
            .bookId(bookId)
            .sellerId(sellerId)
            .quantity(quantity)
            .price(price)
            .build();
        
        CartItem saved = cartItemRepository.save(newItem);
        System.out.println("➕ Created new item: " + saved.getCartItemId());
        
        return saved;
    }
}


    // @Transactional
    // public CartItem execute(String userId, String bookId, String sellerId, int quantity, int price) {
    //     System.err.println("userid"+userId);
    //     // 1. Kiểm tra user có tồn tại không
    //     boolean userExists = userRepository.existsById(userId);
    //     if (!userExists) {

    //         throw new RuntimeException("User not found: " + userId);
    //     }
       
        
    //     // 2. Tìm hoặc tạo cart
    //     Cart cart = cartRepository.findByUserId(userId)
    //         .orElseGet(() -> {
              
    //             Cart newCart = Cart.builder()
    //                 .cartId(UUID.randomUUID().toString())
    //                 .userId(userId)
    //                 .build();
    //             return cartRepository.save(newCart);
    //         });
        

        
    //     // 3. Kiểm tra item đã tồn tại chưa
    //     var existingItem = cartItemRepository.findByCartIdAndBookIdAndSellerId(
    //         cart.getCartId(), bookId, sellerId
    //     );
        
    //     if (existingItem.isPresent()) {
    //         // 4a. Nếu đã có -> cập nhật số lượng
    //         CartItem item = existingItem.get();
    //         int oldQuantity = item.getQuantity();
    //         item.setQuantity(oldQuantity + quantity);
    //         item.setPrice(price);
            
    //         CartItem saved = cartItemRepository.save(item);
           
            
    //         return saved;
    //     } else {
    //         // 4b. Nếu chưa có -> thêm mới
    //         CartItem newItem = CartItem.builder()
    //             .cartItemId(UUID.randomUUID().toString())
    //             .cartId(cart.getCartId())
    //             .bookId(bookId)
    //             .sellerId(sellerId)
    //             .quantity(quantity)
    //             .price(price)
    //             .build();
            
    //         CartItem saved = cartItemRepository.save(newItem);
        
            
    //         return saved;
    //     }
    // }
}