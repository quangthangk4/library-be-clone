package com.library.circulation.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for paying a fine.
 */
public record PayFineRequest(
    @NotNull(message = "Fine ID is required")
    Long fineId
) {
}
