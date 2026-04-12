package com.library.catalog.dto.response.publication;

import com.library.catalog.domain.enums.FacultyTarget;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicationResponse {
    private Long id;
    private String isbn;
    private String title;
    private String subtitle;
    private String description;
    private String language;
    private Integer numberOfPages;
    private String aiSummary;
    private FacultyTarget aiTargetAudience;
    private String fileUrl;
    private Integer publicationYear;
    private Integer edition;
    private String coverImageUrl;
    private String size; // e.g., "20x15x3 cm"
    private Double weight; // in grams
}
