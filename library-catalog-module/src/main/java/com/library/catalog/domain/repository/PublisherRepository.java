package com.library.catalog.domain.repository;

import com.library.catalog.domain.model.Publisher;
import com.library.catalog.domain.valueobject.PublisherId;

import java.util.List;
import java.util.Optional;

/**
 * Publisher repository interface (Port)
 */
public interface PublisherRepository {
    Publisher save(Publisher publisher);
    Optional<Publisher> findById(PublisherId id);
    Optional<Publisher> findByName(String name);
    List<Publisher> findAll();
    void delete(PublisherId id);
}
