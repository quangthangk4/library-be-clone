package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.enums.TransactionStatus;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * JPA Entity for BorrowingTransaction table.
 */
@Entity
@Table(name = "borrowing_transactions", indexes = {
    @Index(name = "idx_borrow_user_id", columnList = "userId"),
    @Index(name = "idx_borrow_item_id", columnList = "itemId"),
    @Index(name = "idx_borrow_due_date", columnList = "dueDate")
})
@Getter
@Setter
@AllArgsConstructor
@Builder
public class BorrowingTransactionEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "librarian_id_issue")
    private Long librarianIdIssue;

    @Column(name = "librarian_id_return")
    private Long librarianIdReturn;

    @Column(name = "borrowed_date")
    private Instant borrowedDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    @Column(name = "picked_up_deadline", nullable = false)
    @Builder.Default
    private Instant pickedUpDeadline = Instant.now().plus(3, DAYS);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.WAITING_FOR_PICKUP;

    @Column(nullable = false)
    @Builder.Default
    private Integer renewalCount = 0;

    protected BorrowingTransactionEntity() {
        // Default constructor for JPA
    }
}
