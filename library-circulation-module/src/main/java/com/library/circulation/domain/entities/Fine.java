package com.library.circulation.domain.entities;

import com.library.circulation.domain.event.FineCreatedEvent;
import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.TransactionId;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Fine Aggregate Root - represents a fine for overdue transactions.
 * Manages the lifecycle of fines and their payment status.
 */
@Getter
public class Fine {
    // Identity
    private final FineId id;
    private final TransactionId transactionId;

    // Fine details
    private final BigDecimal fineAmount;
    private final LocalDate fineDate;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidDate;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    private Fine(
            FineId id,
            TransactionId transactionId,
            BigDecimal fineAmount,
            LocalDate fineDate,
            PaymentStatus paymentStatus,
            LocalDateTime paidDate) {
        this.id = id;
        this.transactionId = transactionId;
        this.fineAmount = fineAmount;
        this.fineDate = fineDate;
        this.paymentStatus = paymentStatus;
        this.paidDate = paidDate;
    }

    /**
     * Factory method to create a new fine.
     */
    public static Fine create(
            TransactionId transactionId,
            BigDecimal fineAmount) {

        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID cannot be null");
        }
        if (fineAmount == null || fineAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Fine amount must be greater than zero");
        }

        FineId id = FineId.generate();
        LocalDate fineDate = LocalDate.now();

        Fine fine = new Fine(
            id,
            transactionId,
            fineAmount,
            fineDate,
            PaymentStatus.UNPAID,
            null // paidDate
        );

        fine.addDomainEvent(new FineCreatedEvent(id, transactionId, fineAmount));

        return fine;
    }

    /**
     * Factory method for mapper (when loading from database).
     */
    public static Fine createForMapper(
            FineId id,
            TransactionId transactionId,
            BigDecimal fineAmount,
            LocalDate fineDate,
            PaymentStatus paymentStatus,
            LocalDateTime paidDate) {
        return new Fine(
            id,
            transactionId,
            fineAmount,
            fineDate,
            paymentStatus,
            paidDate
        );
    }

    // ============== Business Logic Methods ==============

    /**
     * Mark fine as paid.
     */
    public void markAsPaid() {
        if (this.paymentStatus == PaymentStatus.PAID) {
            throw new IllegalStateException("Fine is already paid");
        }

        this.paymentStatus = PaymentStatus.PAID;
        this.paidDate = LocalDateTime.now();
    }

    /**
     * Check if fine is overdue (unpaid for more than 30 days).
     */
    public boolean isOverdue() {
        if (this.paymentStatus == PaymentStatus.PAID) {
            return false;
        }

        long daysSinceFine = ChronoUnit.DAYS.between(this.fineDate, LocalDate.now());
        return daysSinceFine > 30;
    }

    // ============== Domain Events Management ==============

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    public List<Object> pollDomainEvents() {
        List<Object> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }
}
