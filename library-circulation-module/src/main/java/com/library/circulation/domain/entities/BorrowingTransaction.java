package com.library.circulation.domain.entities;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.enums.TransactionStatus;
import com.library.circulation.domain.event.TransactionCreatedEvent;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.user.domain.valueobject.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BorrowingTransaction {
    private final TransactionId id;
    private final UserId userId;
    private final ItemId itemId;

    private UserId librarianIdIssue;
    private UserId librarianIdReturn;

    private final Instant borrowedDate;
    private final Instant dueDate;
    private Instant returnedDate;
    private Instant pickedUpDeadline;

    private TransactionStatus status;
    private int renewalCount;

    private final List<Object> domainEvents = new ArrayList<>();

    public static BorrowingTransaction create(UserId userId, ItemId itemId) {
        BorrowingTransaction borrowingTransaction = new BorrowingTransaction(
                TransactionId.generate(),
                userId,
                itemId,
                null, // librarianIdIssue to be set when item is issued
                null, // librarianIdReturn to be set when item is returned
                null, // borrowedDate
                null,//Instant.now().plus(14, ChronoUnit.DAYS), dueDate (2 weeks from now)
                null, // returnedDate to be set when item is returned
                Instant.now().plus(3, ChronoUnit.DAYS), // pickedUpDeadline (3 days to pick up after reservation, if applicable)
                TransactionStatus.WAITING_FOR_PICKUP,
                0 // renewalCount starts at 0
        );
        borrowingTransaction.addDomainEvent(new TransactionCreatedEvent(borrowingTransaction.getId(), userId, itemId));
        return borrowingTransaction;
    }

    public static BorrowingTransaction of(TransactionId id, UserId userId, ItemId itemId, UserId librarianIdIssue,
                                          Instant borrowedDate, Instant dueDate, UserId librarianIdReturn, Instant returnedDate,
                                          Instant pickedUpDeadline, TransactionStatus status, int renewalCount) {
        return new BorrowingTransaction(id, userId, itemId, librarianIdIssue, librarianIdReturn, borrowedDate,
                dueDate, returnedDate, pickedUpDeadline, status, renewalCount);
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
