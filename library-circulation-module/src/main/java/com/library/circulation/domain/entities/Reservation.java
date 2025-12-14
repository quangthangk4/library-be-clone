package com.library.circulation.domain.entities;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.event.ReservationCreatedEvent;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.user.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Reservation Aggregate Root - represents a user's reservation for a publication.
 * Manages the lifecycle of reserving a publication when all items are borrowed.
 */
@Getter
public class Reservation {
    // Identity
    private final ReservationId id;
    private final UserId userId;
    private final PublicationId publicationId;

    // Reservation metadata
    private final LocalDateTime reservationDate;
    private ReservationStatus status;
    private LocalDateTime notificationSentDate;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    private Reservation(
            ReservationId id,
            UserId userId,
            PublicationId publicationId,
            LocalDateTime reservationDate,
            ReservationStatus status,
            LocalDateTime notificationSentDate) {
        this.id = id;
        this.userId = userId;
        this.publicationId = publicationId;
        this.reservationDate = reservationDate;
        this.status = status;
        this.notificationSentDate = notificationSentDate;
    }

    /**
     * Factory method to create a new reservation.
     */
    public static Reservation create(
            UserId userId,
            PublicationId publicationId) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (publicationId == null) {
            throw new IllegalArgumentException("Publication ID cannot be null");
        }

        ReservationId id = ReservationId.generate();
        LocalDateTime reservationDate = LocalDateTime.now();

        Reservation reservation = new Reservation(
            id,
            userId,
            publicationId,
            reservationDate,
            ReservationStatus.PENDING,
            null // notificationSentDate
        );

        reservation.addDomainEvent(new ReservationCreatedEvent(id, userId, publicationId));

        return reservation;
    }

    /**
     * Factory method for mapper (when loading from database).
     */
    public static Reservation createForMapper(
            ReservationId id,
            UserId userId,
            PublicationId publicationId,
            LocalDateTime reservationDate,
            ReservationStatus status,
            LocalDateTime notificationSentDate) {
        return new Reservation(
            id,
            userId,
            publicationId,
            reservationDate,
            status,
            notificationSentDate
        );
    }

    // ============== Business Logic Methods ==============

    /**
     * Fulfill the reservation - item is now available for pickup.
     */
    public void fulfill() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException(
                "Can only fulfill pending reservations. Current status: " + this.status
            );
        }

        this.status = ReservationStatus.FULFILLED;
    }

    /**
     * Cancel the reservation.
     */
    public void cancel() {
        if (this.status == ReservationStatus.FULFILLED) {
            throw new IllegalStateException("Cannot cancel fulfilled reservation");
        }
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }

        this.status = ReservationStatus.CANCELLED;
    }

    /**
     * Expire the reservation - user did not pick up in time.
     */
    public void expire() {
        if (this.status != ReservationStatus.FULFILLED) {
            throw new IllegalStateException(
                "Can only expire fulfilled reservations. Current status: " + this.status
            );
        }

        this.status = ReservationStatus.EXPIRED;
    }

    /**
     * Send notification to user that item is available.
     */
    public void sendNotification() {
        if (this.status != ReservationStatus.FULFILLED) {
            throw new IllegalStateException("Can only notify for fulfilled reservations");
        }

        this.notificationSentDate = LocalDateTime.now();
    }

    // ============== Domain Events Management ==============

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }
}
