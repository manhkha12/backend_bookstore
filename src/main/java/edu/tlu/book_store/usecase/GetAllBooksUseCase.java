package edu.tlu.book_store.usecase;

import java.util.List;
import org.springframework.stereotype.Service;
import edu.tlu.book_store.domain.model.Book;
import edu.tlu.book_store.domain.repository.BookRepository;

@Service
public class GetAllBooksUseCase {
    private final BookRepository bookRepository;

    public GetAllBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> execute() {
        return bookRepository.findAll();
    }
}
