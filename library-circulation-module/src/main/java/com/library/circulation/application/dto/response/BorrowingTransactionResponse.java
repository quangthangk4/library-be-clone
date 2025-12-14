package com.library.circulation.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for BorrowingTransaction.
 * Includes enriched data from related entities.
 */
public record BorrowingTransactionResponse(
    Long id,
    Long userId,
    String userFullName,
    Long itemId,
    String itemBarcode,
    String publicationTitle,
    Long librarianIdIssue,
    Long librarianIdReturn,
    LocalDateTime borrowedDate,
    LocalDate dueDate,
    LocalDateTime returnedDate,
    String status,
    int renewalCount,
    Integer daysOverdue
) {
}
