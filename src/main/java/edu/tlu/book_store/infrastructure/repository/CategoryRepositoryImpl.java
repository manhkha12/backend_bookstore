package edu.tlu.book_store.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.tlu.book_store.domain.model.Category;
import edu.tlu.book_store.domain.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Category> findAll() {
        return em.createQuery("SELECT c FROM Category c", Category.class)
                 .getResultList();
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            em.persist(category);
            return category;
        } else {
            return em.merge(category);
        }
    }

    @Override
    public Category findById(String id) {
        return em.find(Category.class, id);
    }

    @Override
    public void delete(Category category) {
        em.remove(category);
    }
}
