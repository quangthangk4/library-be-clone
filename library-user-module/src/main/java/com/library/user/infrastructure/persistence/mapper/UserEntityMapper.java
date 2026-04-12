package com.library.user.infrastructure.persistence.mapper;

import com.library.user.domain.entities.Role;
import com.library.user.domain.entities.User;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.PasswordHash;
import com.library.user.domain.valueobject.RoleId;
import com.library.user.domain.valueobject.UserId;
import com.library.user.domain.valueobject.UserProfile;
import com.library.user.infrastructure.persistence.entity.RoleEntity;
import com.library.user.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = UserEntity.builder()
            .hashedPassword(user.getPasswordHash() != null ? user.getPasswordHash().getValue() : null)
            .email(user.getEmail().getValue())
            .fullName(user.getProfile().getFullName())
            .dateOfBirth(user.getProfile().getDateOfBirth())
            .phoneNumber(user.getProfile().getPhoneNumber())
            .address(user.getProfile().getAddress())
            .profilePictureUrl(user.getProfile().getProfilePictureUrl())
            .status(user.getStatus())
            .lastLoginAt(user.getLastLoginAt())
            .aiPersonalizationEnabled(user.isAiPersonalizationEnabled())
            .faculty(user.getFaculty())
            .provider(user.getProvider())
            .studentId(user.getStudentId())
            .providerId(user.getProviderId())
            .build();

        // Set ID if exists
        if (user.getId() != null) {
            entity.setId(user.getId().getValue());
        }

        // Map roles (without circular reference)
        Set<RoleEntity> roleEntities = user.getRoles().stream()
            .map(this::toEntity)
            .collect(Collectors.toSet());
        entity.setRoles(roleEntities);

        return entity;
    }

    /**
     * Convert UserJpaEntity (Infrastructure) to UserAggregate (Domain)
     */
    public User toDomainModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        // Create value objects
        UserId userId = UserId.of(entity.getId());
        Email email = Email.of(entity.getEmail());
        PasswordHash passwordHash = entity.getHashedPassword() != null ? PasswordHash.of(entity.getHashedPassword()) : null;
        UserProfile profile = new UserProfile(
            entity.getFullName(),
            entity.getDateOfBirth(),
            entity.getPhoneNumber(),
            entity.getAddress(),
            entity.getProfilePictureUrl()
        );

        // Map roles
        Set<Role> roles = entity.getRoles().stream()
            .map(this::toDomainModel)
            .collect(Collectors.toSet());

        return User.createForMapper(
            userId,
            email,
            passwordHash,
            profile,
            roles,
            entity.getStatus(),
            entity.getAiPersonalizationEnabled(),
            entity.getLastLoginAt(),
            entity.getProvider(),
            entity.getStudentId(),
            entity.getFaculty(),
            entity.getProviderId(),
            entity.getCreditScore()
        );
    }

    // ============== Role Mapping ==============

    /**
     * Convert RoleAggregate (Domain) to RoleJpaEntity (Infrastructure)
     */
    public RoleEntity toEntity(Role role) {
        if (role == null) {
            return null;
        }

        RoleEntity entity = RoleEntity.builder()
            .roleName(role.getRoleName())
            .description(role.getDescription())
            .build();

        // Set ID if exists
        if (role.getId() != null) {
            entity.setId(role.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert RoleJpaEntity (Infrastructure) to RoleAggregate (Domain)
     */
    public Role toDomainModel(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        RoleId roleId = RoleId.of(entity.getId());

        return Role.of(
            roleId,
            entity.getRoleName(),
            entity.getDescription()
        );
    }
}
