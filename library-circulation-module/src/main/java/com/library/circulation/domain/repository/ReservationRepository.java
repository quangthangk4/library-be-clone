package com.library.circulation.domain.repository;

import com.library.circulation.domain.model.Reservation;
import com.library.circulation.domain.valueobject.ReservationId;

import java.util.List;
import java.util.Optional;

/**
 * Reservation repository interface (Port)
 */
public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(ReservationId id);
    List<Reservation> findByUserId(String userId);
    List<Reservation> findByBookId(String bookId);
    List<Reservation> findActiveReservations();
    List<Reservation> findExpiredReservations();
    List<Reservation> findAll();
    void delete(ReservationId id);
}
