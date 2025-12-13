package com.library.circulation.domain.entity;

/**
 * Reservation status enumeration
 */
public enum ReservationStatus {
    /**
     * Reservation is pending (waiting for book to be available)
     */
    PENDING,

    /**
     * Book is ready for pickup
     */
    READY,

    /**
     * Reservation is fulfilled (book picked up)
     */
    FULFILLED,

    /**
     * Reservation is expired
     */
    EXPIRED,

    /**
     * Reservation is cancelled by user or system
     */
    CANCELLED
}
