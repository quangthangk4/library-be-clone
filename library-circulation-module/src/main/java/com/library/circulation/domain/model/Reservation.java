package com.library.circulation.domain.model;

import com.library.circulation.domain.valueobject.ReservationId;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Reservation domain model
 */
public class Reservation {
    private final ReservationId id;
    private final String userId;
    private final String bookId;
    private final LocalDate reservationDate;
    private LocalDate expiryDate;
    private ReservationStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Reservation(ReservationId id,
                      String userId,
                      String bookId,
                      LocalDate reservationDate,
                      LocalDate expiryDate,
                      ReservationStatus status,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Reservation create(String userId, String bookId, int reservationPeriodDays) {
        if (reservationPeriodDays <= 0) {
            throw new IllegalArgumentException("Reservation period must be positive");
        }

        ReservationId id = ReservationId.generate();
        LocalDate now = LocalDate.now();
        LocalDate expiry = now.plusDays(reservationPeriodDays);
        LocalDateTime timestamp = LocalDateTime.now();

        return new Reservation(id, userId, bookId, now, expiry, ReservationStatus.PENDING, timestamp, timestamp);
    }

    // Business logic: Fulfill reservation (convert to loan)
    public void fulfill() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only pending reservations can be fulfilled");
        }
        if (isExpired()) {
            throw new IllegalStateException("Cannot fulfill expired reservation");
        }
        this.status = ReservationStatus.FULFILLED;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Cancel reservation
    public void cancel() {
        if (this.status == ReservationStatus.FULFILLED) {
            throw new IllegalStateException("Cannot cancel fulfilled reservation");
        }
        this.status = ReservationStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Mark as expired
    public void expire() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only pending reservations can expire");
        }
        this.status = ReservationStatus.EXPIRED;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Check if reservation is expired
    public boolean isExpired() {
        if (this.status != ReservationStatus.PENDING) {
            return false;
        }
        return LocalDate.now().isAfter(this.expiryDate);
    }

    // Business logic: Check if reservation is active
    public boolean isActive() {
        return this.status == ReservationStatus.PENDING && !isExpired();
    }

    // Getters
    public ReservationId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
