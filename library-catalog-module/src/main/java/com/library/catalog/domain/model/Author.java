package com.library.catalog.domain.model;

import com.library.catalog.domain.valueobject.AuthorId;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Author domain model
 */
public class Author {
    private final AuthorId id;
    private String name;
    private String biography;
    private LocalDate birthDate;
    private String nationality;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Author(AuthorId id,
                 String name,
                 String biography,
                 LocalDate birthDate,
                 String nationality,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Author create(String name, String biography, LocalDate birthDate, String nationality) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }

        AuthorId id = AuthorId.generate();
        LocalDateTime now = LocalDateTime.now();

        return new Author(id, name, biography, birthDate, nationality, now, now);
    }

    // Business logic: Update author information
    public void updateInfo(String name, String biography, LocalDate birthDate, String nationality) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        this.biography = biography;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public AuthorId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
