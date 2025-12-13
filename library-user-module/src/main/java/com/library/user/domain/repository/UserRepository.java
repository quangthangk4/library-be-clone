package com.library.user.domain.repository;

import com.library.user.domain.model.User;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * User repository interface (Port)
 * This is part of domain layer, implementation will be in infrastructure layer
 */
public interface UserRepository {

    /**
     * Save a user
     */
    User save(User user);

    /**
     * Find user by ID
     */
    Optional<User> findById(UserId id);

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(Email email);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(Email email);

    /**
     * Find all users
     */
    List<User> findAll();

    /**
     * Delete user
     */
    void delete(UserId id);
}
