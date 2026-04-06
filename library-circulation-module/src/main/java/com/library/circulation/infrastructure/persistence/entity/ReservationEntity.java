package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.entities.ReservationStatus;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column( nullable = false)
    private Long publicationId;

    @Column( nullable = false)
    private Instant reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(nullable = false)
    @Builder.Default
    private Integer queuePosition = 0;

    private Instant holdExpirationTime;

    private Instant notificationSentDate;
}
