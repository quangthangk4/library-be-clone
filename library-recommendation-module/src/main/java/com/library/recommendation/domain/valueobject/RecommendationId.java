package com.library.recommendation.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * RecommendationId value object
 */
public class RecommendationId {
    private final String value;

    private RecommendationId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Recommendation ID cannot be null or empty");
        }
        this.value = value;
    }

    public static RecommendationId of(String value) {
        return new RecommendationId(value);
    }

    public static RecommendationId generate() {
        return new RecommendationId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationId that = (RecommendationId) o;
        return Objects.equals(value, that.value);
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
