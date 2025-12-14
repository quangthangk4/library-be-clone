package com.library.user.application.usecase.user;

import com.library.user.application.dto.response.UserResponse;

/**
 * Use a case for assigning a role to a user
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
