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
    String branch,
    String shelf,
    String condition,
    LocalDate acquiredDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
