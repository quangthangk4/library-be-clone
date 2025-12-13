package com.library.catalog.domain.repository;

import com.library.catalog.domain.model.Book;
import com.library.catalog.domain.valueobject.BookId;
import com.library.catalog.domain.valueobject.ISBN;

import java.util.List;
import java.util.Optional;

/**
 * Book repository interface (Port)
 */
public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(BookId id);
    Optional<Book> findByIsbn(ISBN isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthorId(String authorId);
    List<Book> findByCategoryId(String categoryId);
    List<Book> findByPublisherId(String publisherId);
    List<Book> findAvailableBooks();
    List<Book> findAll();
    void delete(BookId id);
}
