package com.library.circulation.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for creating a borrowing transaction.
 */
public record CreateBorrowingTransactionRequest(
    @NotNull(message = "User ID is required")
    Long userId,

    @NotNull(message = "Item ID is required")
    Long itemId,

    Long librarianIdIssue
) {
}
