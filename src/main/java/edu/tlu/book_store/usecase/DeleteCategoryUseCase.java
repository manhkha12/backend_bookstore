package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.Category;
import edu.tlu.book_store.domain.repository.CategoryRepository;
@Service
public class DeleteCategoryUseCase {
    
    private final CategoryRepository repo;

    public DeleteCategoryUseCase(CategoryRepository repo) {
        this.repo = repo;
    }

    public void execute(String id) {
        Category category = repo.findById(id);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        repo.delete(category);
    }
}
