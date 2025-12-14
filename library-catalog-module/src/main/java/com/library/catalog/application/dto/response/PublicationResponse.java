package com.library.catalog.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record PublicationResponse(
    Long id,
    String isbn,
    String title,
    String subtitle,
    String description,
    String language,
    Integer numberOfPages,
    PublisherResponse publisher,
    List<AuthorResponse> authors,
    Integer publicationYear,
    String edition,
    String coverImageUrl,
    List<CategoryResponse> categories,
    List<TagResponse> tags,
    Long totalItems,
    Long availableItems,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
