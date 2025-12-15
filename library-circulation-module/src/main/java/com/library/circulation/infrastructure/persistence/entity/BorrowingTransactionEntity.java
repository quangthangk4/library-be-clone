package com.library.circulation.infrastructure.persistence.entity;

import com.library.circulation.domain.entities.TransactionStatus;
import com.library.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity for BorrowingTransaction table.
 */
@Entity
@Table(name = "borrowing_transactions", indexes = {
    @Index(name = "idx_borrow_user_id", columnList = "user_id"),
    @Index(name = "idx_borrow_item_id", columnList = "item_id"),
    @Index(name = "idx_borrow_status", columnList = "status"),
    @Index(name = "idx_borrow_due_date", columnList = "due_date")
})
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "borrowed_date", nullable = false)
    private LocalDateTime borrowedDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.ACTIVE;

    @Column(name = "renewal_count", nullable = false)
    @Builder.Default
    private Integer renewalCount = 0;
}
