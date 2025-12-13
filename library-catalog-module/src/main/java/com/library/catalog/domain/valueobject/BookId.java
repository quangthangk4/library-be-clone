package com.library.catalog.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * BookId value object
 */
public class BookId {
    private final String value;

    private BookId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        this.value = value;
    }

    public static BookId of(String value) {
        return new BookId(value);
    }

    public static BookId generate() {
        return new BookId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return Objects.equals(value, bookId.value);
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
