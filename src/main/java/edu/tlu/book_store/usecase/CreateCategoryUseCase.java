package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.Category;
import edu.tlu.book_store.domain.repository.CategoryRepository;
@Service
public class CreateCategoryUseCase {

    private final CategoryRepository repo;

    public CreateCategoryUseCase(CategoryRepository repo) {
        this.repo = repo;
    }

    public Category execute(String name) {
        Category category = new Category();
        category.setName(name);
        return repo.save(category);
    }
}
