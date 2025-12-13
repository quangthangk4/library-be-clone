package com.library.circulation.domain.entity;

/**
 * Loan status enumeration
 */
public enum LoanStatus {
    /**
     * Loan is active (book is borrowed)
     */
    ACTIVE,

    /**
     * Loan is overdue (past due date)
     */
    OVERDUE,

    /**
     * Loan is completed (book returned)
     */
    RETURNED,

    /**
     * Loan is cancelled
     */
    CANCELLED
}
