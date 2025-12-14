package com.library.catalog.domain.entities;

import com.library.catalog.domain.valueobject.TagId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Tag entity - represents a keyword/label for categorizing publications.
 * Used for semantic search and AI recommendations.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tag {
    private final TagId id;
    private String tagName;

    /**
     * Factory method to create a new Tag with generated ID.
     */
    public static Tag create(String tagName) {
        validateTagName(tagName);
        return new Tag(TagId.generate(), tagName.trim().toLowerCase());
    }

    /**
     * Factory method for mapper (when loading from database).
     */
    public static Tag of(TagId id, String tagName) {
        validateTagName(tagName);
        return new Tag(id, tagName.trim().toLowerCase());
    }

    /**
     * Renames the tag.
     */
    public void rename(String newTagName) {
        validateTagName(newTagName);
        this.tagName = newTagName.trim().toLowerCase();
    }

    /**
     * Validates tag name.
     */
    private static void validateTagName(String tagName) {
        if (tagName == null || tagName.isBlank()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty");
        }
        if (tagName.length() > 50) {
            throw new IllegalArgumentException("Tag name must not exceed 50 characters");
        }
    }
}
