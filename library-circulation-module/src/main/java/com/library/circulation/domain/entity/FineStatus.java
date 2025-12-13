package com.library.circulation.domain.entity;

/**
 * Fine status enumeration
 */
public enum FineStatus {
    /**
     * Fine is pending payment
     */
    PENDING,

    /**
     * Fine is paid
     */
    PAID,

    /**
     * Fine is waived/forgiven
     */
    WAIVED,

    /**
     * Fine is cancelled
     */
    CANCELLED
}
