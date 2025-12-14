package com.library.circulation.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for cancelling a reservation.
 */
public record CancelReservationRequest(
    @NotNull(message = "Reservation ID is required")
    Long reservationId
) {
}
