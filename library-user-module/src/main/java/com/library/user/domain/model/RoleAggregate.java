package com.library.user.domain.model;

import com.library.user.domain.valueobject.RoleId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Role Aggregate Root
 * Manages the collection of permissions that define a role
 */
@Getter
public class RoleAggregate {
    private final RoleId id;
    private String roleName;
    private String description;
    private final Set<Permission> permissions;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoleAggregate(RoleId id,
                        String roleName,
                        String description,
                        Set<Permission> permissions,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.permissions = new HashSet<>(permissions != null ? permissions : new HashSet<>());
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create a new role
     */
    public static RoleAggregate create(String roleName, String description) {
        validateRoleName(roleName);
        RoleId id = RoleId.generate();
        LocalDateTime now = LocalDateTime.now();
        return new RoleAggregate(id, roleName, description, new HashSet<>(), now, now);
    }

    /**
     * Business logic: Add a permission to this role
     */
    public void addPermission(Permission permission) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        if (this.permissions.contains(permission)) {
            throw new IllegalStateException("Permission already exists in this role");
        }

        this.permissions.add(permission);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Remove a permission from this role
     */
    public void removePermission(Permission permission) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }

        if (!this.permissions.contains(permission)) {
            throw new IllegalStateException("Permission does not exist in this role");
        }

        this.permissions.remove(permission);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Check if role has a specific permission
     */
    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    /**
     * Business logic: Check if role has a specific permission by name
     */
    public boolean hasPermissionByName(String permissionName) {
        return this.permissions.stream()
            .anyMatch(p -> p.getPermissionName().equals(permissionName));
    }

    /**
     * Get immutable view of permissions
     */
    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    /**
     * Business logic: Update role details
     */
    public void updateDetails(String roleName, String description) {
        validateRoleName(roleName);
        this.roleName = roleName;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Validates role name
     */
    private static void validateRoleName(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        if (roleName.length() > 50) {
            throw new IllegalArgumentException("Role name cannot exceed 50 characters");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleAggregate that = (RoleAggregate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
