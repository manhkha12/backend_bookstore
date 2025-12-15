package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.Book;
import edu.tlu.book_store.domain.repository.BookRepository;
@Service
public class AddBookUseCase {
    private final BookRepository bookRepository;

    public AddBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book execute(Book book) {
        return bookRepository.save(book);
    }
}

