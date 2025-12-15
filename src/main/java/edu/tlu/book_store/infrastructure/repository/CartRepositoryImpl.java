// package edu.tlu.book_store.infrastructure.repository;

// import edu.tlu.book_store.domain.model.Cart;
// import edu.tlu.book_store.domain.repository.CartRepository;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;
// import org.springframework.stereotype.Repository;
// import lombok.RequiredArgsConstructor;
// import java.util.Optional;
// import java.util.UUID;

// @Repository
// @RequiredArgsConstructor
// public class CartRepositoryImpl implements CartRepository {
    
//     private final JdbcTemplate jdbcTemplate;
    
//     private final RowMapper<Cart> cartRowMapper = (rs, rowNum) -> Cart.builder()
//         .cartId(rs.getString("cart_id"))
//         .userId(rs.getString("user_id"))
//         .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
//         .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
//         .build();
    
//     @Override
//     public Optional<Cart> findByUserId(String userId) {
//         String sql = "SELECT * FROM carts WHERE user_id = ?";
//         try {
//             Cart cart = jdbcTemplate.queryForObject(sql, cartRowMapper, userId);
//             return Optional.ofNullable(cart);
//         } catch (Exception e) {
//             return Optional.empty();
//         }
//     }
    
//     @Override
//     public Cart save(Cart cart) {
//         if (cart.getCartId() == null) {
//             // Insert new cart
//             cart.setCartId("cart_" + UUID.randomUUID().toString());
//             String sql = "INSERT INTO carts (cart_id, user_id) VALUES (?, ?)";
//             jdbcTemplate.update(sql, cart.getCartId(), cart.getUserId());
//         } else {
//             // Update existing cart
//             String sql = "UPDATE carts SET updated_at = CURRENT_TIMESTAMP WHERE cart_id = ?";
//             jdbcTemplate.update(sql, cart.getCartId());
//         }
//         return findByUserId(cart.getUserId()).orElse(cart);
//     }
    
//     @Override
//     public void deleteByUserId(String userId) {
//         String sql = "DELETE FROM carts WHERE user_id = ?";
//         jdbcTemplate.update(sql, userId);
//     }
    
//     @Override
//     public boolean existsByUserId(String userId) {
//         String sql = "SELECT COUNT(*) FROM carts WHERE user_id = ?";
//         Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
//         return count != null && count > 0;
//     }
// }


package edu.tlu.book_store.infrastructure.repository;

import edu.tlu.book_store.domain.model.Cart;
import edu.tlu.book_store.domain.repository.CartRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CartRepositoryImpl implements CartRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Cart> findByUserId(String userId) {
        System.out.println("🔍 [CartRepo] Finding cart for userId: " + userId);
        try {
            Cart cart = em.createQuery(
                "SELECT c FROM Cart c WHERE c.userId = :userId", 
                Cart.class
            )
            .setParameter("userId", userId)
            .getSingleResult();
            
            System.out.println("✅ [CartRepo] Cart found: cartId=" + cart.getCartId());
            return Optional.of(cart);
        } catch (Exception e) {
            System.out.println("❌ [CartRepo] No cart found for userId: " + userId);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Cart save(Cart cart) {
        if (cart.getCartId() == null) {
            // Insert new cart
            String newCartId = UUID.randomUUID().toString();
            cart.setCartId(newCartId);
            
            System.out.println("➕ [CartRepo] Persisting new cart: cartId=" + newCartId + ", userId=" + cart.getUserId());
            em.persist(cart);
            System.out.println("✅ [CartRepo] Cart persisted successfully");
            
            return cart;
        } else {
            // Update existing cart
            System.out.println("🔄 [CartRepo] Merging cart: cartId=" + cart.getCartId());
            Cart merged = em.merge(cart);
            System.out.println("✅ [CartRepo] Cart merged successfully");
            
            return merged;
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(String userId) {
        System.out.println("🗑️ [CartRepo] Deleting cart for userId: " + userId);
        int deleted = em.createQuery("DELETE FROM Cart c WHERE c.userId = :userId")
            .setParameter("userId", userId)
            .executeUpdate();
        System.out.println("✅ [CartRepo] Deleted " + deleted + " cart(s)");
    }

    @Override
    public boolean existsByUserId(String userId) {
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Cart c WHERE c.userId = :userId", 
            Long.class
        )
        .setParameter("userId", userId)
        .getSingleResult();
        
        boolean exists = count != null && count > 0;
        System.out.println("🔍 [CartRepo] Cart exists for userId " + userId + ": " + exists);
        return exists;
    }
}