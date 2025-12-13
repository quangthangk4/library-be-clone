package com.library.recommendation.domain.valueobject;

import java.util.Objects;

/**
 * Rating value object
 * Encapsulates rating score (1-5 stars)
 */
public class Rating {
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    private final int value;

    private Rating(int value) {
        if (value < MIN_RATING || value > MAX_RATING) {
            throw new IllegalArgumentException(
                String.format("Rating must be between %d and %d", MIN_RATING, MAX_RATING)
            );
        }
        this.value = value;
    }

    public static Rating of(int value) {
        return new Rating(value);
    }

    public int getValue() {
        return value;
    }

    public boolean isPositive() {
        return value >= 4;
    }

    public boolean isNegative() {
        return value <= 2;
    }

    public boolean isNeutral() {
        return value == 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return value == rating.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value + " stars";
    }
}
