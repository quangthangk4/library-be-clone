package com.library.catalog.application.dto.response;

import java.time.LocalDateTime;

public record TagResponse(
    Long id,
    String tagName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
