package com.library.circulation.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for renewing a borrowing transaction.
 */
public record RenewTransactionRequest(
    @NotNull(message = "Transaction ID is required")
    Long transactionId
) {
}
