package com.library.recommendation.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * UserPreferenceId value object
 */
public class UserPreferenceId {
    private final String value;

    private UserPreferenceId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("UserPreference ID cannot be null or empty");
        }
        this.value = value;
    }

    public static UserPreferenceId of(String value) {
        return new UserPreferenceId(value);
    }

    public static UserPreferenceId generate() {
        return new UserPreferenceId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPreferenceId that = (UserPreferenceId) o;
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
