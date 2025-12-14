package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.Min;

import java.util.List;

public record SearchPublicationRequest(
    String title,

    String authorName,

    String isbn,

    Long categoryId,

    Long publisherId,

    List<Long> tagIds,

    Integer yearFrom,

    Integer yearTo,

    @Min(value = 0, message = "Page must be >= 0")
    int page,

    @Min(value = 1, message = "Size must be >= 1")
    int size
) {
    public SearchPublicationRequest {
        if (page < 0) page = 0;
        if (size < 1) size = 20;
    }
}
