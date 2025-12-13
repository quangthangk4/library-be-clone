package com.library.recommendation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain entity representing user's reading preferences
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {
    private Long id;
    private Long userId;
    private List<Long> favoriteCategories;
    private List<Long> favoriteAuthors;
    private List<String> favoriteGenres;
    private List<String> preferredLanguages;
    private Integer preferredPageCountMin;
    private Integer preferredPageCountMax;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if user has set preferences
     */
    public boolean hasPreferences() {
        return (favoriteCategories != null && !favoriteCategories.isEmpty()) ||
               (favoriteAuthors != null && !favoriteAuthors.isEmpty()) ||
               (favoriteGenres != null && !favoriteGenres.isEmpty());
    }

    /**
     * Business logic: Add favorite category
     */
    public void addFavoriteCategory(Long categoryId) {
        if (this.favoriteCategories == null) {
            this.favoriteCategories = new java.util.ArrayList<>();
        }
        if (!this.favoriteCategories.contains(categoryId)) {
            this.favoriteCategories.add(categoryId);
        }
    }

    /**
     * Business logic: Add favorite author
     */
    public void addFavoriteAuthor(Long authorId) {
        if (this.favoriteAuthors == null) {
            this.favoriteAuthors = new java.util.ArrayList<>();
        }
        if (!this.favoriteAuthors.contains(authorId)) {
            this.favoriteAuthors.add(authorId);
        }
    }

    /**
     * Business logic: Validate preference data
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (preferredPageCountMin != null && preferredPageCountMin < 0) {
            throw new IllegalArgumentException("Minimum page count cannot be negative");
        }
        if (preferredPageCountMax != null && preferredPageCountMax < 0) {
            throw new IllegalArgumentException("Maximum page count cannot be negative");
        }
        if (preferredPageCountMin != null && preferredPageCountMax != null &&
            preferredPageCountMin > preferredPageCountMax) {
            throw new IllegalArgumentException("Minimum page count cannot be greater than maximum");
        }
    }
}
