package com.library.catalog.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain entity representing a book author
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String biography;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String nationality;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if author is still alive
     */
    public boolean isAlive() {
        return this.deathDate == null;
    }

    /**
     * Business logic: Validate author data
     */
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }
        if (birthDate != null && deathDate != null && birthDate.isAfter(deathDate)) {
            throw new IllegalArgumentException("Birth date cannot be after death date");
        }
    }
}
