package com.library.circulation.domain.entities;

/**
 * Enum representing the payment status of a fine.
 */
public enum PaymentStatus {
    /**
     * Fine has not been paid yet.
     */
    UNPAID,

    /**
     * Fine has been paid.
     */
    PAID
}
