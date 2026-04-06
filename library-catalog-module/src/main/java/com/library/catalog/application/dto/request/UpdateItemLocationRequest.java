package com.library.catalog.application.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateItemLocationRequest(
    @Size(max = 100, message = "Branch must not exceed 100 characters")
    String branch,
    @Size(max = 100, message = "Shelf must not exceed 100 characters")
    String shelf
) {
}
