package com.library.catalog.domain.entities;

import com.library.catalog.domain.valueobject.CategoryId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    private final CategoryId id;
    private String categoryName;
    private CategoryId parentCategoryId; // null for root categories

    /**
     * Factory method to create a root category (no parent).
     */
    public static Category createRoot(String categoryName) {
        validateCategoryName(categoryName);
        return new Category(CategoryId.generate(), categoryName.trim(), null);
    }

    /**
     * Factory method to create a child category.
     */
    public static Category createChild(String categoryName, CategoryId parentCategoryId) {
        validateCategoryName(categoryName);
        if (parentCategoryId == null) {
            throw new IllegalArgumentException("Parent category ID cannot be null for child category");
        }
        return new Category(CategoryId.generate(), categoryName.trim(), parentCategoryId);
    }

    /**
     * Factory method for mapper (when loading from a database).
     */
    public static Category of(CategoryId id, String categoryName, CategoryId parentCategoryId) {
        validateCategoryName(categoryName);
        return new Category(id, categoryName.trim(), parentCategoryId);
    }

    /**
     * Renames the category.
     */
    public void rename(String newCategoryName) {
        validateCategoryName(newCategoryName);
        this.categoryName = newCategoryName.trim();
    }

    public void changeParent(CategoryId parentCategoryId){
        this.parentCategoryId = parentCategoryId;
    }

    /**
     * Moves category to a different parent.
     * Pass null to make it a root category.
     */
    public void moveToParent(CategoryId newParentId) {
        // Business rule: Cannot set a parent to itself
        if (newParentId != null && newParentId.equals(this.id)) {
            throw new IllegalArgumentException("Category cannot be its own parent");
        }
        this.parentCategoryId = newParentId;
    }

    /**
     * Checks if this is a root category (no parent).
     */
    public boolean isRoot() {
        return parentCategoryId == null;
    }

    /**
     * Checks if this category has a parent.
     */
    public boolean hasParent() {
        return parentCategoryId != null;
    }

    private static void validateCategoryName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Category name must not exceed 100 characters");
        }
    }
}
