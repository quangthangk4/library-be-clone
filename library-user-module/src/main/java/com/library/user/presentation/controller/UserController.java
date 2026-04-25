package com.library.user.presentation.controller;

import com.library.shared.dto.ApiResponseApp;
import com.library.shared.util.RequiresAuthentication;
import com.library.shared.util.SecurityEvaluator;
import com.library.user.application.dto.request.ChangePasswordRequest;
import com.library.user.application.dto.request.UpdateUserProfileCommand;
import com.library.user.application.dto.response.UserResponse;
import com.library.user.application.usecase.user.ChangePasswordUseCase;
import com.library.user.application.usecase.user.GetUserByIdUseCase;
import com.library.user.application.usecase.user.UpdateUserProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final GetUserByIdUseCase getUserByIdUseCase;
  private final UpdateUserProfileUseCase updateUserProfileUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final SecurityEvaluator security;

  @GetMapping("/my-profile")
  @RequiresAuthentication()
  @Operation(summary = "Get current user profile")
  public ApiResponseApp<UserResponse> getUserById() {
    Long userId = security.getCurrentUserId();
    UserResponse response = getUserByIdUseCase.execute(userId);
    return ApiResponseApp.success(response);
  }

  @PutMapping("/my-profile")
  @RequiresAuthentication()
  @Operation(summary = "Update current user profile")
  @ResponseStatus(HttpStatus.OK)
  public ApiResponseApp<UserResponse> updateUserProfile(
      @Valid @RequestBody UpdateUserProfileCommand request) {
    Long id = security.getCurrentUserId();
    log.info("REST request to update profile for user ID: {}", id);
    UserResponse response = updateUserProfileUseCase.execute(id, request);
    return ApiResponseApp.success(response);
  }

  @PutMapping("/my-profile/change-password")
  @Operation(summary = "Change current user password")
  @RequiresAuthentication()
  @ResponseStatus(HttpStatus.OK)
  public ApiResponseApp<Void> changePassword(
      @Valid @RequestBody ChangePasswordRequest request) {
    Long userId = security.getCurrentUserId();
    log.info("REST request to change password for user ID: {}", userId);
    changePasswordUseCase.execute(request, userId);
    return ApiResponseApp.success("Change password successfully");
  }
}
