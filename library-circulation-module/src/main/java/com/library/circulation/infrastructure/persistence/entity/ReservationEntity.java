package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.entities.ReservationStatus;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * JPA Entity for Reservation table.
 */
@Entity
@Table(name = "reservations", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_publication_id", columnList = "publication_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_reservation_date", columnList = "reservation_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "publication_id", nullable = false)
    private Long publicationId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(name = "notification_sent_date")
    private LocalDateTime notificationSentDate;
}
