package edu.tlu.book_store.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tlu.book_store.usecase.CreateCategoryUseCase;
import edu.tlu.book_store.usecase.DeleteCategoryUseCase;
import edu.tlu.book_store.usecase.GetAllCategoryUseCase;
import edu.tlu.book_store.usecase.UpdateCategoryUseCase;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final GetAllCategoryUseCase getAllCategoryUseCase;
    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public CategoryController(
        GetAllCategoryUseCase getAllCategoryUseCase,
        CreateCategoryUseCase createCategoryUseCase,
        UpdateCategoryUseCase updateCategoryUseCase,
        DeleteCategoryUseCase deleteCategoryUseCase
    ) {
        this.getAllCategoryUseCase = getAllCategoryUseCase;
        this.createCategoryUseCase = createCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    @GetMapping
    public Object getCategories() {
        return getAllCategoryUseCase.execute();
    }

    @PostMapping
    public Object createCategory(@RequestBody Map<String, String> body) {
        return createCategoryUseCase.execute(body.get("name"));
    }

    @PutMapping("/{id}")
    public Object updateCategory(@PathVariable String id, @RequestBody Map<String, String> body) {
        return updateCategoryUseCase.execute(id, body.get("name"));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        deleteCategoryUseCase.execute(id);
    }
}

