package com.library.catalog.domain.service;

import com.library.catalog.domain.model.Book;
import com.library.catalog.domain.repository.BookRepository;
import com.library.catalog.domain.valueobject.ISBN;

/**
 * Book domain service
 */
public class BookDomainService {
    private final BookRepository bookRepository;

    public BookDomainService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Check if ISBN is unique
     */
    public boolean isIsbnUnique(ISBN isbn) {
        return bookRepository.findByIsbn(isbn).isEmpty();
    }

    /**
     * Validate book for creation
     */
    public void validateForCreation(Book book) {
        if (!isIsbnUnique(book.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN already exists: " + book.getIsbn());
        }
    }

    /**
     * Validate book can be borrowed
     */
    public void validateCanBeBorrowed(Book book) {
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing");
        }
    }

    /**
     * Validate book can be returned
     */
    public void validateCanBeReturned(Book book) {
        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new IllegalStateException("All copies are already available");
        }
    }
}
