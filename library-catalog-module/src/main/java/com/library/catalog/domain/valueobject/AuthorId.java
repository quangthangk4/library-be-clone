package com.library.catalog.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * AuthorId value object
 */
public class AuthorId {
    private final String value;

    private AuthorId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Author ID cannot be null or empty");
        }
        this.value = value;
    }

    public static AuthorId of(String value) {
        return new AuthorId(value);
    }

    public static AuthorId generate() {
        return new AuthorId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorId authorId = (AuthorId) o;
        return Objects.equals(value, authorId.value);
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
