package com.library.circulation.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * ReservationId value object
 */
public class ReservationId {
    private final String value;

    private ReservationId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation ID cannot be null or empty");
        }
        this.value = value;
    }

    public static ReservationId of(String value) {
        return new ReservationId(value);
    }

    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID().toString());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId that = (ReservationId) o;
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
