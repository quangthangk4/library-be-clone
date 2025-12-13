package com.library.user.application.usecase;

import com.library.user.application.dto.request.CreateUserRequest;
import com.library.user.application.dto.response.UserResponse;

/**
 * Use case for creating a new user
 */
public interface CreateUserUseCase {

    /**
     * Execute the use case to create a new user
     *
     * @param request the user creation request
     * @return the created user response
     */
    UserResponse execute(CreateUserRequest request);
}
