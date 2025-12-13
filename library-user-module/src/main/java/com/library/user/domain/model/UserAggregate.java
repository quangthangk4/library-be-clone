package com.library.user.domain.model;

import com.library.user.domain.event.UserCreatedEvent;
import com.library.user.domain.event.UserStatusChangedEvent;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserProfile;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

/**
 * User Aggregate Root - Core entity in User bounded context
 * Manages user data, roles, and permissions
 */
@Getter
public class UserAggregate {
    private final UserId id;
    private String username;
    private Email email;
    private String passwordHash;
    private UserProfile profile;
    private final Set<RoleAggregate> roles;
    private UserStatus status;
    private boolean aiPersonalizationEnabled;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    public UserAggregate(UserId id,
                        String username,
                        Email email,
                        String passwordHash,
                        UserProfile profile,
                        Set<RoleAggregate> roles,
                        UserStatus status,
                        boolean aiPersonalizationEnabled,
                        LocalDateTime lastLoginAt,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.profile = profile;
        this.roles = new HashSet<>(roles != null ? roles : new HashSet<>());
        this.status = status;
        this.aiPersonalizationEnabled = aiPersonalizationEnabled;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create a new user
     */
    public static UserAggregate create(
            String username,
            Email email,
            String passwordHash,
            UserProfile profile,
            RoleAggregate defaultRole) {

        validateUsername(username);
        validatePasswordHash(passwordHash);

        UserId id = UserId.generate();
        LocalDateTime now = LocalDateTime.now();

        Set<RoleAggregate> roles = new HashSet<>();
        if (defaultRole != null) {
            roles.add(defaultRole);
        }

        UserAggregate user = new UserAggregate(
            id, username, email, passwordHash, profile,
            roles, UserStatus.ACTIVE, true, null, now, now
        );

        user.addDomainEvent(new UserCreatedEvent(id, username, email.getValue(), now));

        return user;
    }

    // ============== Role Management ==============

    /**
     * Business logic: Add a role to user
     */
    public void assignRole(RoleAggregate role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (this.roles.contains(role)) {
            throw new IllegalStateException("User already has this role");
        }

        this.roles.add(role);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Remove a role from user
     */
    public void removeRole(RoleAggregate role) {
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
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Check if user has a specific role
     */
    public boolean hasRole(String roleName) {
        return this.roles.stream()
            .anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName));
    }

    /**
     * Business logic: Check if user has a specific permission
     */
    public boolean hasPermission(String permissionName) {
        return this.roles.stream()
            .anyMatch(role -> role.hasPermissionByName(permissionName));
    }

    /**
     * Get all permissions from all roles
     */
    public Set<Permission> getAllPermissions() {
        Set<Permission> allPermissions = new HashSet<>();
        this.roles.forEach(role -> allPermissions.addAll(role.getPermissions()));
        return Collections.unmodifiableSet(allPermissions);
    }

    /**
     * Get immutable view of roles
     */
    public Set<RoleAggregate> getRoles() {
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
        this.updatedAt = LocalDateTime.now();

        addDomainEvent(new UserStatusChangedEvent(this.id, oldStatus, UserStatus.ACTIVE, this.updatedAt));
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
        this.updatedAt = LocalDateTime.now();

        addDomainEvent(new UserStatusChangedEvent(this.id, oldStatus, UserStatus.DEACTIVATED, this.updatedAt));
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
        this.updatedAt = LocalDateTime.now();

        addDomainEvent(new UserStatusChangedEvent(this.id, oldStatus, UserStatus.SUSPENDED, this.updatedAt));
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
        return this.status == UserStatus.ACTIVE && hasPermission("BORROW_BOOK");
    }

    /**
     * Business logic: Check if user can perform librarian operations
     */
    public boolean isLibrarian() {
        return hasRole("LIBRARIAN") || hasRole("ADMIN");
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
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Change password
     */
    public void changePassword(String newPasswordHash) {
        validatePasswordHash(newPasswordHash);
        this.passwordHash = newPasswordHash;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Update email
     */
    public void updateEmail(Email newEmail) {
        if (newEmail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Record login
     */
    public void recordLogin() {
        if (!isActive()) {
            throw new IllegalStateException("Cannot login - user account is not active");
        }
        this.lastLoginAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Business logic: Toggle AI personalization
     */
    public void toggleAIPersonalization(boolean enabled) {
        this.aiPersonalizationEnabled = enabled;
        this.updatedAt = LocalDateTime.now();
    }

    // ============== Validation ==============

    private static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException(
                "Username can only contain letters, numbers, and underscores"
            );
        }
    }

    private static void validatePasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
    }

    // ============== Domain Events ==============

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAggregate that = (UserAggregate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
