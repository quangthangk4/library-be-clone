package com.library.user.domain.repository;

import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.valueobject.RoleId;

import java.util.Optional;

/**
 * Repository interface for Role Aggregate
 * Follows Repository pattern from DDD
 */
public interface RoleRepository {

    /**
     * Save a role (insert or update)
     */
    RoleAggregate save(RoleAggregate role);

    /**
     * Find role by ID
     */
    Optional<RoleAggregate> findById(RoleId roleId);

    /**
     * Find role by name
     */
    Optional<RoleAggregate> findByName(String roleName);

    /**
     * Check if role exists by name
     */
    boolean existsByName(String roleName);

    /**
     * Delete a role
     */
    void delete(RoleAggregate role);

    /**
     * Delete role by ID
     */
    void deleteById(RoleId roleId);
}
