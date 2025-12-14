package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateAuthorRequest(
    @NotBlank(message = "Author name is required")
    @Size(max = 255, message = "Author name must not exceed 255 characters")
    String authorName,

    @Size(max = 2000, message = "Biography must not exceed 2000 characters")
    String biography,

    LocalDate dateOfBirth,

    LocalDate dateOfDeath
) {
}
