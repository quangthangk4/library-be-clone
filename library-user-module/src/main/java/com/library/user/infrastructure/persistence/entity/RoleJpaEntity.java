package com.library.user.infrastructure.persistence.entity;

import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for Role table
 * Maps to the database schema defined in the specification
 */
@Entity
@Table(name = "roles", indexes = {
    @Index(name = "idx_role_name", columnList = "role_name", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleJpaEntity extends BaseEntity {

    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String roleName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserJpaEntity> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @Builder.Default
    private Set<PermissionJpaEntity> permissions = new HashSet<>();

    /**
     * Helper method to add a permission
     */
    public void addPermission(PermissionJpaEntity permission) {
        this.permissions.add(permission);
        permission.getRoles().add(this);
    }

    /**
     * Helper method to remove a permission
     */
    public void removePermission(PermissionJpaEntity permission) {
        this.permissions.remove(permission);
        permission.getRoles().remove(this);
    }
}
