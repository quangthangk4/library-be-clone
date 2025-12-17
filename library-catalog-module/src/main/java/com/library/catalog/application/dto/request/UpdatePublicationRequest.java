package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdatePublicationRequest(
    @Size(max = 255, message = "Title must not exceed 255 characters")
    String title,

    @Size(max = 255, message = "Subtitle must not exceed 255 characters")
    String subtitle,

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    String description,

    @Size(max = 20, message = "Language must not exceed 20 characters")
    String language,

    @Min(value = 1, message = "Number of pages must be at least 1")
    Integer numberOfPages,

    @Min(value = 1000, message = "Publication year must be at least 1000")
    @Max(value = 9999, message = "Publication year must not exceed 9999")
    Integer publicationYear,

    @Size(max = 100, message = "Edition must not exceed 100 characters")
    String edition,

    @Size(max = 500, message = "Cover image URL must not exceed 500 characters")
    String coverImageUrl,

    @Size(max = 50, message = "Size must not exceed 50 characters")
    String size,

    @Positive(message = "Weight must be positive")
    Double weight,

    @NotNull(message = "Publisher ID is required")
    Long publisherId,

    @NotNull(message = "At least one author is required")
    List<Long> authorIds,

    List<Long> categoryIds,

    List<Long> tagIds
) {
}
