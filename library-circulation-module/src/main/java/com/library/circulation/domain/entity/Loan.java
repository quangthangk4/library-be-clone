package com.library.circulation.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Domain entity representing a book loan
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Business logic: Check if loan is overdue
     */
    public boolean isOverdue() {
        if (this.returnDate != null) {
            return false; // Already returned
        }
        return LocalDate.now().isAfter(this.dueDate);
    }

    /**
     * Business logic: Calculate days overdue
     */
    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(this.dueDate, LocalDate.now());
    }

    /**
     * Business logic: Check if loan is active
     */
    public boolean isActive() {
        return this.status == LoanStatus.ACTIVE;
    }

    /**
     * Business logic: Return book
     */
    public void returnBook() {
        if (this.returnDate != null) {
            throw new IllegalStateException("Book already returned");
        }
        this.returnDate = LocalDate.now();
        this.status = LoanStatus.RETURNED;
    }

    /**
     * Business logic: Extend due date
     */
    public void extendDueDate(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Extension days must be positive");
        }
        if (this.returnDate != null) {
            throw new IllegalStateException("Cannot extend due date for returned book");
        }
        this.dueDate = this.dueDate.plusDays(days);
    }

    /**
     * Business logic: Validate loan data
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (bookId == null) {
            throw new IllegalArgumentException("Book ID cannot be null");
        }
        if (loanDate == null) {
            throw new IllegalArgumentException("Loan date cannot be null");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (loanDate.isAfter(dueDate)) {
            throw new IllegalArgumentException("Loan date cannot be after due date");
        }
        if (returnDate != null && returnDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Return date cannot be before loan date");
        }
    }
}
