package com.library.user.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse (
    Long id,
    String email,
    String fullName,
    LocalDate dateOfBirth,
    String phoneNumber,
    String studentId,
    String faculty,
    String address,
    String profilePictureUrl,
    Set<RoleResponse> roles,
    String status,
    boolean aiPersonalizationEnabled,
    LocalDateTime lastLoginAt,
    Long creditScore
){}
