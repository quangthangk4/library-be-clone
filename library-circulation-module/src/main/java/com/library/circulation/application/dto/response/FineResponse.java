package com.library.circulation.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for Fine.
 * Includes enriched data from related entities.
 */
public record FineResponse(
    Long id,
    Long transactionId,
    Long userId,
    String userFullName,
    String itemBarcode,
    BigDecimal fineAmount,
    LocalDate fineDate,
    String paymentStatus,
    LocalDateTime paidDate
) {
}
