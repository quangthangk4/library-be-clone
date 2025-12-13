package com.library.user.api.controller;

import com.library.shared.dto.ApiResponseApp;
import com.library.user.application.dto.request.AssignRoleRequest;
import com.library.user.application.dto.request.CreateUserRequest;
import com.library.user.application.dto.request.UpdateUserProfileRequest;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.application.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for User management
 * Follows RESTful API design principles
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final AssignRoleToUserUseCase assignRoleToUserUseCase;

    /**
     * Create a new user
     * POST /api/v1/users
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseApp<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("REST request to create user: {}", request.getUsername());
        UserResponse response = createUserUseCase.execute(request);
        return ApiResponseApp.<UserResponse>builder()
            .code(HttpStatus.CREATED.value())
            .message("User created successfully")
            .data(response)
            .build();
    }

    /**
     * Get user by ID
     * GET /api/v1/users/{id}
     */
    @GetMapping("/{id}")
    public ApiResponseApp<UserResponse> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by ID: {}", id);
        UserResponse response = getUserByIdUseCase.execute(id);
        return ApiResponseApp.<UserResponse>builder()
            .code(HttpStatus.OK.value())
            .message("User retrieved successfully")
            .data(response)
            .build();
    }

    /**
     * Update user profile
     * PUT /api/v1/users/{id}/profile
     */
    @PutMapping("/{id}/profile")
    public ApiResponseApp<UserResponse> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserProfileRequest request) {
        log.info("REST request to update profile for user ID: {}", id);
        UserResponse response = updateUserProfileUseCase.execute(id, request);
        return ApiResponseApp.<UserResponse>builder()
            .code(HttpStatus.OK.value())
            .message("User profile updated successfully")
            .data(response)
            .build();
    }

    /**
     * Assign role to user
     * POST /api/v1/users/{userId}/roles/{roleId}
     */
    @PostMapping("/{userId}/roles/{roleId}")
    public ApiResponseApp<UserResponse> assignRoleToUser(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        log.info("REST request to assign role {} to user {}", roleId, userId);
        UserResponse response = assignRoleToUserUseCase.execute(userId, roleId);
        return ApiResponseApp.<UserResponse>builder()
            .code(HttpStatus.OK.value())
            .message("Role assigned successfully")
            .data(response)
            .build();
    }

    /**
     * Activate user account
     * POST /api/v1/users/{id}/activate
     */
    @PostMapping("/{id}/activate")
    public ApiResponseApp<String> activateUser(@PathVariable Long id) {
        log.info("REST request to activate user ID: {}", id);
        // TODO: Implement ActivateUserUseCase
        return ApiResponseApp.<String>builder()
            .code(HttpStatus.OK.value())
            .message("User activated successfully")
            .data("User account has been activated")
            .build();
    }

    /**
     * Suspend user account
     * POST /api/v1/users/{id}/suspend
     */
    @PostMapping("/{id}/suspend")
    public ApiResponseApp<String> suspendUser(@PathVariable Long id) {
        log.info("REST request to suspend user ID: {}", id);
        // TODO: Implement SuspendUserUseCase
        return ApiResponseApp.<String>builder()
            .code(HttpStatus.OK.value())
            .message("User suspended successfully")
            .data("User account has been suspended")
            .build();
    }

    /**
     * Deactivate user account
     * POST /api/v1/users/{id}/deactivate
     */
    @PostMapping("/{id}/deactivate")
    public ApiResponseApp<String> deactivateUser(@PathVariable Long id) {
        log.info("REST request to deactivate user ID: {}", id);
        // TODO: Implement DeactivateUserUseCase
        return ApiResponseApp.<String>builder()
            .code(HttpStatus.OK.value())
            .message("User deactivated successfully")
            .data("User account has been deactivated")
            .build();
    }
}
