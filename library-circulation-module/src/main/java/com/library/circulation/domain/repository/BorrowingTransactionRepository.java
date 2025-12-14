package com.library.circulation.domain.repository;

import com.library.catalog.domain.valueobject.ItemId;
import com.library.circulation.domain.entities.BorrowingTransaction;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.user.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for BorrowingTransaction aggregate.
 * Defines operations for persisting and retrieving borrowing transactions.
 */
public interface BorrowingTransactionRepository {

    /**
     * Save a borrowing transaction.
     */
    BorrowingTransaction save(BorrowingTransaction transaction);

    /**
     * Find transaction by ID.
     */
    Optional<BorrowingTransaction> findById(TransactionId transactionId);

    /**
     * Find all transactions.
     */
    List<BorrowingTransaction> findAll();

    /**
     * Delete a transaction.
     */
    void delete(BorrowingTransaction transaction);

    /**
     * Delete transaction by ID.
     */
    void deleteById(TransactionId transactionId);

    /**
     * Count all transactions.
     */
    long count();

    /**
     * Find all transactions for a user.
     */
    List<BorrowingTransaction> findByUserId(UserId userId);

    /**
     * Find all transactions for an item.
     */
    List<BorrowingTransaction> findByItemId(ItemId itemId);

    /**
     * Find active transactions for a user.
     */
    List<BorrowingTransaction> findActiveByUserId(UserId userId);

    /**
     * Find all overdue transactions.
     */
    List<BorrowingTransaction> findOverdueTransactions();

    /**
     * Check if there is an active transaction for an item.
     */
    boolean existsActiveByItemId(ItemId itemId);
}
