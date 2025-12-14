package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateItemPhysicalPropertiesRequest(
    @Size(max = 50, message = "Size must not exceed 50 characters")
    String size,

    @Positive(message = "Weight must be positive")
    Double weight
) {
}
