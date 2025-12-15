package edu.tlu.book_store.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    // From cart_items table
    private String cartItemId;
    private String cartId;
    private String bookId;
    private String sellerId;
    private int quantity;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional info (not in DB, fetched from books/sellers)
    private String bookName;
    private String bookImage;
    private String sellerName;
    
    // Computed
    public int getTotalPrice() {
        return price * quantity;
    }
}