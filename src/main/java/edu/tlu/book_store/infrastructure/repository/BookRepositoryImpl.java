package edu.tlu.book_store.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edu.tlu.book_store.domain.model.Book;
import edu.tlu.book_store.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        return em.createQuery(
        "SELECT DISTINCT b FROM Book b " +
        "LEFT JOIN FETCH b.category " +
        "LEFT JOIN FETCH b.images i " ,
       
        Book.class
    ).getResultList();
    }

    @Override
    public Optional<Book> findById(String id) {
        return em.createQuery(
        "SELECT b FROM Book b " +
        "LEFT JOIN FETCH b.category " +
        "LEFT JOIN FETCH b.images " +
        "LEFT JOIN FETCH b.specifications " +
        "LEFT JOIN FETCH b.attributes " +
        "LEFT JOIN FETCH b.sellers " +
        "WHERE b.id = :id",
        Book.class
    ).setParameter("id", id)
     .getResultStream()
     .findFirst();
    }

    @Transactional
    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            book = em.merge(book);
        }
        return book;
    }

    @Transactional
    @Override
    public boolean deleteById(String id) {
        Book book = em.find(Book.class, id);
        if (book == null) return false;
        em.remove(book);
        return true;
    }
}

