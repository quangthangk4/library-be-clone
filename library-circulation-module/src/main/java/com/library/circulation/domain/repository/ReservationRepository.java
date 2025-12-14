package com.library.circulation.domain.repository;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Reservation aggregate.
 * Defines operations for persisting and retrieving reservations.
 */
public interface ReservationRepository {

    /**
     * Save a reservation.
     */
    Reservation save(Reservation reservation);

    /**
     * Find reservation by ID.
     */
    Optional<Reservation> findById(ReservationId reservationId);

    /**
     * Find all reservations.
     */
    List<Reservation> findAll();

    /**
     * Delete a reservation.
     */
    void delete(Reservation reservation);

    /**
     * Delete reservation by ID.
     */
    void deleteById(ReservationId reservationId);

    /**
     * Count all reservations.
     */
    long count();

    /**
     * Find all reservations for a user.
     */
    List<Reservation> findByUserId(UserId userId);

    /**
     * Find all reservations for a publication.
     */
    List<Reservation> findByPublicationId(PublicationId publicationId);

    /**
     * Find pending reservations for a publication, ordered by reservation date.
     */
    List<Reservation> findPendingByPublicationId(PublicationId publicationId);

    /**
     * Check if user has a pending reservation for a publication.
     */
    boolean existsPendingByUserAndPublication(UserId userId, PublicationId publicationId);
}
