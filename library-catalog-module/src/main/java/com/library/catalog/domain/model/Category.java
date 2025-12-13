package com.library.catalog.domain.model;

import com.library.catalog.domain.valueobject.CategoryId;

import java.time.LocalDateTime;

/**
 * Category domain model
 */
public class Category {
    private final CategoryId id;
    private String name;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Category(CategoryId id,
                   String name,
                   String description,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Category create(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        CategoryId id = CategoryId.generate();
        LocalDateTime now = LocalDateTime.now();

        return new Category(id, name, description, now, now);
    }

    // Business logic: Update category information
    public void updateInfo(String name, String description) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public CategoryId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
