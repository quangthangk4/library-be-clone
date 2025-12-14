package com.library.catalog.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AuthorResponse(
    Long id,
    String authorName,
    String biography,
    LocalDate dateOfBirth,
    LocalDate dateOfDeath,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
