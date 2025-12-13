package com.library.user.domain.model;

import com.library.user.domain.valueobject.PermissionId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Permission domain model - Represents a specific permission/right in the system
 * Examples: BORROW_BOOK, MANAGE_USERS, ADD_PUBLICATION, etc.
 */
@Getter
public class Permission {
    private final PermissionId id;
    private String permissionName;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Permission(PermissionId id,
                     String permissionName,
                     String description,
                     LocalDateTime createdAt,
                     LocalDateTime updatedAt) {
        this.id = id;
        this.permissionName = permissionName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create a new permission
     */
    public static Permission create(String permissionName, String description) {
        validatePermissionName(permissionName);
        PermissionId id = PermissionId.generate();
        LocalDateTime now = LocalDateTime.now();
        return new Permission(id, permissionName, description, now, now);
    }

    /**
     * Business logic: Update permission details
     */
    public void updateDetails(String permissionName, String description) {
        validatePermissionName(permissionName);
        this.permissionName = permissionName;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Validates permission name format (ACTION_RESOURCE convention)
     */
    private static void validatePermissionName(String permissionName) {
        if (permissionName == null || permissionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Permission name cannot be null or empty");
        }

        // Convention: Permission names should be in UPPERCASE and follow ACTION_RESOURCE pattern
        if (!permissionName.matches("^[A-Z_]+$")) {
            throw new IllegalArgumentException(
                "Permission name must be uppercase and follow ACTION_RESOURCE pattern (e.g., BORROW_BOOK)"
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
