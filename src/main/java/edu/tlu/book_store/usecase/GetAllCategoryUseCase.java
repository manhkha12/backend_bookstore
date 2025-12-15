package edu.tlu.book_store.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.Category;
import edu.tlu.book_store.domain.repository.CategoryRepository;

@Service
public class GetAllCategoryUseCase {
    final CategoryRepository categoryRepository;
    public GetAllCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> execute() {
        return categoryRepository.findAll();    
    }
}
