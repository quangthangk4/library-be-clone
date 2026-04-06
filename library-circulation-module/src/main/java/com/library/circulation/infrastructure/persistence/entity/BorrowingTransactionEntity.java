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
    @Index(name = "idx_borrow_user_id", columnList = "userId"),
    @Index(name = "idx_borrow_item_id", columnList = "itemId"),
    @Index(name = "idx_borrow_status", columnList = "status"),
    @Index(name = "idx_borrow_due_date", columnList = "dueDate")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowingTransactionEntity extends BaseEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long itemId;

    private Long librarianIdIssue;

    private Long librarianIdReturn;

    @Column(nullable = false)
    private LocalDateTime borrowedDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDateTime returnedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.ACTIVE;

    @Column(nullable = false)
    @Builder.Default
    private Integer renewalCount = 0;
}
