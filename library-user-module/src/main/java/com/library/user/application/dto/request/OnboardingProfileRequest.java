package com.library.user.application.dto.request;

import com.library.user.domain.enums.FacultyEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OnboardingProfileRequest(
        @NotNull(message = "Student ID is required")
        @Pattern(regexp = "\\d{7}", message = "Student ID must be exactly 7 digits")
        String studentId,
        FacultyEnum faculty
){
}
