package com.library.catalog.dto.response.publisher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublisherOverviewResponse {
    private Long id;
    private String name;
}
