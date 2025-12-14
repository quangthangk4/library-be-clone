package com.library.catalog.domain.entities;

import com.library.catalog.domain.valueobject.AuthorId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

/**
 * Author entity - represents a book author/writer.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {
    private final AuthorId id;
    private String authorName;
    private String biography;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;

    /**
     * Factory method to create a new Author with name only.
     */
    public static Author create(String authorName) {
        validateAuthorName(authorName);
        return new Author(AuthorId.generate(), authorName.trim(), null, null, null);
    }

    /**
     * Factory method to create Author with full details.
     */
    public static Author create(String authorName, String biography,
                               LocalDate dateOfBirth, LocalDate dateOfDeath) {
        validateAuthorName(authorName);
        validateLifeDates(dateOfBirth, dateOfDeath);
        return new Author(
            AuthorId.generate(),
            authorName.trim(),
            biography != null ? biography.trim() : null,
            dateOfBirth,
            dateOfDeath
        );
    }

    /**
     * Factory method for mapper (when loading from a database).
     */
    public static Author of(AuthorId id, String authorName, String biography,
                          LocalDate dateOfBirth, LocalDate dateOfDeath) {
        validateAuthorName(authorName);
        validateLifeDates(dateOfBirth, dateOfDeath);
        return new Author(id, authorName.trim(), biography, dateOfBirth, dateOfDeath);
    }

    /**
     * Updates author biography.
     */
    public void updateBiography(String newBiography) {
        this.biography = newBiography != null ? newBiography.trim() : null;
    }

    /**
     * Updates author life dates.
     */
    public void updateLifeDates(LocalDate birth, LocalDate death) {
        validateLifeDates(birth, death);
        this.dateOfBirth = birth;
        this.dateOfDeath = death;
    }

    public void updateName(String authorName){
        validateAuthorName(authorName);
        this.authorName = authorName.trim();
    }

    /**
     * Checks if the author is still alive (no death date recorded).
     */
    public boolean isAlive() {
        return dateOfDeath == null;
    }

    /**
     * Gets author's age if alive, or age at death.
     */
    public Integer getAge() {
        if (dateOfBirth == null) return null;
        LocalDate endDate = dateOfDeath != null ? dateOfDeath : LocalDate.now();
        return Period.between(dateOfBirth, endDate).getYears();
    }

    private static void validateAuthorName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Author name must not exceed 100 characters");
        }
    }

    private static void validateLifeDates(LocalDate birth, LocalDate death) {
        if (birth != null && death != null && death.isBefore(birth)) {
            throw new IllegalArgumentException("Date of death cannot be before date of birth");
        }
        if (birth != null && birth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
    }
}
