package edu.tlu.book_store.presentation;



import edu.tlu.book_store.domain.model.Cart;
import edu.tlu.book_store.domain.model.CartItem;
import edu.tlu.book_store.infrastructure.sercurity.JwtService;
import edu.tlu.book_store.presentation.dto.AddToCartRequest;
import edu.tlu.book_store.presentation.dto.UpdateCartItemRequest;
import edu.tlu.book_store.usecase.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@CrossOrigin
@RequiredArgsConstructor
public class CartController {
    
    private final AddToCartUseCase addToCartUseCase;
    private final GetCartUseCase getCartUseCase;
private final UpdateCartItemByIdUseCase updateCartItemByIdUseCase; // Thay thế
    private final RemoveCartItemUseCase removeCartItemUseCase;
    private final ClearCartUseCase clearCartUseCase;
    private final JwtService jwtService;
    
    // Helper method: Extract userId từ Authorization header
    private String extractUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid authorization header");
        }
        String token = authHeader.substring(7);
        String userId = jwtService.extractUserId(token);
        
        if (userId == null) {
            throw new RuntimeException("UserId not found in token");
        }
        
   
        return userId;
    }
    
    // Get cart
    @GetMapping
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String authHeader) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            Cart cart = getCartUseCase.execute(userId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
    
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Add to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody AddToCartRequest request
    ) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            
       
            // Validate
            if (request.getBookId() == null || request.getBookId().isEmpty()) {
                return ResponseEntity.badRequest().body("bookId is required");
            }
            if (request.getSellerId() == null || request.getSellerId().isEmpty()) {
                return ResponseEntity.badRequest().body("sellerId is required");
            }
            if (request.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("quantity must be greater than 0");
            }
            if (request.getPrice() <= 0) {
                return ResponseEntity.badRequest().body("price must be greater than 0");
            }
            
            CartItem item = addToCartUseCase.execute(
                userId,
                request.getBookId(),
                request.getSellerId(),
                request.getQuantity(),
                request.getPrice()
            );
            
     
            return ResponseEntity.ok(item);
            
        } catch (Exception e) {
         
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Update cart item quantity


@PutMapping("/items/{cartItemId}")
public ResponseEntity<?> updateCartItemQuantity(
    @PathVariable String cartItemId,
    @RequestBody UpdateCartItemRequest request
) {
    try {
        updateCartItemByIdUseCase.execute(cartItemId, request.getQuantity());
        return ResponseEntity.noContent().build();
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
    
    // Remove cart item
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String itemId) {
        try {
            removeCartItemUseCase.execute(itemId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Clear cart
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String authHeader) {
        try {
            String userId = extractUserIdFromToken(authHeader);
            clearCartUseCase.execute(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}