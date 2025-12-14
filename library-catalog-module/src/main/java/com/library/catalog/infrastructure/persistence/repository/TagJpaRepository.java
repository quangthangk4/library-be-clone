package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.infrastructure.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByTagName(String tagName);
    boolean existsByTagName(String tagName);
}
