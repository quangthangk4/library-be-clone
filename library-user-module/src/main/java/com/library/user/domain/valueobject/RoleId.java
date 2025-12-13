package com.library.user.domain.valueobject;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object representing Role identifier
 */
@Getter
public class RoleId {
    private final Long value;

    private RoleId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Role ID must be a positive number");
        }
        this.value = value;
    }

    public static RoleId of(Long value) {
        return new RoleId(value);
    }

    public static RoleId generate() {
        // In real implementation, this would be handled by the database
        // For now, we use a placeholder
        return new RoleId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleId roleId = (RoleId) o;
        return Objects.equals(value, roleId.value);
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
