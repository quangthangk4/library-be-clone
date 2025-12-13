package com.library.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Domain entity representing a library user
 * This is a plain Java object without any persistence annotations
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if the user is active
     */
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    /**
     * Business logic: Check if user can borrow books
     */
    public boolean canBorrowBooks() {
        return this.status == UserStatus.ACTIVE;
    }

    /**
     * Business logic: Check if user is admin
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    /**
     * Business logic: Check if user is librarian
     */
    public boolean isLibrarian() {
        return this.role == Role.LIBRARIAN;
    }

    /**
     * Business logic: Get full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Business logic: Validate user data
     */
    public void validate() {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }
}
