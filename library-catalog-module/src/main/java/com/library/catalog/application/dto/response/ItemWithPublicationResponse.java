package com.library.catalog.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ItemWithPublicationResponse(
    Long id,
    PublicationResponse publication,
    String barcode,
    String status,
    String itemType,
    String location,
    LocalDate acquiredDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
