package com.library.catalog.domain.valueobject;

import lombok.Value;

/**
 * Value Object encapsulating publication metadata (title, description, language, etc.).
 * Immutable and self-validating.
 */
@Value
public class PublicationMetadata {
    String title;
    String subtitle;
    String description;
    String language;
    Integer numberOfPages;

    public PublicationMetadata(String title, String subtitle, String description,
                              String language, Integer numberOfPages) {
        // Validate title (required)
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

        this.title = title.trim();
        this.subtitle = subtitle != null ? subtitle.trim() : null;
        this.description = description != null ? description.trim() : null;
        this.language = language.trim();
        this.numberOfPages = numberOfPages;
    }

    /**
     * Creates PublicationMetadata with only required fields.
     */
    public static PublicationMetadata of(String title, String language) {
        return new PublicationMetadata(title, null, null, language, null);
    }

    /**
     * Creates a new PublicationMetadata with an updated title.
     */
    public PublicationMetadata withTitle(String newTitle) {
        return new PublicationMetadata(newTitle, this.subtitle, this.description,
                                      this.language, this.numberOfPages);
    }

    /**
     * Creates a new PublicationMetadata with an updated subtitle.
     */
    public PublicationMetadata withSubtitle(String newSubtitle) {
        return new PublicationMetadata(this.title, newSubtitle, this.description,
                                      this.language, this.numberOfPages);
    }

    /**
     * Creates a new PublicationMetadata with updated description.
     */
    public PublicationMetadata withDescription(String newDescription) {
        return new PublicationMetadata(this.title, this.subtitle, newDescription,
                                      this.language, this.numberOfPages);
    }

    /**
     * Checks if this publication has a description.
     */
    public boolean hasDescription() {
        return description != null && !description.isBlank();
    }

    /**
     * Checks if this publication has a subtitle.
     */
    public boolean hasSubtitle() {
        return subtitle != null && !subtitle.isBlank();
    }
}
