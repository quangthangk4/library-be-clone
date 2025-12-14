package com.library.catalog.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ItemResponse(
    Long id,
    Long publicationId,
    String publicationTitle,
    String barcode,
    String status,
    String itemType,
    String location,
    LocalDate acquiredDate,
    String size,
    Double weight,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
