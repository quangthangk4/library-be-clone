package com.library.catalog.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain entity representing a book in the library catalog
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private LocalDate publishedDate;
    private Integer totalCopies;
    private Integer availableCopies;
    private String language;
    private Integer pageCount;
    private BookStatus status;

    // Relationships (using IDs to avoid circular dependencies)
    private Long publisherId;
    private Long categoryId;
    private List<Long> authorIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if book is available for borrowing
     */
    public boolean isAvailable() {
        return this.status == BookStatus.AVAILABLE && this.availableCopies > 0;
    }

    /**
     * Business logic: Check if book can be reserved
     */
    public boolean canBeReserved() {
        return this.availableCopies == 0 && this.status == BookStatus.BORROWED;
    }

    /**
     * Business logic: Decrease available copies when borrowed
     */
    public void decreaseAvailableCopies() {
        if (this.availableCopies <= 0) {
            throw new IllegalStateException("No available copies to borrow");
        }
        this.availableCopies--;
    }

    /**
     * Business logic: Increase available copies when returned
     */
    public void increaseAvailableCopies() {
        if (this.availableCopies >= this.totalCopies) {
            throw new IllegalStateException("Cannot increase available copies beyond total copies");
        }
        this.availableCopies++;
    }

    /**
     * Business logic: Validate book data
     */
    public void validate() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        if (isbn == null || !isbn.matches("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")) {
            throw new IllegalArgumentException("Invalid ISBN format");
        }
        if (totalCopies == null || totalCopies < 0) {
            throw new IllegalArgumentException("Total copies must be a non-negative number");
        }
        if (availableCopies == null || availableCopies < 0 || availableCopies > totalCopies) {
            throw new IllegalArgumentException("Available copies must be between 0 and total copies");
        }
        if (pageCount != null && pageCount < 0) {
            throw new IllegalArgumentException("Page count must be a positive number");
        }
    }
}
