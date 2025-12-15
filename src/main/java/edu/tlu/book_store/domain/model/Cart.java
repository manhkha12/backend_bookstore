package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    @Id
    @Column(name = "cart_id")
    private String cartId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private List<CartItem> items;
    
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
// import java.util.List;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class Cart {
//     private String cartId;
//     private String userId;
//     private LocalDateTime createdAt;
//     private LocalDateTime updatedAt;
//     private List<CartItem> items;
    
//     // Tính tổng tiền giỏ hàng
//     public int getTotalAmount() {
//         if (items == null) return 0;
//         return items.stream()
//             .mapToInt(item -> item.getPrice() * item.getQuantity())
//             .sum();
//     }
    
//     // Tính tổng số lượng sản phẩm
//     public int getTotalItems() {
//         if (items == null) return 0;
//         return items.stream()
//             .mapToInt(CartItem::getQuantity)
//             .sum();
//     }
// }