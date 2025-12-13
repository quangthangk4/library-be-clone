package com.library.catalog.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * PublisherId value object
 */
public class PublisherId {
    private final String value;

    private PublisherId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Publisher ID cannot be null or empty");
        }
        this.value = value;
    }

    public static PublisherId of(String value) {
        return new PublisherId(value);
    }

    public static PublisherId generate() {
        return new PublisherId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublisherId that = (PublisherId) o;
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
