package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.infrastructure.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);

    // Find by publication
    List<ItemEntity> findByPublicationId(Long publicationId);
    long countByPublicationId(Long publicationId);

    @Query("SELECT COUNT(i) FROM ItemEntity i WHERE i.publicationId = :publicationId AND i.status = 'AVAILABLE'")
    long countAvailableByPublicationId(@Param("publicationId") Long publicationId);

    // Find by status
    List<ItemEntity> findByStatus(ItemStatus status);

    @Query("SELECT i FROM ItemEntity i WHERE i.publicationId = :publicationId AND i.status = 'AVAILABLE'")
    List<ItemEntity> findAvailableByPublicationId(@Param("publicationId") Long publicationId);
}
