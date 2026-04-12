package com.library.user.application.usecase.user;

import com.library.user.application.dto.request.UpdateUserProfileRequest;
import com.library.user.application.dto.response.UserResponse;

public interface UpdateUserProfileUseCase {
    UserResponse execute(Long userId, UpdateUserProfileRequest request);
}
