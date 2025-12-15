package edu.tlu.book_store.domain.repository;

import java.util.List;
import java.util.Optional;

import edu.tlu.book_store.domain.model.Book;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(String id);
    Book save(Book book);
    boolean deleteById(String id);
}

