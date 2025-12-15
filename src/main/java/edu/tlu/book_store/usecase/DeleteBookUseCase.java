package edu.tlu.book_store.usecase;

import java.util.Optional;

import edu.tlu.book_store.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
@Service
public class DeleteBookUseCase {

    private final BookRepository bookRepository;

    public DeleteBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean execute(String id) {
        Optional<?> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return false;
        }
        return bookRepository.deleteById(id);
    }
}
