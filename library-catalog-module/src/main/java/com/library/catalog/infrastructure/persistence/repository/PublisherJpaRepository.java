package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.infrastructure.persistence.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherJpaRepository extends JpaRepository<PublisherEntity, Long> {
    Optional<PublisherEntity> findByPublisherName(String publisherName);
    boolean existsByPublisherName(String publisherName);
}
