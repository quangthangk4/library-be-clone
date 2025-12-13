package com.library.recommendation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing a book recommendation for a user
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    private Long id;
    private Long userId;
    private Long bookId;
    private RecommendationType type;
    private Double score; // Recommendation score/confidence (0.0 - 1.0)
    private String reason; // Why this book is recommended
    private Boolean isClicked; // User clicked on this recommendation
    private Boolean isBorrowed; // User borrowed this book after recommendation
    private LocalDateTime expiryDate; // Recommendation validity period
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if recommendation is still valid
     */
    public boolean isValid() {
        if (this.expiryDate == null) {
            return true;
        }
        return LocalDateTime.now().isBefore(this.expiryDate);
    }

    /**
     * Business logic: Check if recommendation is high confidence
     */
    public boolean isHighConfidence() {
        return this.score != null && this.score >= 0.8;
    }

    /**
     * Business logic: Mark as clicked
     */
    public void markAsClicked() {
        this.isClicked = true;
    }

    /**
     * Business logic: Mark as borrowed
     */
    public void markAsBorrowed() {
        this.isBorrowed = true;
        this.isClicked = true; // Borrowing implies clicking
    }

    /**
     * Business logic: Calculate conversion rate (borrowed / clicked)
     */
    public static double calculateConversionRate(long totalClicked, long totalBorrowed) {
        if (totalClicked == 0) {
            return 0.0;
        }
        return (double) totalBorrowed / totalClicked;
    }

    /**
     * Business logic: Validate recommendation data
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Recommendation type cannot be null");
        }
        if (score != null && (score < 0.0 || score > 1.0)) {
            throw new IllegalArgumentException("Score must be between 0.0 and 1.0");
        }
    }
}
