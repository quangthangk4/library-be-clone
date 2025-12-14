package com.library.catalog.domain.repository;

import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.valueobject.AuthorId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface for Author entity.
 */
public interface AuthorRepository {
    Author save(Author author);
    Optional<Author> findById(AuthorId id);
    Optional<Author> findByName(String authorName);
    List<Author> findAll();
    List<Author> findByIds(List<AuthorId> ids);
    List<Author> searchByName(String nameKeyword);
    boolean existsByName(String authorName);
    void delete(Author author);
    void deleteById(AuthorId id);
    long count();
}
