package com.library.recommendation.domain.model;

import com.library.recommendation.domain.valueobject.RecommendationId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Recommendation domain model
 */
public class Recommendation {
    private final RecommendationId id;
    private final String userId;
    private final List<String> bookIds;
    private final RecommendationType type;
    private final double score;
    private final String reason;
    private final LocalDateTime createdAt;

    public Recommendation(RecommendationId id,
                         String userId,
                         List<String> bookIds,
                         RecommendationType type,
                         double score,
                         String reason,
                         LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.bookIds = bookIds != null ? new ArrayList<>(bookIds) : new ArrayList<>();
        this.type = type;
        this.score = score;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    // Factory method
    public static Recommendation create(String userId,
                                       List<String> bookIds,
                                       RecommendationType type,
                                       double score,
                                       String reason) {
        if (bookIds == null || bookIds.isEmpty()) {
            throw new IllegalArgumentException("Recommended books cannot be empty");
        }
        if (score < 0 || score > 1) {
            throw new IllegalArgumentException("Score must be between 0 and 1");
        }

        RecommendationId id = RecommendationId.generate();
        LocalDateTime now = LocalDateTime.now();

        return new Recommendation(id, userId, bookIds, type, score, reason, now);
    }

    // Business logic: Check if recommendation is highly relevant
    public boolean isHighlyRelevant() {
        return this.score >= 0.7;
    }

    // Business logic: Check if recommendation is based on similar users
    public boolean isCollaborativeFiltering() {
        return this.type == RecommendationType.COLLABORATIVE_FILTERING;
    }

    // Business logic: Check if recommendation is based on content
    public boolean isContentBased() {
        return this.type == RecommendationType.CONTENT_BASED;
    }

    // Getters
    public RecommendationId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getBookIds() {
        return new ArrayList<>(bookIds);
    }

    public RecommendationType getType() {
        return type;
    }

    public double getScore() {
        return score;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
