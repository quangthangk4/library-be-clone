package com.library.circulation.application.dto.response;

import java.time.LocalDateTime;

/**
 * Response DTO for Reservation.
 * Includes enriched data from related entities.
 */
public record ReservationResponse(
    Long id,
    Long userId,
    String userFullName,
    Long publicationId,
    String publicationTitle,
    LocalDateTime reservationDate,
    String status,
    LocalDateTime notificationSentDate,
    Integer queuePosition
) {
}
