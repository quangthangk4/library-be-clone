package com.library.user.domain.entities;

import com.library.shared.exception.AppException;
import com.library.shared.exception.DomainException;
import com.library.shared.exception.ErrorCode;
import com.library.user.domain.enums.FacultyEnum;
import com.library.user.domain.event.UserCreatedEvent;
import com.library.user.domain.event.UserStatusChangedEvent;
import com.library.user.domain.port.IPasswordHasher;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.PasswordHash;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserProfile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final UserId id;
    private final Email email;
    private PasswordHash passwordHash;
    private UserProfile profile;
    private final Set<Role> roles;
    private UserStatus status;
    private boolean aiPersonalizationEnabled;
    private LocalDateTime lastLoginAt;
    private String studentId;
    private FacultyEnum faculty;
    private final String provider;
    private final String providerId;
    private int creditScore;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    public static User createForMapper(UserId id,
                              Email email,
                              PasswordHash passwordHash,
                              UserProfile profile,
                              Set<Role> roles,
                              UserStatus status,
                              boolean aiPersonalizationEnabled,
                              LocalDateTime lastLoginAt,
                              String provider,
                              String studentId,
                              FacultyEnum faculty,
                              String providerId,
                               int creditScore) {
        return new User(id, email, passwordHash, profile,
                roles, status, aiPersonalizationEnabled, lastLoginAt,studentId, faculty, provider, providerId, creditScore);
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
            roles, UserStatus.INACTIVE, true, null, null,null,null
                , null, 100);

        user.addDomainEvent(new UserCreatedEvent(id, email.getValue()));

        return user;
    }

    public static User createWithOauth2(
            Email email,
            UserProfile profile,
            String provider,
            String providerId,
            Role defaultRole
    ){
        UserId id = UserId.generate();

        Set<Role> roles = new HashSet<>();
        if (defaultRole != null) {
            roles.add(defaultRole);
        }

        return new User(id, email, null, profile,
                roles, UserStatus.ACTIVE, true, null,null,null
                , provider, providerId, 100);
    }

    // onboarding profile
    public void completeOnboardingProfile(String studentId, FacultyEnum faculty) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new DomainException("Student ID cannot be null or empty");
        }
        if (faculty == null) {
            throw new DomainException("Faculty cannot be null");
        }
        this.studentId = studentId;
        this.faculty = faculty;
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

    public void updateFaculty(FacultyEnum faculty) {
        if (faculty == null) {
            throw new DomainException("Faculty cannot be null");
        }
        this.faculty = faculty;
    }

    public void updateProfile(UserProfile newProfile) {
        if (newProfile == null) {
            throw new IllegalArgumentException("Profile cannot be null");
        }
        this.profile = newProfile;
    }

    public void validateStatus() {
        if (status == UserStatus.LOCKED) {
            throw new AppException(ErrorCode.USER_LOCKED);
        }
        if (status == UserStatus.INACTIVE) {
            throw new AppException(ErrorCode.USER_EMAIL_NOT_VERIFIED);
        }
        if (status == UserStatus.BANNED) {
            throw new AppException(ErrorCode.USER_BANNED);
        }
    }

    /**
     * Business logic: Change password
     */
    public void changePassword(String oldPassword, String newPassword, String confirmPassword, IPasswordHasher hasher) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be null or empty");
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Confirm password cannot be null or empty");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Old password cannot be null or empty");
        }

        if (this.passwordHash == null || !this.passwordHash.matches(oldPassword, hasher)) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        this.passwordHash = PasswordHash.createFromRaw(newPassword, hasher);
    }

    public void verifyPassword(String newPassword, IPasswordHasher hasher) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (this.passwordHash == null || !this.passwordHash.matches(newPassword, hasher)){
            throw new AppException(ErrorCode.EMAIL_OR_PASSWORD_INCORRECT);
        }
    }

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
}
