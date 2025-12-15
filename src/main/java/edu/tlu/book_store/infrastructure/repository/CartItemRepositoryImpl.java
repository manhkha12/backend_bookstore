// package edu.tlu.book_store.infrastructure.repository;

// import edu.tlu.book_store.domain.model.CartItem;
// import edu.tlu.book_store.domain.repository.CartItemRepository;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;
// import org.springframework.stereotype.Repository;
// import lombok.RequiredArgsConstructor;
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// @Repository
// @RequiredArgsConstructor
// public class CartItemRepositoryImpl implements CartItemRepository {
    
//     private final JdbcTemplate jdbcTemplate;
    
//     private final RowMapper<CartItem> cartItemRowMapper = (rs, rowNum) -> CartItem.builder()
//         .cartItemId(rs.getString("cart_item_id"))
//         .cartId(rs.getString("cart_id"))
//         .bookId(rs.getString("book_id"))
//         .sellerId(rs.getString("seller_id"))
//         .quantity(rs.getInt("quantity"))
//         .price(rs.getInt("price"))
//         .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
//         .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
//         .bookName(rs.getString("book_name"))
//         .bookImage(rs.getString("book_image"))
//         .sellerName(rs.getString("seller_name"))
//         .build();
    
//     // @Override
//     // public List<CartItem> findByCartId(String cartId) {
//     //     String sql = """
//     //         SELECT ci.*, b.name as book_name, 
//     //                bi.base_url as book_image, s.name as seller_name
//     //         FROM cart_items ci
//     //         JOIN books b ON ci.book_id = b.book_id
//     //         LEFT JOIN book_images bi ON b.book_id = bi.book_id AND bi.position = 1
//     //         JOIN sellers s ON ci.seller_id = s.seller_id
//     //         WHERE ci.cart_id = ?
//     //         ORDER BY ci.created_at DESC
//     //     """;
//     //     return jdbcTemplate.query(sql, cartItemRowMapper, cartId);
//     // }
    
// @Override
// public List<CartItem> findByCartId(String cartId) {
//     System.out.println("🔍 Finding cart items for cartId: " + cartId);
    
//     String sql = """
//         SELECT ci.*, b.name as book_name, 
//                bi.base_url as book_image, s.name as seller_name
//         FROM cart_items ci
//         JOIN books b ON ci.book_id = b.book_id
//         LEFT JOIN book_images bi ON b.book_id = bi.book_id AND bi.position = 1
//         JOIN sellers s ON ci.seller_id = s.seller_id
//         WHERE ci.cart_id = ?
//         ORDER BY ci.created_at DESC
//     """;
    
//     List<CartItem> items = jdbcTemplate.query(sql, cartItemRowMapper, cartId);
//     System.out.println("✅ Found " + items.size() + " items");
    
//     return items;
// }


//     @Override
//     public Optional<CartItem> findByCartIdAndBookIdAndSellerId(String cartId, String bookId, String sellerId) {
//         String sql = """
//             SELECT ci.*, b.name as book_name, 
//                    bi.base_url as book_image, s.name as seller_name
//             FROM cart_items ci
//             JOIN books b ON ci.book_id = b.book_id
//             LEFT JOIN book_images bi ON b.book_id = bi.book_id AND bi.position = 1
//             JOIN sellers s ON ci.seller_id = s.seller_id
//             WHERE ci.cart_id = ? AND ci.book_id = ? AND ci.seller_id = ?
//         """;
//         try {
//             CartItem item = jdbcTemplate.queryForObject(sql, cartItemRowMapper, cartId, bookId, sellerId);
//             return Optional.ofNullable(item);
//         } catch (Exception e) {
//             return Optional.empty();
//         }
//     }
    
//     @Override
// public CartItem save(CartItem cartItem) {
//     if (cartItem.getCartItemId() == null) {
//         // Insert new item
//         cartItem.setCartItemId(UUID.randomUUID().toString());
//         String sql = """
//             INSERT INTO cart_items (cart_item_id, cart_id, book_id, seller_id, quantity, price)
//             VALUES (?, ?, ?, ?, ?, ?)
//         """;
//         jdbcTemplate.update(sql, 
//             cartItem.getCartItemId(), 
//             cartItem.getCartId(),
//             cartItem.getBookId(), 
//             cartItem.getSellerId(), 
//             cartItem.getQuantity(),
//             cartItem.getPrice()
//         );
        
//         // ✅ FIX: Fetch lại để lấy bookName, bookImage, timestamps
//         return findByCartIdAndBookIdAndSellerId(
//             cartItem.getCartId(), 
//             cartItem.getBookId(), 
//             cartItem.getSellerId()
//         ).orElse(cartItem);
        
//     } else {
//         // Update existing item
//         String sql = """
//             UPDATE cart_items 
//             SET quantity = ?, price = ?, updated_at = CURRENT_TIMESTAMP
//             WHERE cart_item_id = ?
//         """;
//         jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getPrice(), cartItem.getCartItemId());
        
