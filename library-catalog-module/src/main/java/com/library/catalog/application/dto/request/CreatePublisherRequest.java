package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePublisherRequest(
    @NotBlank(message = "Publisher name is required")
    @Size(max = 255, message = "Publisher name must not exceed 255 characters")
    String publisherName,

    @Size(max = 500, message = "Address must not exceed 500 characters")
    String address
) {
}
