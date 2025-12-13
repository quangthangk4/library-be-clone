package com.library.catalog.domain.repository;

import com.library.catalog.domain.model.Category;
import com.library.catalog.domain.valueobject.CategoryId;

import java.util.List;
import java.util.Optional;

/**
 * Category repository interface (Port)
 */
public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
    void delete(CategoryId id);
}
