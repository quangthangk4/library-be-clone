package com.library.circulation.domain.entities;

/**
 * Enum representing the status of a reservation.
 */
public enum ReservationStatus {
    /**
     * Reservation is pending - waiting for item availability.
     */
    PENDING,

    /**
     * Reservation is fulfilled - item is available for pickup.
     */
    FULFILLED,

    /**
     * Reservation is cancelled - user cancelled the reservation.
     */
    CANCELLED,

    /**
     * Reservation is expired - user did not pick up the item in time.
     */
    EXPIRED
}
