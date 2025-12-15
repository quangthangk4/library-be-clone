package com.library.circulation.application.mapper;

import com.library.catalog.domain.entities.Publication;
import com.library.circulation.application.dto.response.ReservationResponse;
import com.library.circulation.domain.entities.Reservation;
import com.library.user.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for Reservation.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    /**
     * Map Reservation to ReservationResponse.
     * Note: userFullName, publicationTitle, and queuePosition must be set manually in use case.
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "publicationId", source = "publicationId.value")
    @Mapping(target = "status", expression = "java(reservation.getStatus().name())")
    @Mapping(target = "userFullName", ignore = true)
    @Mapping(target = "publicationTitle", ignore = true)
    @Mapping(target = "queuePosition", ignore = true)
    ReservationResponse toResponse(Reservation reservation);

    /**
     * Map Reservation to ReservationResponse with enriched data.
     *
     * @param reservation the reservation entity
     * @param user the user entity
     * @param publication the publication entity
     * @param queuePosition the queue position
     * @return the enriched reservation response
     */
    default ReservationResponse toResponse(
            Reservation reservation,
            User user,
            Publication publication,
            int queuePosition) {
        return new ReservationResponse(
            reservation.getId().getValue(),
            reservation.getUserId().getValue(),
            user.getProfile().getFullName(),
            reservation.getPublicationId().getValue(),
            publication.getMetadata().getTitle(),
            reservation.getReservationDate(),
            reservation.getStatus().name(),
            reservation.getNotificationSentDate(),
            queuePosition
        );
    }
}
