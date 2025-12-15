package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.Book;
import edu.tlu.book_store.domain.repository.BookRepository;

@Service
public class GetBookDetailUseCase {
    final BookRepository bookRepository;
    public GetBookDetailUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public Book execute(String id) {
        return bookRepository.findById(id).orElse(null);
    }
}
