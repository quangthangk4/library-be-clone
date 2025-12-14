package com.library.catalog.domain.service;

import com.library.catalog.domain.valueobject.ISBN;
import com.library.catalog.domain.valueobject.PublicationId;

/**
 * Domain service for Publication-related business logic that spans multiple aggregates.
 */
public interface PublicationDomainService {
    /**
     * Validates that an ISBN is unique in the system.
     * Throws exception if ISBN already exists.
     */
    void validateUniqueISBN(ISBN isbn);

    /**
     * Validates that a publication can be deleted (no items exist).
     * Throws exception if the publication has associated items.
     */
    void validateCanDeletePublication(PublicationId id);

    /**
     * Checks if an ISBN is available (not used by another publication).
     */
    boolean isISBNAvailable(ISBN isbn);

    /**
     * Checks if a publication exists by ID.
     */
    boolean publicationExists(PublicationId id);
}
