package com.library.circulation.domain.repository;

import com.library.circulation.domain.entities.Fine;
import com.library.circulation.domain.valueobject.FineId;
import com.library.circulation.domain.valueobject.TransactionId;
import com.library.user.domain.valueobject.UserId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Fine aggregate.
 * Defines operations for persisting and retrieving fines.
 */
public interface FineRepository {

    /**
     * Save a fine.
     */
    Fine save(Fine fine);

    /**
     * Find fine by ID.
     */
    Optional<Fine> findById(FineId fineId);

    /**
     * Find all fines.
     */
    List<Fine> findAll();

    /**
     * Delete a fine.
     */
    void delete(Fine fine);

    /**
     * Delete fine by ID.
     */
    void deleteById(FineId fineId);

    /**
     * Find fine by transaction ID.
     */
    Optional<Fine> findByTransactionId(TransactionId transactionId);

    /**
     * Find all fines for a user (through their transactions).
     */
    List<Fine> findByUserId(UserId userId);

    /**
     * Find unpaid fines for a user.
     */
    List<Fine> findUnpaidByUserId(UserId userId);

    /**
     * Get total unpaid amount for a user.
     */
    BigDecimal getTotalUnpaidAmountByUserId(UserId userId);
}
