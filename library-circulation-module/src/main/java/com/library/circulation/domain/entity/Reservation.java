package com.library.circulation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain entity representing a book reservation
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate reservationDate;
    private LocalDate expiryDate;
    private LocalDate pickupDate;
    private ReservationStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if reservation is expired
     */
    public boolean isExpired() {
        if (this.pickupDate != null) {
            return false; // Already picked up
        }
        return LocalDate.now().isAfter(this.expiryDate);
    }

    /**
     * Business logic: Check if reservation is active
     */
    public boolean isActive() {
        return this.status == ReservationStatus.PENDING || this.status == ReservationStatus.READY;
    }

    /**
     * Business logic: Mark as ready for pickup
     */
    public void markAsReady() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Can only mark pending reservations as ready");
        }
        this.status = ReservationStatus.READY;
    }

    /**
     * Business logic: Fulfill reservation (book picked up)
     */
    public void fulfill() {
        if (this.status != ReservationStatus.READY) {
            throw new IllegalStateException("Can only fulfill ready reservations");
        }
        this.pickupDate = LocalDate.now();
        this.status = ReservationStatus.FULFILLED;
    }

    /**
     * Business logic: Cancel reservation
     */
    public void cancel() {
        if (this.status == ReservationStatus.FULFILLED) {
            throw new IllegalStateException("Cannot cancel fulfilled reservations");
        }
        this.status = ReservationStatus.CANCELLED;
    }

    /**
     * Business logic: Validate reservation data
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        if (reservationDate == null) {
            throw new IllegalArgumentException("Reservation date cannot be null");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null");
        }
        if (reservationDate.isAfter(expiryDate)) {
            throw new IllegalArgumentException("Reservation date cannot be after expiry date");
        }
    }
}
