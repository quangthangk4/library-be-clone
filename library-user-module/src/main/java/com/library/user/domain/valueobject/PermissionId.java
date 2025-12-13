package com.library.user.domain.valueobject;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object representing Permission identifier
 */
@Getter
public class PermissionId {
    private final Long value;

    private PermissionId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Permission ID must be a positive number");
        }
        this.value = value;
    }

    public static PermissionId of(Long value) {
        return new PermissionId(value);
    }

    public static PermissionId generate() {
        // In real implementation, this would be handled by the database
        // For now, we use a placeholder
        return new PermissionId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionId that = (PermissionId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
