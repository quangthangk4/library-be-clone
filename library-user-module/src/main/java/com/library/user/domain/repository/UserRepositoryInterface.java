package com.library.user.domain.repository;

import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User Aggregate Root
 * Follows Repository pattern from DDD
 */
public interface UserRepositoryInterface {

    /**
     * Save a user (insert or update)
     */
    UserAggregate save(UserAggregate user);

    /**
     * Find user by ID
     */
    Optional<UserAggregate> findById(UserId userId);

    /**
     * Find user by username
     */
    Optional<UserAggregate> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<UserAggregate> findByEmail(Email email);

    /**
     * Find all users
     */
    List<UserAggregate> findAll();

    /**
     * Find users by role name
     */
    List<UserAggregate> findByRoleName(String roleName);

    /**
     * Check if username already exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email already exists
     */
    boolean existsByEmail(Email email);

    /**
     * Delete a user
     */
    void delete(UserAggregate user);

    /**
     * Delete user by ID
     */
    void deleteById(UserId userId);

    /**
     * Count total number of users
     */
    long count();
}
