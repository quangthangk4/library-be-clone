package com.library.circulation.application.mapper;

import com.library.circulation.application.dto.response.FineResponse;
import com.library.circulation.domain.entities.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * MapStruct mapper for Fine.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FineMapper {

    /**
     * Map Fine to FineResponse.
     * Note: userId, userFullName, and itemBarcode must be set manually in use case.
     */
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "transactionId", source = "transactionId.value")
    @Mapping(target = "paymentStatus", expression = "java(fine.getPaymentStatus().name())")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userFullName", ignore = true)
    @Mapping(target = "itemBarcode", ignore = true)
    FineResponse toResponse(Fine fine);
}
