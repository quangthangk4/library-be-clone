package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.*;

import java.util.List;

public record CreatePublicationRequest(
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "Invalid ISBN format. Must be 10 or 13 digits")
    String isbn,

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    String title,

    @Size(max = 255, message = "Subtitle must not exceed 255 characters")
    String subtitle,

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    String description,

    @NotBlank(message = "Language is required")
    @Size(max = 20, message = "Language must not exceed 20 characters")
    String language,

    @Min(value = 1, message = "Number of pages must be at least 1")
    Integer numberOfPages,

    @NotNull(message = "Publisher ID is required")
    Long publisherId,

    @NotNull(message = "At least one author is required")
    @Size(min = 1, message = "At least one author is required")
    List<Long> authorIds,

    @Min(value = 1000, message = "Publication year must be at least 1000")
    @Max(value = 9999, message = "Publication year must not exceed 9999")
    Integer publicationYear,

    @Size(max = 100, message = "Edition must not exceed 100 characters")
    String edition,

    @Size(max = 500, message = "Cover image URL must not exceed 500 characters")
    String coverImageUrl,

    List<Long> categoryIds,

    List<Long> tagIds
) {
}
