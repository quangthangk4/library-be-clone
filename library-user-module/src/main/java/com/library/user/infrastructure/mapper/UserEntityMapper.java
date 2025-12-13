package com.library.user.infrastructure.mapper;

import com.library.user.domain.model.Permission;
import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.model.UserStatus;
import com.library.user.domain.valueobject.*;
import com.library.user.infrastructure.persistence.entity.PermissionJpaEntity;
import com.library.user.infrastructure.persistence.entity.RoleJpaEntity;
import com.library.user.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for converting between JPA Entities and Domain Models
 * This is a critical component in Clean Architecture to maintain separation between layers
 */
@Component
public class UserEntityMapper {

    // ============== User Mapping ==============

    /**
     * Convert UserAggregate (Domain) to UserJpaEntity (Infrastructure)
     */
    public UserJpaEntity toJpaEntity(UserAggregate user) {
        if (user == null) {
            return null;
        }

        UserJpaEntity entity = UserJpaEntity.builder()
            .username(user.getUsername())
            .hashedPassword(user.getPasswordHash())
            .email(user.getEmail().getValue())
            .fullName(user.getProfile().getFullName())
            .dateOfBirth(user.getProfile().getDateOfBirth())
            .phoneNumber(user.getProfile().getPhoneNumber())
            .address(user.getProfile().getAddress())
            .profilePictureUrl(user.getProfile().getProfilePictureUrl())
            .accountStatus(user.getStatus())
            .lastLoginAt(user.getLastLoginAt())
            .aiPersonalizationEnabled(user.isAiPersonalizationEnabled())
            .build();

        // Set ID if exists
        if (user.getId() != null) {
            entity.setId(user.getId().getValue());
        }

        // Set timestamps
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        // Map roles (without circular reference)
        Set<RoleJpaEntity> roleEntities = user.getRoles().stream()
            .map(this::toJpaEntity)
            .collect(Collectors.toSet());
        entity.setRoles(roleEntities);

        return entity;
    }

    /**
     * Convert UserJpaEntity (Infrastructure) to UserAggregate (Domain)
     */
    public UserAggregate toDomainModel(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        // Create value objects
        UserId userId = UserId.of(entity.getId());
        Email email = Email.of(entity.getEmail());
        UserProfile profile = new UserProfile(
            entity.getFullName(),
            entity.getDateOfBirth(),
            entity.getPhoneNumber(),
            entity.getAddress(),
            entity.getProfilePictureUrl()
        );

        // Map roles
        Set<RoleAggregate> roles = entity.getRoles().stream()
            .map(this::toDomainModel)
            .collect(Collectors.toSet());

        return new UserAggregate(
            userId,
            entity.getUsername(),
            email,
            entity.getHashedPassword(),
            profile,
            roles,
            entity.getAccountStatus(),
            entity.getAiPersonalizationEnabled(),
            entity.getLastLoginAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    // ============== Role Mapping ==============

    /**
     * Convert RoleAggregate (Domain) to RoleJpaEntity (Infrastructure)
     */
    public RoleJpaEntity toJpaEntity(RoleAggregate role) {
        if (role == null) {
            return null;
        }

        RoleJpaEntity entity = RoleJpaEntity.builder()
            .roleName(role.getRoleName())
            .description(role.getDescription())
            .build();

        // Set ID if exists
        if (role.getId() != null) {
            entity.setId(role.getId().getValue());
        }

        // Set timestamps
        entity.setCreatedAt(role.getCreatedAt());
        entity.setUpdatedAt(role.getUpdatedAt());

        // Map permissions
        Set<PermissionJpaEntity> permissionEntities = role.getPermissions().stream()
            .map(this::toJpaEntity)
            .collect(Collectors.toSet());
        entity.setPermissions(permissionEntities);

        return entity;
    }

    /**
     * Convert RoleJpaEntity (Infrastructure) to RoleAggregate (Domain)
     */
    public RoleAggregate toDomainModel(RoleJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        RoleId roleId = RoleId.of(entity.getId());

        // Map permissions
        Set<Permission> permissions = entity.getPermissions().stream()
            .map(this::toDomainModel)
            .collect(Collectors.toSet());

        return new RoleAggregate(
            roleId,
            entity.getRoleName(),
            entity.getDescription(),
            permissions,
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    // ============== Permission Mapping ==============

    /**
     * Convert Permission (Domain) to PermissionJpaEntity (Infrastructure)
     */
    public PermissionJpaEntity toJpaEntity(Permission permission) {
        if (permission == null) {
            return null;
        }

        PermissionJpaEntity entity = PermissionJpaEntity.builder()
            .permissionName(permission.getPermissionName())
            .description(permission.getDescription())
            .build();

        // Set ID if exists
        if (permission.getId() != null) {
            entity.setId(permission.getId().getValue());
        }

        // Set timestamps
        entity.setCreatedAt(permission.getCreatedAt());
        entity.setUpdatedAt(permission.getUpdatedAt());

        return entity;
    }

    /**
     * Convert PermissionJpaEntity (Infrastructure) to Permission (Domain)
     */
    public Permission toDomainModel(PermissionJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        PermissionId permissionId = PermissionId.of(entity.getId());

        return new Permission(
            permissionId,
            entity.getPermissionName(),
            entity.getDescription(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}
