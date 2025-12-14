package com.library.circulation.infrastructure.persistence.repository;

import com.library.circulation.domain.entities.ReservationStatus;
import com.library.circulation.infrastructure.persistence.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for ReservationEntity.
 */
@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    /**
     * Find all reservations for a user.
     */
    List<ReservationEntity> findByUserId(Long userId);

    /**
     * Find all reservations for a publication.
     */
    List<ReservationEntity> findByPublicationId(Long publicationId);

    /**
     * Find pending reservations for a publication, ordered by reservation date.
     */
    @Query("SELECT r FROM ReservationEntity r WHERE r.publicationId = :publicationId AND r.status = 'PENDING' ORDER BY r.reservationDate ASC")
    List<ReservationEntity> findPendingByPublicationIdOrderByReservationDate(@Param("publicationId") Long publicationId);

    /**
     * Check if user has a pending reservation for a publication.
     */
    boolean existsByUserIdAndPublicationIdAndStatus(Long userId, Long publicationId, ReservationStatus status);
}
