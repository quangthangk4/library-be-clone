package com.library.circulation.infrastructure.persistence.mapper;

import com.library.catalog.domain.valueobject.PublicationId;
import com.library.circulation.domain.entities.Reservation;
import com.library.circulation.domain.valueobject.ReservationId;
import com.library.circulation.infrastructure.persistence.entity.ReservationEntity;
import com.library.user.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ReservationEntity and Reservation domain model.
 */
@Component
public class ReservationEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public ReservationEntity toEntity(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationEntity entity = ReservationEntity.builder()
            .userId(reservation.getUserId().getValue())
            .publicationId(reservation.getPublicationId().getValue())
            .reservationDate(reservation.getReservationDate())
            .status(reservation.getStatus())
            .notificationSentDate(reservation.getNotificationSentDate())
            .build();

        if (reservation.getId() != null) {
            entity.setId(reservation.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public Reservation toDomainModel(ReservationEntity entity) {
        if (entity == null) {
            return null;
        }

        ReservationId id = ReservationId.of(entity.getId());
        UserId userId = UserId.of(entity.getUserId());
        PublicationId publicationId = PublicationId.of(entity.getPublicationId());

        return Reservation.createForMapper(
            id,
            userId,
            publicationId,
            entity.getReservationDate(),
            entity.getStatus(),
            entity.getNotificationSentDate()
        );
    }
}
