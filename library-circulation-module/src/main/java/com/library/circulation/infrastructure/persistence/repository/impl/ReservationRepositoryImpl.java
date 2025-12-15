package com.library.circulation.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.entities.ReservationStatus;
import com.library.circulation.domain.repository.ReservationRepository;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.circulation.infrastructure.persistence.entity.ReservationEntity;
import com.library.circulation.infrastructure.persistence.mapper.ReservationEntityMapper;
import com.library.circulation.infrastructure.persistence.repository.ReservationJpaRepository;
import com.library.user.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of ReservationRepository.
 */
@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;
    private final ReservationEntityMapper entityMapper;

    @Override
    public Reservation save(Reservation reservation) {
        var entity = entityMapper.toEntity(reservation);
        var savedEntity = jpaRepository.save(entity);
        return entityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Reservation> findById(ReservationId reservationId) {
        return jpaRepository.findById(reservationId.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Reservation> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Reservation reservation) {
        jpaRepository.deleteById(reservation.getId().getValue());
    }

    @Override
    public void deleteById(ReservationId reservationId) {
        jpaRepository.deleteById(reservationId.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public List<Reservation> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByPublicationId(PublicationId publicationId) {
        return jpaRepository.findByPublicationId(publicationId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findPendingByPublicationId(PublicationId publicationId) {
        return jpaRepository.findPendingByPublicationIdOrderByReservationDate(publicationId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsPendingByUserAndPublication(UserId userId, PublicationId publicationId) {
        return jpaRepository.existsByUserIdAndPublicationIdAndStatus(
            userId.getValue(),
            publicationId.getValue(),
            ReservationStatus.PENDING
        );
    }

    @Override
    public int getQueuePosition(PublicationId publicationId, ReservationId reservationId) {
        // Get all pending reservations for this publication, ordered by date
        List<ReservationEntity> pendingReservations =
            jpaRepository.findPendingByPublicationIdOrderByReservationDate(publicationId.getValue());

        // Find the position of the given reservation (1-based index)
        for (int i = 0; i < pendingReservations.size(); i++) {
            if (pendingReservations.get(i).getId().equals(reservationId.getValue())) {
                return i + 1; // Return 1-based position
            }
        }

        // Return 0 if reservation is not in the pending queue
        // (e.g., it's fulfilled, cancelled, or expired)
        return 0;
    }
}
