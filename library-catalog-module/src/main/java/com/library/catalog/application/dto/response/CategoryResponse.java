package com.library.catalog.application.dto.response;

import java.time.LocalDateTime;

public record CategoryResponse(
    Long id,
    String categoryName,
    Long parentCategoryId,
    String parentCategoryName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
