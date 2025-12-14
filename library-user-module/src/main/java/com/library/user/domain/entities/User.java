package com.library.user.domain.entities;

import com.library.user.domain.event.UserCreatedEvent;
import com.library.user.domain.event.UserStatusChangedEvent;
import com.library.user.domain.service.IPasswordHasher;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.PasswordHash;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserProfile;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * User Aggregate Root - Core entity in User bounded context
 * Manages user data, roles
 */
@Getter
public class User {
    private final UserId id;
    private final Email email;
    private PasswordHash passwordHash;
    private UserProfile profile;
    private final Set<Role> roles;
    private UserStatus status;
    private boolean aiPersonalizationEnabled;
    private LocalDateTime lastLoginAt;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    private User(UserId id,
                Email email,
                PasswordHash passwordHash,
                UserProfile profile,
                Set<Role> roles,
                UserStatus status,
                boolean aiPersonalizationEnabled,
                LocalDateTime lastLoginAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.profile = profile;
        this.roles = roles != null? new HashSet<>(roles) : new HashSet<>();
        this.status = status;
        this.aiPersonalizationEnabled = aiPersonalizationEnabled;
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * Factory method to create a new user
     */
    public static User createForMapper(UserId id,
                              Email email,
                              PasswordHash passwordHash,
                              UserProfile profile,
                              Set<Role> roles,
                              UserStatus status,
                              boolean aiPersonalizationEnabled,
                              LocalDateTime lastLoginAt) {
        return new User(id, email, passwordHash, profile,
                roles, status, aiPersonalizationEnabled, lastLoginAt);
    }

    public static User create(
            Email email,
            PasswordHash passwordHash,
            UserProfile profile,
            Role defaultRole) {

        UserId id = UserId.generate();

        Set<Role> roles = new HashSet<>();
        if (defaultRole != null) {
            roles.add(defaultRole);
        }

        User user = new User(id, email, passwordHash, profile,
            roles, UserStatus.INACTIVE, true, null);

        user.addDomainEvent(new UserCreatedEvent(id, email.getValue()));

        return user;
    }

    // ============== Role Management ==============
    public void assignRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (this.roles.contains(role)) {
            throw new IllegalStateException("User already has this role");
        }

        this.roles.add(role);
    }

    /**
     * Business logic: Remove a role from user
     */
    public void removeRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (!this.roles.contains(role)) {
            throw new IllegalStateException("User does not have this role");
        }

        // Ensure user has at least one role
        if (this.roles.size() == 1) {
            throw new IllegalStateException("User must have at least one role");
        }

        this.roles.remove(role);
    }

    /**
     * Business logic: Check if a user has a specific role
     */
    public boolean hasRole(String roleName) {
        return this.roles.stream()
            .anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName));
    }

    /**
     * Get an immutable view of roles
     */
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    // ============== User Status Management ==============

    /**
     * Business logic: Activate user
     */
    public void activate() {
        if (this.status == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is already active");
        }

        UserStatus oldStatus = this.status;
        this.status = UserStatus.ACTIVE;

        addDomainEvent(new UserStatusChangedEvent(this.id, oldStatus, UserStatus.ACTIVE));
    }

    /**
     * Business logic: Deactivate user
     */
    public void deactivate() {
        if (this.status == UserStatus.DEACTIVATED) {
            throw new IllegalStateException("User is already deactivated");
        }

        UserStatus oldStatus = this.status;
        this.status = UserStatus.DEACTIVATED;
        addDomainEvent(new UserStatusChangedEvent(this.id, oldStatus, UserStatus.DEACTIVATED));
    }

    /**
     * Business logic: Suspend user
     */
    public void suspend() {
        if (this.status == UserStatus.SUSPENDED) {
            throw new IllegalStateException("User is already suspended");
        }

        UserStatus oldStatus = this.status;
        this.status = UserStatus.SUSPENDED;

        addDomainEvent(new UserStatusChangedEvent(this.id, oldStatus, UserStatus.SUSPENDED));
    }

    // ============== Business Rules ==============

    /**
     * Business logic: Check if user is active
     */
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    /**
     * Business logic: Check if user can borrow books
     */
    public boolean canBorrowBooks() {
        return this.status == UserStatus.ACTIVE
//                && hasPermission("BORROW_BOOK")
                ;
    }

    /**
     * Business logic: Check if the user can perform librarian operations
     */
    public boolean isLibrarian() {
        return hasRole("LIBRARIAN");
    }

    /**
     * Business logic: Check if user is admin
     */
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    // ============== Profile Management ==============

    /**
     * Business logic: Update user profile
     */
    public void updateProfile(UserProfile newProfile) {
        if (newProfile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }
        this.profile = newProfile;
    }

    /**
     * Business logic: Change password
     */
    public void changePassword(PasswordHash newPasswordHash) {
        if (newPasswordHash == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        this.passwordHash = newPasswordHash;
    }

    public boolean verifyPassword(String newPassword, IPasswordHasher hasher) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return this.passwordHash.matches(newPassword, hasher);
    }

    /**
     * Business logic: Record login
     */
    public void recordLogin() {
        if (!isActive()) {
            throw new IllegalStateException("Cannot login - user account is not active");
        }
        this.lastLoginAt = LocalDateTime.now();
    }

    /**
     * Business logic: Toggle AI personalization
     */
    public void toggleAIPersonalization(boolean enabled) {
        this.aiPersonalizationEnabled = enabled;
    }

    // ============== Domain Events ==============

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
