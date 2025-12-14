package com.library.user.domain.service;

import com.library.user.domain.entities.Role;
import com.library.user.domain.entities.User;
import com.library.user.domain.valueobject.Email;

/**
 * Domain Service interface for User business logic
 */
public interface UserDomainService {

    /**
     * Validate that email is unique across the system
     * @throws IllegalArgumentException if email already exists
     */
    void validateUniqueEmail(Email email);

    /**
     * Assign a default role to a newly created user
     */
    void assignDefaultRole(User user);

    /**
     * Validate that a role change is allowed
     * @throws IllegalArgumentException if a role change is not valid
     */
    void validateRoleChange(User user, Role newRole);

    /**
     * Check if email is available
     */
    boolean isEmailAvailable(Email email);
}
