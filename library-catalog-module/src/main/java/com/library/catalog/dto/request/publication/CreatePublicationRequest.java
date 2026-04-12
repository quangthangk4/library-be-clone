package com.library.catalog.dto.request.publication;

import com.library.catalog.domain.enums.FacultyTarget;

public record CreatePublicationRequest (
    String isbn,
    String title,
    String subtitle,
    String description,
    String language,
    Integer numberOfPages,
    String aiSummary,
    FacultyTarget aiTargetAudience,
    String fileUrl,
    Integer publicationYear,
    Integer edition,
    String coverImageUrl,
    String size, // e.g., "20x15x3 cm"
    Double weight, // in grams
    Long publisherId,
    Long[] authorId,
    Long[] tags,
    Long[] categories
){
}
