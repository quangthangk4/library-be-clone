package com.library.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Current password is required")
        String currentPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 4, max = 50, message = "Password must be at least 4 characters")
        String newPassword,

        @NotBlank(message = "Confirm password is required")
        @Size(min = 4, max = 50, message = "Password must be at least 4 characters")
        String confirmPassword
) {}
