package com.library.catalog.domain.repository;

import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.valueobject.CategoryId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface for Category entity.
 */
public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    Optional<Category> findByName(String categoryName);
    List<Category> findAll();
    List<Category> findByIds(List<CategoryId> ids);
    List<Category> findRootCategories(); // Categories with null parent
    List<Category> findByParentId(CategoryId parentId);
    boolean existsByName(String categoryName);
    boolean hasChildren(CategoryId categoryId);
    void delete(Category category);
    void deleteById(CategoryId id);
    long count();
}
