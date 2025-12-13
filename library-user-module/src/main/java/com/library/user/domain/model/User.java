package com.library.user.domain.model;

import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserProfile;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * User domain model - Pure Java object with business logic
 */
@Getter
public class User {
    // Getters
    private final UserId id;
    private String username;
    private Email email;
    private String passwordHash;
    private UserProfile profile;
    private Role role;
    private UserStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(UserId id,
                String username,
                Email email,
                String passwordHash,
                UserProfile profile,
                Role role,
                UserStatus status,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.profile = profile;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method để tạo user mới
    public static User create(String username, Email email, String passwordHash, UserProfile profile, Role role) {
        UserId id = UserId.generate();
        LocalDateTime now = LocalDateTime.now();
        return new User(id, username, email, passwordHash, profile, role, UserStatus.ACTIVE, now, now);
    }

    // Business logic: Activate user
    public void activate() {
        if (this.status == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is already active");
        }
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Deactivate a user
    public void deactivate() {
        if (this.status == UserStatus.INACTIVE) {
            throw new IllegalStateException("User is already inactive");
        }
        this.status = UserStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Suspend user
    public void suspend() {
        if (this.status == UserStatus.SUSPENDED) {
            throw new IllegalStateException("User is already suspended");
        }
        this.status = UserStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Check if the user is active
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    // Business logic: Check if user can borrow books
    public boolean canBorrowBooks() {
        return this.status == UserStatus.ACTIVE;
    }

    // Business logic: Check if the user is admin
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    // Business logic: Check if user is librarian
    public boolean isLibrarian() {
        return this.role == Role.LIBRARIAN;
    }

    // Business logic: Check if user is member
    public boolean isMember() {
        return this.role == Role.MEMBER;
    }

    // Business logic: Update profile
    public void updateProfile(UserProfile newProfile) {
        if (newProfile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }
        this.profile = newProfile;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Change password
    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        this.passwordHash = newPasswordHash;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Update email
    public void updateEmail(Email newEmail) {
        if (newEmail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Change a role
    public void changeRole(Role newRole) {
        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = newRole;
        this.updatedAt = LocalDateTime.now();
    }

}
