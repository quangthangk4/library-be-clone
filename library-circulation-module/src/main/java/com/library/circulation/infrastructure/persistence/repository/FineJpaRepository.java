package com.library.circulation.infrastructure.persistence.repository;

import com.library.circulation.domain.entities.PaymentStatus;
import com.library.circulation.infrastructure.persistence.entity.FineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for FineEntity.
 */
@Repository
public interface FineJpaRepository extends JpaRepository<FineEntity, Long> {

    /**
     * Find fine by transaction ID.
     */
    Optional<FineEntity> findByTransactionId(Long transactionId);

    /**
     * Find all fines for a user (through their transactions).
     */
    @Query("SELECT f FROM FineEntity f JOIN BorrowingTransactionEntity t ON f.transactionId = t.id WHERE t.userId = :userId")
    List<FineEntity> findByUserId(@Param("userId") Long userId);

    /**
     * Find unpaid fines for a user.
     */
    @Query("SELECT f FROM FineEntity f JOIN BorrowingTransactionEntity t ON f.transactionId = t.id WHERE t.userId = :userId AND f.paymentStatus = 'UNPAID'")
    List<FineEntity> findUnpaidByUserId(@Param("userId") Long userId);

    /**
     * Get total unpaid amount for a user.
     */
    @Query("SELECT COALESCE(SUM(f.fineAmount), 0) FROM FineEntity f JOIN BorrowingTransactionEntity t ON f.transactionId = t.id WHERE t.userId = :userId AND f.paymentStatus = 'UNPAID'")
    BigDecimal getTotalUnpaidAmountByUserId(@Param("userId") Long userId);
}
