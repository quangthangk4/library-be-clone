package com.library.circulation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain entity representing a fine for overdue or damaged books
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fine {
    private Long id;
    private Long loanId;
    private Long userId;
    private BigDecimal amount;
    private String reason;
    private LocalDate issueDate;
    private LocalDate paidDate;
    private FineStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if fine is paid
     */
    public boolean isPaid() {
        return this.status == FineStatus.PAID;
    }

    /**
     * Business logic: Check if fine is pending
     */
    public boolean isPending() {
        return this.status == FineStatus.PENDING;
    }

    /**
     * Business logic: Mark fine as paid
     */
    public void markAsPaid() {
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Fine is already paid");
        }
        if (this.status == FineStatus.WAIVED) {
            throw new IllegalStateException("Cannot pay a waived fine");
        }
        this.paidDate = LocalDate.now();
        this.status = FineStatus.PAID;
    }

    /**
     * Business logic: Waive fine
     */
    public void waive(String reason) {
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Cannot waive a paid fine");
        }
        this.status = FineStatus.WAIVED;
        this.notes = reason;
    }

    /**
     * Business logic: Calculate fine for overdue days
     */
    public static BigDecimal calculateOverdueFine(long daysOverdue, BigDecimal dailyRate) {
        if (daysOverdue <= 0) {
            return BigDecimal.ZERO;
        }
        return dailyRate.multiply(BigDecimal.valueOf(daysOverdue));
    }

    /**
     * Business logic: Validate fine data
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fine amount must be non-negative");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Fine reason cannot be empty");
        }
        if (issueDate == null) {
            throw new IllegalArgumentException("Issue date cannot be null");
        }
    }
}
