package com.library.user.application.dto.request;

import com.library.user.domain.enums.FacultyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserCommand(
    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Full name must contain only letters")
    String fullName,

    @NotBlank(message = "Student ID is required")
    @Pattern(regexp = "\\d{7}", message = "Student ID must be exactly 7 digits")
    String studentId,

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@hcmut\\.edu\\.vn$", message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 50, message = "Password must be at least 4 characters")
    String password,

    @NotBlank(message = "Confirm password is required")
    @Size(min = 4, max = 50, message = "Password must be at least 4 characters")
    String confirmPassword,

    FacultyEnum faculty
) {

}
