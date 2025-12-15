package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.Category;
import edu.tlu.book_store.domain.repository.CategoryRepository;
@Service
public class UpdateCategoryUseCase {

    private final CategoryRepository repo;

    public UpdateCategoryUseCase(CategoryRepository repo) {
        this.repo = repo;
    }

    public Category execute(String id, String newName) {
        Category category = repo.findById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        category.setName(newName);
        return repo.save(category);
    }
}
