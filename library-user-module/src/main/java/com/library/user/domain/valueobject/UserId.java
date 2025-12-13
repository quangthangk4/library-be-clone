package com.library.user.domain.valueobject;

import io.hypersistence.tsid.TSID;
import lombok.Getter;

import java.util.Objects;

/**
 * UserId value object
 * Encapsulates user identifier
 */
@Getter
public class UserId {
    private final Long value;

    private UserId(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        this.value = value;
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

    public static UserId generate() {
        return new UserId(TSID.fast().toLong());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
