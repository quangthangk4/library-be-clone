package com.library.circulation.domain.entities;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.enums.ReservationStatus;
import com.library.circulation.domain.event.ReservationCreatedEvent;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.user.domain.valueobject.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {
    // Identity
    private ReservationId id;
    private UserId userId;
    private PublicationId publicationId;

    // Reservation metadata
    private Instant reservationDate;
    private ReservationStatus status;
    private int queuePosition;
    private Instant holdExpirationTime;
    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    protected Reservation() {
        // For ORM
    }

    public static Reservation create(
            UserId userId,
            PublicationId publicationId,
            Instant reservationDate) {
        Reservation reservation = new Reservation(
            ReservationId.generate(),
            userId,
            publicationId,
            reservationDate,
            ReservationStatus.PENDING,
            0, // Initial queue position will be set by the service
            null // Hold expiration time will be set when it's the user's turn
        );
        reservation.addDomainEvent(new ReservationCreatedEvent(reservation.getId(), userId, publicationId));
        return reservation;
    }


    public void fulfill() {
        if (this.status != ReservationStatus.PENDING) {
            throw new IllegalStateException(
                "Can only fulfill pending reservations. Current status: " + this.status
            );
        }

        this.status = ReservationStatus.FULFILLED;
    }

    public void cancel() {
        if (this.status == ReservationStatus.FULFILLED) {
            throw new IllegalStateException("Cannot cancel fulfilled reservation");
        }
        if (this.status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled");
        }

        this.status = ReservationStatus.CANCELLED;
    }

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }
}
