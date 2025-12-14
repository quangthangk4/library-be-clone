package com.library.catalog.domain.repository;

import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.domain.valueobject.PublisherId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface for Publisher entity.
 */
public interface PublisherRepository {
    Publisher save(Publisher publisher);
    Optional<Publisher> findById(PublisherId id);
    Optional<Publisher> findByName(String publisherName);
    List<Publisher> findAll();
    boolean existsByName(String publisherName);
    void delete(Publisher publisher);
    void deleteById(PublisherId id);
    long count();
}
