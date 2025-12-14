package com.library.catalog.domain.entities;

/**
 * Enum representing the status of a library item (physical/digital copy).
 */
public enum ItemStatus {
    /**
     * Item is available for borrowing.
     */
    AVAILABLE,

    /**
     * Item is currently borrowed by a user.
     */
    BORROWED,

    /**
     * Item is reserved for a specific user.
     */
    RESERVED,

    /**
     * Item is under maintenance or repair.
     */
    IN_MAINTENANCE,

    /**
     * Item has been reported as lost.
     */
    LOST
}
