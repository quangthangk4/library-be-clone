package com.library.catalog.dto.response.publication;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class LibrarianPublicationListResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long publicationId;
    private String title;
    private String subtitle;
    private String coverImageUrl;
    private List<String> authorNames;
    private Integer publicationYear;
    private Long totalItems;
    private Instant createdAt;
}
