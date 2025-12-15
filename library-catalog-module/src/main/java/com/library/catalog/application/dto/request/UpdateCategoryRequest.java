package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
    @Size(max = 255, message = "Category name must not exceed 255 characters")
    String categoryName,

    Long parentCategoryId,

    Boolean hasParent
) {
}
