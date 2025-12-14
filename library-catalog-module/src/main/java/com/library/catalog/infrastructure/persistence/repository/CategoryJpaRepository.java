package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryName(String categoryName);
    boolean existsByCategoryName(String categoryName);

    // Find root categories (no parent)
    List<CategoryEntity> findByParentCategoryIdIsNull();

    // Find children of a category
    List<CategoryEntity> findByParentCategoryId(Long parentId);

    // Check if category has children
    @Query("SELECT COUNT(c) > 0 FROM CategoryEntity c WHERE c.parentCategoryId = :categoryId")
    boolean hasChildren(@Param("categoryId") Long categoryId);

    long countByParentCategoryId(Long parentId);
}
