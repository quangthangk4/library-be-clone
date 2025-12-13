package com.library.catalog.domain.entity;

/**
 * Book availability status enumeration
 */
public enum BookStatus {
    /**
     * Book is available for borrowing
     */
    AVAILABLE,

    /**
     * Book is currently borrowed
     */
    BORROWED,

    /**
     * Book is reserved by a user
     */
    RESERVED,

    /**
     * Book is being maintained or repaired
     */
    MAINTENANCE,

    /**
     * Book is lost
     */
    LOST,

    /**
     * Book is damaged and cannot be borrowed
     */
    DAMAGED
}
