package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.infrastructure.persistence.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByAuthorName(String authorName);
    boolean existsByAuthorName(String authorName);

    @Query("SELECT a FROM AuthorEntity a WHERE LOWER(a.authorName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AuthorEntity> searchByName(@Param("keyword") String keyword);
}
