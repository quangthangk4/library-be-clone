package com.library.user.application.usecase.user;

import com.library.user.application.dto.response.UserResponse;

/**
 * Use a case for getting user by ID
 */
public interface GetUserByIdUseCase {
    UserResponse execute(Long userId);
}
