package com.library.catalog.dto.response.publication;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class LibrarianPublicationListResponse {
    private long publicationId;
    private String title;
    private String subtitle;
    private String coverImageUrl;
    private List<String> authorNames;
    private Integer publicationYear;
    private long totalItems;
    private Instant createdAt;
}