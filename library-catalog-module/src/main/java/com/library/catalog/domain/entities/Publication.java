package com.library.catalog.domain.entities;

import com.library.catalog.domain.event.PublicationCreatedEvent;
import com.library.catalog.domain.valueobject.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * Publication aggregate root - represents a published work (book, journal, etc.).
 * This is the conceptual entity, separate from physical/digital copies (Items).
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Publication {
    // Identity
    private final PublicationId id;
    private ISBN isbn; // nullable - not all publications have ISBN
    private PublicationMetadata metadata;
    private PublisherId publisherId;
    // Physical properties
    private String size;      // e.g., "20x15x3 cm"
    private Double weight;    // in grams
    // Relationships (stored as IDs only in the domain model)
    private Set<AuthorId> authorIds;
    private Set<CategoryId> categoryIds;
    private Set<TagId> tagIds;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    /**
     * Factory method to create a new Publication.
     */
    public static Publication create(
            ISBN isbn,
            PublicationMetadata metadata,
            PublisherId publisherId,
            Set<AuthorId> authorIds,
            Integer publicationYear
    ) {
        // Validate required fields
        if (metadata == null) {
            throw new IllegalArgumentException("Publication metadata cannot be null");
        }
        if (publisherId == null) {
            throw new IllegalArgumentException("Publisher ID cannot be null");
        }
        if (authorIds == null || authorIds.isEmpty()) {
            throw new IllegalArgumentException("Publication must have at least one author");
        }
        if (publicationYear != null) {
            validatePublicationYear(publicationYear);
        }

        Publication publication = new Publication(
            PublicationId.generate(),
            isbn,
            metadata,
            publisherId,
            null, // size
            null, // weight
            new HashSet<>(authorIds),
            new HashSet<>(), // categories
            new HashSet<>()  // tags
        );

        // Add domain event
        publication.addDomainEvent(new PublicationCreatedEvent(
            publication.id,
            publication.isbn,
            publication.metadata.getTitle()
        ));

        return publication;
    }

    /**
     * Factory method for mapper (when loading from a database).
     * Does not trigger domain events.
     */
    public static Publication createForMapper(
            PublicationId id,
            ISBN isbn,
            PublicationMetadata metadata,
            PublisherId publisherId,
            String size,
            Double weight,
            Set<AuthorId> authorIds,
            Set<CategoryId> categoryIds,
            Set<TagId> tagIds
    ) {
        return new Publication(
            id,
            isbn,
            metadata,
            publisherId,
            size,
            weight,
            authorIds != null ? new HashSet<>(authorIds) : new HashSet<>(),
            categoryIds != null ? new HashSet<>(categoryIds) : new HashSet<>(),
            tagIds != null ? new HashSet<>(tagIds) : new HashSet<>()
        );
    }

    // ========== Business Logic Methods ==========

    /**
     * Updates publication metadata.
     */
    public void updateMetadata(PublicationMetadata newMetadata) {
        if (newMetadata == null) {
            throw new IllegalArgumentException("Metadata cannot be null");
        }
        this.metadata = newMetadata;
    }

    /**
     * Assigns an author to this publication.
     */
    public void assignAuthor(AuthorId authorId) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        if (this.authorIds.contains(authorId)) {
            throw new IllegalStateException("Author is already assigned to this publication");
        }
        this.authorIds.add(authorId);
    }

    /**
     * Removes an author from this publication.
     */
    public void removeAuthor(AuthorId authorId) {
        if (authorId == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        if (this.authorIds.size() <= 1) {
            throw new IllegalStateException("Publication must have at least one author");
        }
        this.authorIds.remove(authorId);
    }

    /**
     * Assigns a category to this publication.
     */
    public void assignCategory(CategoryId categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        this.categoryIds.add(categoryId);
    }

    /**
     * Removes a category from this publication.
     */
    public void removeCategory(CategoryId categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        this.categoryIds.remove(categoryId);
    }

    /**
     * Assigns a tag to this publication.
     */
    public void assignTag(TagId tagId) {
        if (tagId == null) {
            throw new IllegalArgumentException("Tag ID cannot be null");
        }
        this.tagIds.add(tagId);
    }

    /**
     * Removes a tag from this publication.
     */
    public void removeTag(TagId tagId) {
        if (tagId == null) {
            throw new IllegalArgumentException("Tag ID cannot be null");
        }
        this.tagIds.remove(tagId);
    }

    /**
     * Clears all categories.
     */
    public void clearCategories() {
        this.categoryIds.clear();
    }

    /**
     * Clears all tags.
     */
    public void clearTags() {
        this.tagIds.clear();
    }

    // ========== Physical Properties Management ==========

    /**
     * Updates physical dimensions.
     */
    public void updateSize(String newSize) {
        this.size = newSize != null ? newSize.trim() : null;
    }

    /**
     * Updates weight in grams.
     */
    public void updateWeight(Double newWeight) {
        if (newWeight != null && newWeight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = newWeight;
    }

    /**
     * Updates both physical properties.
     */
    public void updatePhysicalProperties(String newSize, Double newWeight) {
        updateSize(newSize);
        updateWeight(newWeight);
    }

    /**
     * Replaces all authors with a new set.
     */
    public void replaceAuthors(Set<AuthorId> newAuthorIds) {
        if (newAuthorIds == null || newAuthorIds.isEmpty()) {
            throw new IllegalArgumentException("Publication must have at least one author");
        }
        this.authorIds = new HashSet<>(newAuthorIds);
    }

    /**
     * Replaces all categories with a new set.
     */
    public void replaceCategories(Set<CategoryId> newCategoryIds) {
        this.categoryIds = newCategoryIds != null ? new HashSet<>(newCategoryIds) : new HashSet<>();
    }

    /**
     * Replaces all tags with a new set.
     */
    public void replaceTags(Set<TagId> newTagIds) {
        this.tagIds = newTagIds != null ? new HashSet<>(newTagIds) : new HashSet<>();
    }


    /**
     * Checks if this publication is in the given category.
     */
    public boolean hasCategory(CategoryId categoryId) {
        return categoryIds.contains(categoryId);
    }

    /**
     * Checks if this publication has the given tag.
     */
    public boolean hasTag(TagId tagId) {
        return tagIds.contains(tagId);
    }

    /**
     * Gets immutable copy of author IDs.
     */
    public Set<AuthorId> getAuthorIds() {
        return Collections.unmodifiableSet(authorIds);
    }

    /**
     * Gets immutable copy of category IDs.
     */
    public Set<CategoryId> getCategoryIds() {
        return Collections.unmodifiableSet(categoryIds);
    }

    /**
     * Gets immutable copy of tag IDs.
     */
    public Set<TagId> getTagIds() {
        return Collections.unmodifiableSet(tagIds);
    }

    // ========== Domain Events Management ==========

    /**
     * Polls and clears all domain events.
     */
    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    // ========== Validation ==========

    private static void validatePublicationYear(Integer year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < 0 || year > currentYear + 1) {
            throw new IllegalArgumentException(
                "Publication year must be between 0 and " + (currentYear + 1)
            );
        }
    }
}
