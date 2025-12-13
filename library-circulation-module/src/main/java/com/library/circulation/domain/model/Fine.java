package com.library.circulation.domain.model;

import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Fine domain model
 */
public class Fine {
    private final FineId id;
    private final String userId;
    private final String loanId;
    private final Money amount;
    private final String reason;
    private final LocalDate issuedDate;
    private LocalDate paidDate;
    private FineStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Fine(FineId id,
               String userId,
               String loanId,
               Money amount,
               String reason,
               LocalDate issuedDate,
               LocalDate paidDate,
               FineStatus status,
               LocalDateTime createdAt,
               LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.loanId = loanId;
        this.amount = amount;
        this.reason = reason;
        this.issuedDate = issuedDate;
        this.paidDate = paidDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Fine create(String userId, String loanId, Money amount, String reason) {
        if (amount.isNegativeOrZero()) {
            throw new IllegalArgumentException("Fine amount must be positive");
        }
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Fine reason cannot be empty");
        }

        FineId id = FineId.generate();
        LocalDate now = LocalDate.now();
        LocalDateTime timestamp = LocalDateTime.now();

        return new Fine(id, userId, loanId, amount, reason, now, null, FineStatus.UNPAID, timestamp, timestamp);
    }

    // Business logic: Pay fine
    public void pay() {
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Fine is already paid");
        }
        if (this.status == FineStatus.WAIVED) {
            throw new IllegalStateException("Cannot pay a waived fine");
        }
        this.paidDate = LocalDate.now();
        this.status = FineStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Waive fine
    public void waive() {
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Cannot waive a paid fine");
        }
        if (this.status == FineStatus.WAIVED) {
            throw new IllegalStateException("Fine is already waived");
        }
        this.status = FineStatus.WAIVED;
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic: Check if fine is paid
    public boolean isPaid() {
        return this.status == FineStatus.PAID;
    }

    // Business logic: Check if fine is outstanding
    public boolean isOutstanding() {
        return this.status == FineStatus.UNPAID;
    }

    // Getters
    public FineId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLoanId() {
        return loanId;
    }

    public Money getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public FineStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
