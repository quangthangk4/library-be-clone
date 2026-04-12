package com.library.circulation.domain.enums;

/**
 * Enum representing the status of a borrowing transaction.
 */
public enum TransactionStatus {
    WAITING_FOR_PICKUP,
    BORROWING,
    RETURNED,
    OVERDUE
}
