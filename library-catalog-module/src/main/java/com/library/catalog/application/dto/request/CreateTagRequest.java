package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTagRequest(
    @NotBlank(message = "Tag name is required")
    @Size(max = 100, message = "Tag name must not exceed 100 characters")
    String tagName
) {
}
