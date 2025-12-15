package edu.tlu.book_store.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tlu.book_store.domain.model.Book;
import edu.tlu.book_store.usecase.AddBookUseCase;
import edu.tlu.book_store.usecase.DeleteBookUseCase;
import edu.tlu.book_store.usecase.GetAllBooksUseCase;
import edu.tlu.book_store.usecase.UpdateBookUseCase;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final GetAllBooksUseCase getAllBooks;
    private final AddBookUseCase addBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final DeleteBookUseCase deleteBookUseCase;

    public BookController(
        GetAllBooksUseCase getAllBooks,
        AddBookUseCase addBookUseCase,
        UpdateBookUseCase updateBookUseCase,
        DeleteBookUseCase deleteBookUseCase
    ) {
        this.getAllBooks = getAllBooks;
        this.addBookUseCase = addBookUseCase;
        this.updateBookUseCase = updateBookUseCase;
        this.deleteBookUseCase = deleteBookUseCase;
    }

    @GetMapping
    public List<Book> getBooks() {
        return getAllBooks.execute();
    }

    
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return getAllBooks.execute().stream()
            .filter(book -> book.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return addBookUseCase.execute(book);
    }

    @PutMapping("/{id}")
public Book updateBook(@PathVariable String id, @RequestBody Book book) {
    book.setId(id);
    return updateBookUseCase.execute(book);
}

@DeleteMapping("/{id}")
public boolean deleteBook(@PathVariable String id) {
    return deleteBookUseCase.execute(id);
}
}

