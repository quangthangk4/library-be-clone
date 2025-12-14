package com.library.circulation.infrastructure.persistence.mapper;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.circulation.infrastructure.persistence.entity.BorrowingTransactionEntity;
import com.library.user.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between BorrowingTransactionEntity and BorrowingTransaction domain model.
 */
@Component
public class BorrowingTransactionEntityMapper {

    /**
     * Convert domain model to entity.
     */
    public BorrowingTransactionEntity toEntity(BorrowingTransaction transaction) {
        if (transaction == null) {
            return null;
        }

        BorrowingTransactionEntity entity = BorrowingTransactionEntity.builder()
            .userId(transaction.getUserId().getValue())
            .itemId(transaction.getItemId().getValue())
            .librarianIdIssue(transaction.getLibrarianIdIssue() != null ?
                transaction.getLibrarianIdIssue().getValue() : null)
            .librarianIdReturn(transaction.getLibrarianIdReturn() != null ?
                transaction.getLibrarianIdReturn().getValue() : null)
            .borrowedDate(transaction.getBorrowedDate())
            .dueDate(transaction.getDueDate())
            .returnedDate(transaction.getReturnedDate())
            .status(transaction.getStatus())
            .renewalCount(transaction.getRenewalCount())
            .build();

        if (transaction.getId() != null) {
            entity.setId(transaction.getId().getValue());
        }

        return entity;
    }

    /**
     * Convert entity to domain model.
     */
    public BorrowingTransaction toDomainModel(BorrowingTransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        TransactionId id = TransactionId.of(entity.getId());
        UserId userId = UserId.of(entity.getUserId());
        ItemId itemId = ItemId.of(entity.getItemId());
        UserId librarianIdIssue = entity.getLibrarianIdIssue() != null ?
            UserId.of(entity.getLibrarianIdIssue()) : null;
        UserId librarianIdReturn = entity.getLibrarianIdReturn() != null ?
            UserId.of(entity.getLibrarianIdReturn()) : null;

        return BorrowingTransaction.createForMapper(
            id,
            userId,
            itemId,
            librarianIdIssue,
            librarianIdReturn,
            entity.getBorrowedDate(),
            entity.getDueDate(),
            entity.getReturnedDate(),
            entity.getStatus(),
            entity.getRenewalCount()
        );
    }
}
