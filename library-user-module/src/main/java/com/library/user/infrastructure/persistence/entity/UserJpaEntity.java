package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import com.library.user.domain.model.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for User table
 * Maps to the database schema defined in the specification
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username"),
    @Index(name = "idx_email", columnList = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJpaEntity extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    @Builder.Default
    private UserStatus accountStatus = UserStatus.ACTIVE;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "ai_personalization_enabled", nullable = false)
    @Builder.Default
    private Boolean aiPersonalizationEnabled = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<RoleJpaEntity> roles = new HashSet<>();

    /**
     * Helper method to add a role
     */
    public void addRole(RoleJpaEntity role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    /**
     * Helper method to remove a role
     */
    public void removeRole(RoleJpaEntity role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
