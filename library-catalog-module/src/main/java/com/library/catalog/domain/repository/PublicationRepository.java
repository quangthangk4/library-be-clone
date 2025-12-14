package com.library.catalog.domain.repository;

import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.valueobject.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface for Publication aggregate.
 */
public interface PublicationRepository {
    // Basic CRUD
    Publication save(Publication publication);
    Optional<Publication> findById(PublicationId id);
    Optional<Publication> findByISBN(ISBN isbn);
    List<Publication> findAll();
    Page<Publication> findAll(Pageable pageable);
    void delete(Publication publication);
    void deleteById(PublicationId id);
    long count();

    // Query by relationships
    List<Publication> findByAuthorId(AuthorId authorId);
    List<Publication> findByCategoryId(CategoryId categoryId);
    List<Publication> findByPublisherId(PublisherId publisherId);
    List<Publication> findByTagId(TagId tagId);

    // Search methods
    List<Publication> searchByTitle(String titleKeyword);
    Page<Publication> searchByTitle(String titleKeyword, Pageable pageable);

    // Existence checks
    boolean existsByISBN(ISBN isbn);
    boolean existsById(PublicationId id);
}
