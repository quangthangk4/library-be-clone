package com.library.catalog.domain.repository;

import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.valueobject.TagId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface for Tag entity.
 */
public interface TagRepository {
    Tag save(Tag tag);
    Optional<Tag> findById(TagId id);
    Optional<Tag> findByName(String tagName);
    List<Tag> findAll();
    List<Tag> findByIds(List<TagId> ids);
    boolean existsByName(String tagName);
    void delete(Tag tag);
    void deleteById(TagId id);
    long count();
}
