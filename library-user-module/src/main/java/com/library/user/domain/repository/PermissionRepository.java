package com.library.user.domain.repository;

import com.library.user.domain.model.Permission;
import com.library.user.domain.valueobject.PermissionId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for Permission entity
 * Follows Repository pattern from DDD
 */
public interface PermissionRepository {

    /**
     * Save a permission (insert or update)
     */
    Permission save(Permission permission);

    /**
     * Find permission by ID
     */
    Optional<Permission> findById(PermissionId permissionId);

    /**
     * Find permission by name
     */
    Optional<Permission> findByName(String permissionName);

    /**
     * Find multiple permissions by their names
     */
    Set<Permission> findByNames(Set<String> permissionNames);

    /**
     * Find all permissions
     */
    List<Permission> findAll();

    /**
     * Check if permission exists by name
     */
    boolean existsByName(String permissionName);

    /**
     * Delete a permission
     */
    void delete(Permission permission);

    /**
     * Delete permission by ID
     */
    void deleteById(PermissionId permissionId);
}
