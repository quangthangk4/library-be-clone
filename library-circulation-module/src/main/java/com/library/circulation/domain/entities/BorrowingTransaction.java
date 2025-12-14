package com.library.circulation.domain.entities;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.event.TransactionCreatedEvent;
import com.library.circulation.domain.event.TransactionReturnedEvent;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.user.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * BorrowingTransaction Aggregate Root - represents a book borrowing transaction.
 * Manages the lifecycle of borrowing an item from the library.
 */
@Getter
public class BorrowingTransaction {
    // Identity
    private final TransactionId id;
    private final UserId userId;
    private final ItemId itemId;

    // Librarian tracking
    private UserId librarianIdIssue;
    private UserId librarianIdReturn;

    // Transaction dates
    private final LocalDateTime borrowedDate;
    private final LocalDate dueDate;
    private LocalDateTime returnedDate;

    // Status and metadata
    private TransactionStatus status;
    private int renewalCount;

    // Domain events
    private final List<Object> domainEvents = new ArrayList<>();

    private BorrowingTransaction(
            TransactionId id,
            UserId userId,
            ItemId itemId,
            UserId librarianIdIssue,
            UserId librarianIdReturn,
            LocalDateTime borrowedDate,
            LocalDate dueDate,
            LocalDateTime returnedDate,
            TransactionStatus status,
            int renewalCount) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.librarianIdIssue = librarianIdIssue;
        this.librarianIdReturn = librarianIdReturn;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.status = status;
        this.renewalCount = renewalCount;
    }

    /**
     * Factory method to create a new borrowing transaction.
     */
    public static BorrowingTransaction create(
            UserId userId,
            ItemId itemId,
            UserId librarianIdIssue,
            LocalDate dueDate) {

        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (itemId == null) {
            throw new IllegalArgumentException("Item ID cannot be null");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }

        TransactionId id = TransactionId.generate();
        LocalDateTime borrowedDate = LocalDateTime.now();

        BorrowingTransaction transaction = new BorrowingTransaction(
            id,
            userId,
            itemId,
            librarianIdIssue,
            null, // librarianIdReturn
            borrowedDate,
            dueDate,
            null, // returnedDate
            TransactionStatus.ACTIVE,
            0 // renewalCount
        );

        transaction.addDomainEvent(new TransactionCreatedEvent(id, userId, itemId));

        return transaction;
    }

    /**
     * Factory method for mapper (when loading from database).
     */
    public static BorrowingTransaction createForMapper(
            TransactionId id,
            UserId userId,
            ItemId itemId,
            UserId librarianIdIssue,
            UserId librarianIdReturn,
            LocalDateTime borrowedDate,
            LocalDate dueDate,
            LocalDateTime returnedDate,
            TransactionStatus status,
            int renewalCount) {
        return new BorrowingTransaction(
            id,
            userId,
            itemId,
            librarianIdIssue,
            librarianIdReturn,
            borrowedDate,
            dueDate,
            returnedDate,
            status,
            renewalCount
        );
    }

    // ============== Business Logic Methods ==============

    /**
     * Return the borrowed item.
     */
    public void returnItem(UserId librarianIdReturn) {
        if (this.status == TransactionStatus.RETURNED) {
            throw new IllegalStateException("Transaction is already returned");
        }

        this.returnedDate = LocalDateTime.now();
        this.librarianIdReturn = librarianIdReturn;
        this.status = TransactionStatus.RETURNED;

        addDomainEvent(new TransactionReturnedEvent(this.id, this.itemId));
    }

    /**
     * Renew the transaction (extend due date).
     */
    public void renew() {
        if (!canRenew()) {
            throw new IllegalStateException(
                "Cannot renew: renewal count is " + renewalCount + ", max is 2"
            );
        }

        if (this.status != TransactionStatus.ACTIVE) {
            throw new IllegalStateException(
                "Cannot renew: transaction status is " + this.status
            );
        }

        if (isOverdue()) {
            throw new IllegalStateException("Cannot renew an overdue transaction");
        }

        this.renewalCount++;
        // Extend due date by 14 days from current due date
    }

    /**
     * Mark transaction as overdue.
     */
    public void markAsOverdue() {
        if (this.status == TransactionStatus.RETURNED) {
            throw new IllegalStateException("Cannot mark returned transaction as overdue");
        }

        this.status = TransactionStatus.OVERDUE;
    }

    /**
     * Check if transaction can be renewed.
     */
    public boolean canRenew() {
        return this.renewalCount < 2
            && this.status == TransactionStatus.ACTIVE
            && !isOverdue();
    }

    /**
     * Check if transaction is overdue.
     */
    public boolean isOverdue() {
        if (this.status == TransactionStatus.RETURNED) {
            return false;
        }
        return LocalDate.now().isAfter(this.dueDate);
    }

    /**
     * Calculate days overdue.
     */
    public int calculateDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(this.dueDate, LocalDate.now());
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
