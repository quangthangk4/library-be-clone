package com.library.recommendation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing a book review by a user
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long userId;
    private Long bookId;
    private Integer rating; // 1-5 stars
    private String title;
    private String comment;
    private LocalDateTime reviewDate;
    private Integer helpfulCount; // Number of users who found this review helpful
    private Boolean isVerifiedPurchase; // User actually borrowed this book
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if review has minimum rating
     */
    public boolean isPositive() {
        return this.rating >= 4;
    }

    /**
     * Business logic: Check if review is detailed
     */
    public boolean isDetailed() {
        return this.comment != null && this.comment.length() >= 50;
    }

    /**
     * Business logic: Increment helpful count
     */
    public void markAsHelpful() {
        if (this.helpfulCount == null) {
            this.helpfulCount = 0;
        }
        this.helpfulCount++;
    }

    /**
     * Business logic: Validate review data
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (comment != null && comment.length() > 2000) {
            throw new IllegalArgumentException("Comment cannot exceed 2000 characters");
        }
    }
}
