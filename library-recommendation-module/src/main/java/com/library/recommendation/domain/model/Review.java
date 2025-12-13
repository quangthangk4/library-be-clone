package com.library.recommendation.domain.model;

import com.library.recommendation.domain.valueobject.Rating;
import com.library.recommendation.domain.valueobject.ReviewId;

import java.time.LocalDateTime;

/**
 * Review domain model
 */
public class Review {
    private final ReviewId id;
    private final String userId;
    private final String bookId;
    private Rating rating;
    private String comment;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review(ReviewId id,
                 String userId,
                 String bookId,
                 Rating rating,
                 String comment,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Review create(String userId, String bookId, Rating rating, String comment) {
        ReviewId id = ReviewId.generate();
        LocalDateTime now = LocalDateTime.now();
        return new Review(id, userId, bookId, rating, comment, now, now);
    }

    // Business logic: Update review
    public void update(Rating newRating, String newComment) {
        if (newRating != null) {
            this.rating = newRating;
        }
        this.comment = newComment;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Check if review is positive
    public boolean isPositive() {
        return this.rating.isPositive();
    }

    // Business logic: Check if review is negative
    public boolean isNegative() {
        return this.rating.isNegative();
    }

    // Business logic: Check if review has comment
    public boolean hasComment() {
        return this.comment != null && !this.comment.trim().isEmpty();
    }

    // Getters
    public ReviewId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public Rating getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
