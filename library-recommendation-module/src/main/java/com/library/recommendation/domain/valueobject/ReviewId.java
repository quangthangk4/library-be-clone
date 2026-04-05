package com.library.recommendation.domain.valueobject;

import com.library.shared.util.TsIdGenerator;
import lombok.Value;

@Value
public class ReviewId {
    // properties
    Long value; // private final Long value;


    private ReviewId(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("Review ID cannot be null");
        }
        this.value = value;
    }

    public static ReviewId of(Long value) {
        return new ReviewId(value);
    }

    public static ReviewId generate() {
        return new ReviewId(TsIdGenerator.next());
    }
}
