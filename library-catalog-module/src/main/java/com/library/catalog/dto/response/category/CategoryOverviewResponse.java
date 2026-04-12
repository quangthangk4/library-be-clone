package com.library.catalog.dto.response.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryOverviewResponse {
    private Long id;
    private String name;
}
