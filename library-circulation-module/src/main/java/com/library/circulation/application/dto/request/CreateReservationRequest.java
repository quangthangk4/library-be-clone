package com.library.circulation.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for creating a reservation.
 */
public record CreateReservationRequest(
    @NotNull(message = "User ID is required")
    Long userId,

    @NotNull(message = "Publication ID is required")
    Long publicationId
) {
}
