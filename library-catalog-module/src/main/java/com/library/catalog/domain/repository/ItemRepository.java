package com.library.catalog.domain.repository;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.catalog.domain.valueobject.PublicationId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface for Item aggregate.
 */
public interface ItemRepository {
    // Basic CRUD
    Item save(Item item);
    Optional<Item> findById(ItemId id);
    Optional<Item> findByBarcode(Barcode barcode);
    List<Item> findAll();
    void delete(Item item);
    void deleteById(ItemId id);
    long count();

    // Query by publication
    List<Item> findByPublicationId(PublicationId publicationId);
    long countByPublicationId(PublicationId publicationId);
    long countAvailableByPublicationId(PublicationId publicationId);

    // Query by status
    List<Item> findByStatus(ItemStatus status);
    List<Item> findAvailableByPublicationId(PublicationId publicationId);

    // Existence checks
    boolean existsByBarcode(Barcode barcode);
    boolean existsById(ItemId id);
}
