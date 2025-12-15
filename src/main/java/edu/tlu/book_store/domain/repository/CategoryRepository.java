package edu.tlu.book_store.domain.repository;

import java.util.List;

import edu.tlu.book_store.domain.model.Category;

public interface CategoryRepository {
    List<Category> findAll();
    Category save(Category category);
    Category findById(String id);
    void delete(Category category);
}
