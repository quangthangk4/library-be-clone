package com.library.user.domain.repository;

import com.library.user.domain.entities.User;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User Aggregate Root
 * Follows Repository pattern from DDD
 */
public interface UserRepository {

    /**
     * Save a user (insert or update)
     */
    User save(User user);

    /**
     * Find user by ID
     */
    Optional<User> findById(UserId userId);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(Email email);

    /**
     * Find all users
     */
    List<User> findAll();

    /**
     * Find users by role name
     */
    List<User> findByRoleName(String roleName);

    /**
     * Check if email already exists
     */
    boolean existsByEmail(Email email);

    /**
     * Delete a user
     */
    void delete(User user);

    /**
     * Delete user by ID
     */
    void deleteById(UserId userId);

    /**
     * Count the total number of users
     */
    long count();
}
