package com.library.circulation.domain.entities;

/**
 * Enum representing the status of a borrowing transaction.
 */
public enum TransactionStatus {
    /**
     * Transaction is active - item is currently borrowed.
     */
    ACTIVE,

    /**
     * Transaction is completed - item has been returned.
     */
    RETURNED,

    /**
     * Transaction is overdue - item was not returned by due date.
     */
    OVERDUE
}
