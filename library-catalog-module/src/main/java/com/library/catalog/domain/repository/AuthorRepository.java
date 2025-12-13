package com.library.catalog.domain.repository;

import com.library.catalog.domain.model.Author;
import com.library.catalog.domain.valueobject.AuthorId;

import java.util.List;
import java.util.Optional;

/**
 * Author repository interface (Port)
 */
public interface AuthorRepository {
    Author save(Author author);
    Optional<Author> findById(AuthorId id);
    List<Author> findByName(String name);
    List<Author> findAll();
    void delete(AuthorId id);
}
