package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import com.library.user.domain.entities.UserStatus;
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
    @Index(name = "idx_email", columnList = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "hashedPassword")
    private String hashedPassword;

    @Column(name = "fullName", nullable = false, length = 100)
    private String fullName;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserStatus accountStatus = UserStatus.INACTIVE;

    private LocalDateTime lastLoginAt;

    private String profilePictureUrl;

    private String provider;

    private String providerId;

    @Column( nullable = false)
    @Builder.Default
    private Boolean aiPersonalizationEnabled = true;

    @Column(nullable = false)
    @Builder.Default
    private int creditScore = 100;

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    @Builder.Default
    private Set<RoleEntity> roles = new HashSet<>();

    /**
     * Helper method to add a role
     */
    public void addRole(RoleEntity role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    /**
     * Helper method to remove a role
     */
    public void removeRole(RoleEntity role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
