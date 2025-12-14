package com.library.circulation.application.mapper;

import com.library.circulation.application.dto.response.ReservationResponse;
import com.library.circulation.domain.entities.Reservation;
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
}