//         // ✅ FIX: Fetch lại
//         return findByCartIdAndBookIdAndSellerId(
//             cartItem.getCartId(), 
//             cartItem.getBookId(), 
//             cartItem.getSellerId()
//         ).orElse(cartItem);
//     }
// }
//     @Override
//     public void delete(String cartItemId) {
//         String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
//         jdbcTemplate.update(sql, cartItemId);
//     }
    
//     @Override
//     public void deleteByCartId(String cartId) {
//         String sql = "DELETE FROM cart_items WHERE cart_id = ?";
//         jdbcTemplate.update(sql, cartId);
//     }
    
//     @Override
//     public int countByCartId(String cartId) {
//         String sql = "SELECT COUNT(*) FROM cart_items WHERE cart_id = ?";
//         Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cartId);
//         return count != null ? count : 0;
//     }
// }



package edu.tlu.book_store.infrastructure.repository;

import edu.tlu.book_store.domain.model.CartItem;
import edu.tlu.book_store.domain.repository.CartItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CartItemRepositoryImpl implements CartItemRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CartItem> findByCartId(String cartId) {
        System.out.println("🔍 [CartItemRepo] Finding items for cartId: " + cartId);
        
        List<CartItem> items = em.createQuery(
            "SELECT ci FROM CartItem ci WHERE ci.cartId = :cartId ORDER BY ci.createdAt DESC",
            CartItem.class
        )
        .setParameter("cartId", cartId)
        .getResultList();
        
        System.out.println("✅ [CartItemRepo] Found " + items.size() + " items");
        return items;
    }

    @Override
    public Optional<CartItem> findByCartIdAndBookIdAndSellerId(String cartId, String bookId, String sellerId) {
        System.out.println("🔍 [CartItemRepo] Finding item: cartId=" + cartId + ", bookId=" + bookId + ", sellerId=" + sellerId);
        
        try {
            CartItem item = em.createQuery(
                "SELECT ci FROM CartItem ci WHERE ci.cartId = :cartId AND ci.bookId = :bookId AND ci.sellerId = :sellerId",
                CartItem.class
            )
            .setParameter("cartId", cartId)
            .setParameter("bookId", bookId)
            .setParameter("sellerId", sellerId)
            .getSingleResult();
            
            System.out.println("✅ [CartItemRepo] Item found: " + item.getCartItemId());
            return Optional.of(item);
        } catch (Exception e) {
            System.out.println("❌ [CartItemRepo] Item not found");
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public CartItem save(CartItem cartItem) {
        if (cartItem.getCartItemId() == null) {
            // Insert new item
            String newItemId = UUID.randomUUID().toString();
            cartItem.setCartItemId(newItemId);
            
            System.out.println("➕ [CartItemRepo] Persisting new item: " + newItemId);
            em.persist(cartItem);
            System.out.println("✅ [CartItemRepo] Item persisted successfully");
            
            return cartItem;
        } else {
            // Update existing item
            System.out.println("🔄 [CartItemRepo] Merging item: " + cartItem.getCartItemId());
            CartItem merged = em.merge(cartItem);
            System.out.println("✅ [CartItemRepo] Item merged successfully");
            
            return merged;
        }
    }

    @Override
    @Transactional
    public void delete(String cartItemId) {
        System.out.println("🗑️ [CartItemRepo] Deleting item: " + cartItemId);
        CartItem item = em.find(CartItem.class, cartItemId);
        if (item != null) {
            em.remove(item);
            System.out.println("✅ [CartItemRepo] Item deleted successfully");
        } else {
            System.out.println("❌ [CartItemRepo] Item not found");
        }
    }

    @Override
    @Transactional
    public void deleteByCartId(String cartId) {
        System.out.println("🗑️ [CartItemRepo] Deleting all items for cartId: " + cartId);
        int deleted = em.createQuery("DELETE FROM CartItem ci WHERE ci.cartId = :cartId")
            .setParameter("cartId", cartId)
            .executeUpdate();
        System.out.println("✅ [CartItemRepo] Deleted " + deleted + " item(s)");
    }

    @Override
    public int countByCartId(String cartId) {
        Long count = em.createQuery(
            "SELECT COUNT(ci) FROM CartItem ci WHERE ci.cartId = :cartId",
            Long.class
        )
        .setParameter("cartId", cartId)
        .getSingleResult();
        
        return count != null ? count.intValue() : 0;
    }

    @Override
    @Transactional
    public void updateQuantity(String cartItemId, int quantity) {
        System.out.println("🔄 [CartItemRepo] Updating quantity for item: " + cartItemId + " to " + quantity);
        int updated = em.createQuery(
            "UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.cartItemId = :id"
        )
        .setParameter("quantity", quantity)
        .setParameter("id", cartItemId)
        .executeUpdate();
        System.out.println("✅ [CartItemRepo] Updated " + updated + " item(s)");
    }
}