package edu.tlu.book_store.infrastructure.repository;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    // @Override
    // public Optional<User> findById(String id) {
    //     return Optional.ofNullable(em.find(User.class, id));
    // }

    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("🔍 [UserRepository] findByEmail: " + email);
        List<User> result = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email",
                User.class
        )
        .setParameter("email", email)
        .getResultList();
        System.out.println("   query result size = " + result.size());

        return result.stream().findFirst();
    }

      @Override
    public User findById(String id) {
        return em.find(User.class, id);
    }


    @Override
    public User save(User user) {
        if (user.getId() == null) {
            // persist → insert
            em.persist(user);
            return user;
        } else {
            // merge → update
            return em.merge(user);
        }
    }

    @Override
    public List<User> findAll() {
        return em.createQuery(
                "SELECT u FROM User u ORDER BY u.username",
                User.class
        ).getResultList();
    }

    @Override
    public void delete(String id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
     @Override
    public boolean existsById(String id) {
        Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.id = :id",
                Long.class
        )
        .setParameter("id", id)
        .getSingleResult();
        
        return count != null && count > 0;
    }
    
}
