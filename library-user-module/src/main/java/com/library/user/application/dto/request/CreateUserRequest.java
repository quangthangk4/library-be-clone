package com.library.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO for creating a new user
 */
public record CreateUserRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password,

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Full name must contain only letters")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    String fullName,

    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,

    @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Phone number must be valid")
    String phoneNumber
){
}
