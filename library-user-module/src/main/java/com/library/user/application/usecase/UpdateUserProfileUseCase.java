package com.library.user.application.usecase;

import com.library.user.application.dto.request.UpdateUserProfileRequest;
import com.library.user.application.dto.response.UserResponse;

/**
 * Use case for updating user profile
 */
public interface UpdateUserProfileUseCase {

    /**
     * Execute the use case to update user profile
     *
     * @param userId the user ID
     * @param request the update profile request
     * @return the updated user response
     */
    UserResponse execute(Long userId, UpdateUserProfileRequest request);
}
