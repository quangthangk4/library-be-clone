package com.library.circulation.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * FineId value object
 */
public class FineId {
    private final String value;

    private FineId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Fine ID cannot be null or empty");
        }
        this.value = value;
    }

    public static FineId of(String value) {
        return new FineId(value);
    }

    public static FineId generate() {
        return new FineId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FineId fineId = (FineId) o;
        return Objects.equals(value, fineId.value);
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
