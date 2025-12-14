package com.library.catalog.domain.service;

import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.PublicationId;

/**
 * Domain service for Item-related business logic.
 */
public interface ItemDomainService {
    /**
     * Validates that a barcode is unique in the system.
     * Throws exception if barcode already exists.
     */
    void validateUniqueBarcode(Barcode barcode);

    /**
     * Validates that a publication exists before creating an item for it.
     * Throws exception if publication not found.
     */
    void validatePublicationExists(PublicationId publicationId);

    /**
     * Checks if a barcode is available (not used by another item).
     */
    boolean isBarcodeAvailable(Barcode barcode);
}
