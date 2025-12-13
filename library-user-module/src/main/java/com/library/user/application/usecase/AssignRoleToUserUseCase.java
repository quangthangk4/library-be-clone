package com.library.user.application.usecase;

import com.library.user.application.dto.response.UserResponse;

/**
 * Use case for assigning a role to user
 */
public interface AssignRoleToUserUseCase {

    /**
     * Execute the use case to assign role to user
     *
     * @param userId the user ID
     * @param roleId the role ID to assign
     * @return the updated user response
     */
    UserResponse execute(Long userId, Long roleId);
}
