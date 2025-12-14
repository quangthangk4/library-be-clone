package com.library.circulation.application.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for returning a borrowed item.
 */
public record ReturnItemRequest(
    @NotNull(message = "Transaction ID is required")
    Long transactionId,

    Long librarianIdReturn
) {
}
