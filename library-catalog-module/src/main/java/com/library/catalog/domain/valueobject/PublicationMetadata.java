package com.library.catalog.domain.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Value Object encapsulating publication metadata (title, description, language, etc.).
 * Immutable and self-validating.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PublicationMetadata {
    String title;
    String subtitle;
    String description;
    String language;
    Integer numberOfPages;
    String aiSummary;
    String aiTargetAudience;
    String fileUrl;
    Integer publicationYear;
    String edition;
    String coverImageUrl;


    public static PublicationMetadata of(String title, String subtitle, String description,
                                         String language, Integer numberOfPages, String aiSummary,
                                         String aiTargetAudience, String fileUrl, Integer publicationYear,
                                         String edition, String coverImageUrl){
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Title must not exceed 255 characters");
        }

        // Validate subtitle (optional)
        if (subtitle != null && subtitle.length() > 255) {
            throw new IllegalArgumentException("Subtitle must not exceed 255 characters");
        }

        // Validate language (required)
        if (language == null || language.isBlank()) {
            throw new IllegalArgumentException("Language is required");
        }

        if (language.length() > 50) {
            throw new IllegalArgumentException("Language must not exceed 50 characters");
        }

        // Validate numberOfPages (optional, but must be positive if provided)
        if (numberOfPages != null && numberOfPages <= 0) {
            throw new IllegalArgumentException("Number of pages must be positive");
        }

        if (coverImageUrl == null || coverImageUrl.isBlank()) {
            throw new IllegalArgumentException("Cover image URL is required");
        }
        return new PublicationMetadata(title, subtitle, description, language, numberOfPages, aiSummary, aiTargetAudience, fileUrl, publicationYear, edition, coverImageUrl);
    }
}
