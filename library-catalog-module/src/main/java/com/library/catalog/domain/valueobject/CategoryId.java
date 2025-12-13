package com.library.catalog.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * CategoryId value object
 */
public class CategoryId {
    private final String value;

    private CategoryId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Category ID cannot be null or empty");
        }
        this.value = value;
    }

    public static CategoryId of(String value) {
        return new CategoryId(value);
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryId that = (CategoryId) o;
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
