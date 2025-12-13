package com.library.user.application.usecase;

import com.library.user.application.dto.response.UserResponse;

/**
 * Use case for getting user by ID
 */
public interface GetUserByIdUseCase {

    /**
     * Execute the use case to get user by ID
     *
     * @param userId the user ID
     * @return the user response
     */
    UserResponse execute(Long userId);
}
