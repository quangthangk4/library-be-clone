package com.library.circulation.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * LoanId value object
 */
public class LoanId {
    private final String value;

    private LoanId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Loan ID cannot be null or empty");
        }
        this.value = value;
    }

    public static LoanId of(String value) {
        return new LoanId(value);
    }

    public static LoanId generate() {
        return new LoanId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanId loanId = (LoanId) o;
        return Objects.equals(value, loanId.value);
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
