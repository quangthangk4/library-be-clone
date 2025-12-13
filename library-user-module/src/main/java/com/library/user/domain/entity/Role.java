package com.library.user.domain.entity;

/**
 * Role enumeration for different user types in the library system
 */
public enum Role {
    /**
     * System administrator with full access
     */
    ADMIN,

    /**
     * Librarian who manages books and loans
     */
    LIBRARIAN,

    /**
     * Regular library member who can borrow books
     */
    MEMBER
}
