package com.library.recommendation.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * ReviewId value object
 */
public class ReviewId {
    private final String value;

    private ReviewId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Review ID cannot be null or empty");
        }
        this.value = value;
    }

    public static ReviewId of(String value) {
        return new ReviewId(value);
    }

    public static ReviewId generate() {
        return new ReviewId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(value, reviewId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
