package com.library.recommendation.domain.valueobject;

import lombok.Value;

@Value
public class RatingMetadata {
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    int value; // private final int value (do lambok)
    String comment;

    private RatingMetadata(int value, String comment) {
        if (value < MIN_RATING || value > MAX_RATING) {
            throw new IllegalArgumentException(
                String.format("Rating must be between %d and %d", MIN_RATING, MAX_RATING)
            );
        }
        if (comment != null && comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }

        this.comment = comment;
        this.value = value;
    }

    public static RatingMetadata of(int value, String comment) {
        return new RatingMetadata(value, comment);
    }

    public boolean isPositive() {
        return value >= 4;
    }

    public boolean isNegative() {
        return value <= 2;
    }
}
