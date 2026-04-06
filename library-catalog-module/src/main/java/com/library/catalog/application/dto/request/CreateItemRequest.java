package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateItemRequest(
    @NotNull(message = "Publication ID is required")
    Long publicationId,

    @NotBlank(message = "Barcode is required")
    @Size(max = 50, message = "Barcode must not exceed 50 characters")
    @Pattern(regexp = "^[A-Z0-9\\-]+$", message = "Barcode must contain only uppercase letters, numbers, and hyphens")
    String barcode,

    @NotBlank(message = "Item type is required")
    @Pattern(regexp = "^(HARDCOVER|PAPERBACK|JOURNAL)$", message = "Item type must be HARDCOVER, PAPERBACK, or JOURNAL")
    String itemType,

    @Size(max = 100, message = "Branch must not exceed 100 characters")
    String branch,

    @Size(max = 100, message = "Shelf must not exceed 100 characters")
    String shelf
) {
}
