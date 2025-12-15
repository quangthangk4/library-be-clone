package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.infrastructure.persistence.entity.PublicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PublicationJpaRepository extends JpaRepository<PublicationEntity, Long>, JpaSpecificationExecutor<PublicationEntity> {
    Optional<PublicationEntity> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    // Search by title
    @Query("SELECT p FROM PublicationEntity p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<PublicationEntity> searchByTitle(@Param("keyword") String keyword);

    @Query("SELECT p FROM PublicationEntity p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<PublicationEntity> searchByTitle(@Param("keyword") String keyword, Pageable pageable);

    // Find by relationships (via junction tables)
    @Query("SELECT DISTINCT p FROM PublicationEntity p JOIN p.publicationAuthors pa WHERE pa.authorId = :authorId")
    List<PublicationEntity> findByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT DISTINCT p FROM PublicationEntity p JOIN p.publicationCategories pc WHERE pc.categoryId = :categoryId")
    List<PublicationEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    List<PublicationEntity> findByPublisherId(Long publisherId);

    @Query("SELECT DISTINCT p FROM PublicationEntity p JOIN p.publicationTags pt WHERE pt.tagId = :tagId")
    List<PublicationEntity> findByTagId(@Param("tagId") Long tagId);
}
