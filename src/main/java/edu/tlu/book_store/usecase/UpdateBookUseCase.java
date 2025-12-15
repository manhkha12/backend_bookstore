package edu.tlu.book_store.usecase;

import java.util.Optional;

import edu.tlu.book_store.domain.model.Book;
import edu.tlu.book_store.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
@Service
public class UpdateBookUseCase {

    private final BookRepository bookRepository;

    public UpdateBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book execute(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getId());
        if (existingBook.isEmpty()) {
            throw new RuntimeException("Book not found with id: " + book.getId());
        }
        // Cập nhật sách
        return bookRepository.save(book);
    }
}
