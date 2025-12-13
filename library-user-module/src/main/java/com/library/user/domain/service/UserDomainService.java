package com.library.user.domain.service;

import com.library.user.domain.model.Permission;
import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.valueobject.Email;

/**
 * Domain Service interface for User business logic
 */
public interface UserDomainService {

    /**
     * Validate that username is unique across the system
     * @throws IllegalArgumentException if username already exists
     */
    void validateUniqueUsername(String username);

    /**
     * Validate that email is unique across the system
     * @throws IllegalArgumentException if email already exists
     */
    void validateUniqueEmail(Email email);

    /**
     * Check if user can perform a specific action based on their permissions
     */
    boolean canUserPerformAction(UserAggregate user, String permissionName);

    /**
     * Assign default role to a newly created user
     */
    void assignDefaultRole(UserAggregate user);

    /**
     * Validate that a role change is allowed
     * @throws IllegalArgumentException if role change is not valid
     */
    void validateRoleChange(UserAggregate user, RoleAggregate newRole);

    /**
     * Validate that a permission can be assigned to a role
     * @throws IllegalArgumentException if assignment is not valid
     */
    void validatePermissionAssignment(RoleAggregate role, Permission permission);

    /**
     * Check if username is available
     */
    boolean isUsernameAvailable(String username);

    /**
     * Check if email is available
     */
    boolean isEmailAvailable(Email email);
}
