package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateItemStatusRequest(
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(AVAILABLE|BORROWED|RESERVED|IN_MAINTENANCE|LOST)$",
            message = "Status must be AVAILABLE, BORROWED, RESERVED, IN_MAINTENANCE, or LOST")
    String status
) {
}
