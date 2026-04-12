package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.enums.ReservationStatus;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * JPA Entity for Reservation table.
 */
@Entity
@Table(name = "reservations", indexes = {
    @Index(name = "idx_reservation_user_id", columnList = "userId"),
    @Index(name = "idx_reservation_publication_id", columnList = "publicationId"),
    @Index(name = "idx_reservation_status", columnList = "status")
})
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReservationEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "publication_id", nullable = false)
    private Long publicationId;

    @Column(name = "reservation_date", nullable = false)
    private Instant reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(name = "queue_position", nullable = false)
    @Builder.Default
    private Integer queuePosition = 0;

    @Column(name = "hold_expiration_time")
    private Instant holdExpirationTime;

    protected ReservationEntity() {}
}
