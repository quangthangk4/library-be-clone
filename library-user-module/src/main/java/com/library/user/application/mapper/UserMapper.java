package com.library.user.application.mapper;

import com.library.user.application.dto.request.CreateUserRequest;
import com.library.user.application.dto.request.UpdateUserProfileRequest;
import com.library.user.application.dto.response.PermissionResponse;
import com.library.user.application.dto.response.RoleResponse;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.domain.model.Permission;
import com.library.user.domain.model.RoleAggregate;
import com.library.user.domain.model.UserAggregate;
import com.library.user.domain.valueobject.Email;
import com.library.user.domain.valueobject.UserProfile;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * MapStruct mapper for User aggregate
 * Handles conversion between domain models and DTOs
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Map UserAggregate to UserResponse
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "fullName", source = "profile.fullName")
    @Mapping(target = "dateOfBirth", source = "profile.dateOfBirth")
    @Mapping(target = "phoneNumber", source = "profile.phoneNumber")
    @Mapping(target = "address", source = "profile.address")
    @Mapping(target = "profilePictureUrl", source = "profile.profilePictureUrl")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "roles", source = "roles")
    UserResponse toResponse(UserAggregate user);

    /**
     * Map RoleAggregate to RoleResponse
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "permissions", source = "permissions")
    RoleResponse toRoleResponse(RoleAggregate role);

    /**
     * Map Permission to PermissionResponse
     */
    @Mapping(target = "id", source = "id.value")
    PermissionResponse toPermissionResponse(Permission permission);

    /**
     * Helper method to map Email value object
     */
    default String mapEmail(Email email) {
        return email != null ? email.getValue() : null;
    }

    /**
     * Helper method to create Email from string
     */
    default Email mapToEmail(String email) {
        return email != null ? Email.of(email) : null;
    }

    /**
     * Helper method to create UserProfile from request
     */
    default UserProfile mapToUserProfile(UpdateUserProfileRequest request) {
        if (request == null) {
            return null;
        }
        return new UserProfile(
            request.getFullName(),
            request.getDateOfBirth(),
            request.getPhoneNumber(),
            request.getAddress(),
            request.getProfilePictureUrl()
        );
    }

    /**
     * Helper method to create UserProfile from CreateUserRequest
     */
    default UserProfile mapToUserProfile(CreateUserRequest request) {
        if (request == null) {
            return null;
        }
        return new UserProfile(
            request.getFullName(),
            request.getDateOfBirth(),
            request.getPhoneNumber(),
            request.getAddress(),
            null // profile picture URL is set separately
        );
    }
}
