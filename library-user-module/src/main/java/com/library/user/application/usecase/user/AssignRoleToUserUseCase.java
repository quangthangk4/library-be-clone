package com.library.user.application.usecase.user;

import com.library.user.application.dto.response.UserResponse;

public interface AssignRoleToUserUseCase {

  UserResponse execute(Long userId, Long roleId);
}
