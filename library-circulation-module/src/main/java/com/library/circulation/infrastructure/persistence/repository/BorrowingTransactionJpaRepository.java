package com.library.circulation.infrastructure.persistence.repository;

import com.library.circulation.domain.entities.TransactionStatus;
import com.library.circulation.infrastructure.persistence.entity.BorrowingTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for BorrowingTransactionEntity.
 */
@Repository
public interface BorrowingTransactionJpaRepository extends JpaRepository<BorrowingTransactionEntity, Long> {

    /**
     * Find all transactions for a user.
     */
    List<BorrowingTransactionEntity> findByUserId(Long userId);

    /**
     * Find all transactions for an item.
     */
    List<BorrowingTransactionEntity> findByItemId(Long itemId);

    /**
     * Find active transactions for a user.
     */
    @Query("SELECT t FROM BorrowingTransactionEntity t WHERE t.userId = :userId AND t.status = 'ACTIVE'")
    List<BorrowingTransactionEntity> findActiveByUserId(@Param("userId") Long userId);

    /**
     * Find all overdue transactions.
     */
    @Query("SELECT t FROM BorrowingTransactionEntity t WHERE t.status IN ('ACTIVE', 'OVERDUE') AND t.dueDate < :currentDate")
    List<BorrowingTransactionEntity> findOverdueTransactions(@Param("currentDate") LocalDate currentDate);

    /**
     * Check if there's an active transaction for an item.
     */
    boolean existsByItemIdAndStatus(Long itemId, TransactionStatus status);
}
