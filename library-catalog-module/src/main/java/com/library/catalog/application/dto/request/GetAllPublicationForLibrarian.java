package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.Min;

public record GetAllPublicationForLibrarian(
    String keyword,

    Long categoryId,

    Integer year,

    AvailabilityFilter availability,

    String sortBy,

    SortDirection direction,

    @Min(value = 0, message = "Page must be >= 0")
    int page,

    @Min(value = 1, message = "Size must be >= 1")
    int size
) {
    public GetAllPublicationForLibrarian {
        if (page < 0) page = 0;
        if (size < 1) size = 20;
    }

    public enum AvailabilityFilter {
        ALL,
        HAS_ITEMS,
        NO_ITEMS
    }

    public enum SortDirection {
        ASC,
        DESC
    }
}
