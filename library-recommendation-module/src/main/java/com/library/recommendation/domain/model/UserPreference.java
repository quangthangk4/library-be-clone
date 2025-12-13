package com.library.recommendation.domain.model;

import com.library.recommendation.domain.valueobject.UserPreferenceId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UserPreference domain model
 */
public class UserPreference {
    private final UserPreferenceId id;
    private final String userId;
    private List<String> favoriteGenres;
    private List<String> favoriteAuthors;
    private List<String> preferredLanguages;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserPreference(UserPreferenceId id,
                         String userId,
                         List<String> favoriteGenres,
                         List<String> favoriteAuthors,
                         List<String> preferredLanguages,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.favoriteGenres = favoriteGenres != null ? new ArrayList<>(favoriteGenres) : new ArrayList<>();
        this.favoriteAuthors = favoriteAuthors != null ? new ArrayList<>(favoriteAuthors) : new ArrayList<>();
        this.preferredLanguages = preferredLanguages != null ? new ArrayList<>(preferredLanguages) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static UserPreference create(String userId) {
        UserPreferenceId id = UserPreferenceId.generate();
        LocalDateTime now = LocalDateTime.now();
        return new UserPreference(id, userId, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), now, now);
    }

    // Business logic: Add favorite genre
    public void addFavoriteGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty");
        }
        if (!this.favoriteGenres.contains(genre)) {
            this.favoriteGenres.add(genre);
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Business logic: Remove favorite genre
    public void removeFavoriteGenre(String genre) {
        if (this.favoriteGenres.remove(genre)) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Business logic: Add favorite author
    public void addFavoriteAuthor(String authorId) {
        if (authorId == null || authorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Author ID cannot be empty");
        }
        if (!this.favoriteAuthors.contains(authorId)) {
            this.favoriteAuthors.add(authorId);
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Business logic: Remove favorite author
    public void removeFavoriteAuthor(String authorId) {
        if (this.favoriteAuthors.remove(authorId)) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Business logic: Add preferred language
    public void addPreferredLanguage(String language) {
        if (language == null || language.trim().isEmpty()) {
            throw new IllegalArgumentException("Language cannot be empty");
        }
        if (!this.preferredLanguages.contains(language)) {
            this.preferredLanguages.add(language);
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Business logic: Remove preferred language
    public void removePreferredLanguage(String language) {
        if (this.preferredLanguages.remove(language)) {
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Business logic: Check if user has preferences
    public boolean hasPreferences() {
        return !favoriteGenres.isEmpty() || !favoriteAuthors.isEmpty() || !preferredLanguages.isEmpty();
    }

    // Getters
    public UserPreferenceId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getFavoriteGenres() {
        return new ArrayList<>(favoriteGenres);
    }

    public List<String> getFavoriteAuthors() {
        return new ArrayList<>(favoriteAuthors);
    }

    public List<String> getPreferredLanguages() {
        return new ArrayList<>(preferredLanguages);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
