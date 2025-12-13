package com.library.catalog.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * ISBN value object
 * Encapsulates ISBN with validation
 */
public class ISBN {
    private static final Pattern ISBN_PATTERN =
        Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");

    private final String value;

    private ISBN(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        String normalized = value.replaceAll("[- ]", "");
        if (!ISBN_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid ISBN format: " + value);
        }
        this.value = normalized;
    }

    public static ISBN of(String value) {
        return new ISBN(value);
    }

    public String getValue() {
        return value;
    }

    public boolean isISBN10() {
        return value.length() == 10;
    }

    public boolean isISBN13() {
        return value.length() == 13;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return Objects.equals(value, isbn.value);
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
