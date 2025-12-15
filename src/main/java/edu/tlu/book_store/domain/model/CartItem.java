package edu.tlu.book_store.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    
    @Id
    @Column(name = "cart_item_id")
    private String cartItemId;
    
    @Column(name = "cart_id", nullable = false)
    private String cartId;
    
    @Column(name = "book_id", nullable = false)
    private String bookId;
    
    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    @Column(name = "price", nullable = false)
    private int price;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    
     @Transient
    private String bookName;
    
    @Transient
    private String bookImage;
    
    @Transient
    private String sellerName;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
















// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import java.time.LocalDateTime;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class CartItem {
//     private String cartItemId;
//     private String cartId;
//     private String bookId;
//     private String sellerId;
//     private int quantity;
//     private int price;
//     private LocalDateTime createdAt;
//     private LocalDateTime updatedAt;
    
//     // Thông tin book (để hiển thị)
//     private String bookName;
//     private String bookImage;
//     private String sellerName;
    
//     // Tính tổng tiền của item
//     public int getTotalPrice() {
//         return price * quantity;
//     }
// }