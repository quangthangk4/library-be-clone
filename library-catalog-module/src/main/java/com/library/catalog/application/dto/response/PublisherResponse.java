package com.library.catalog.application.dto.response;

import java.time.LocalDateTime;

public record PublisherResponse(
    Long id,
    String publisherName,
    String address,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
