package com.library.catalog.dto.response.author;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorOverviewResponse {
    private Long id;
    private String name;
}
